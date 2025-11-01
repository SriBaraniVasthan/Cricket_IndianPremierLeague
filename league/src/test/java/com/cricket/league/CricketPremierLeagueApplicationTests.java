package com.cricket.league;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.cricket.model.MatchResult;
import com.cricket.model.Scoreboard;
import com.cricket.service.ResultService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the Cricket Premier League application. Tests load the
 * Spring Boot application context and perform HTTP tests, including posting
 * match results and retrieving computed scoreboard data.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CricketPremierLeagueApplicationTests {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ResultService resultService;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
	}

	@AfterEach
	public void reset() throws Exception {
		resultService.reset();
	}

	/**
	 * Test posting and computing scoreboard for first 10 match results. Asserts
	 * expected wins for Chennai Super Kings and Mumbai Indians.
	 */
	@Test
	public void first10MatchesTest() throws Exception {
		Scoreboard scoreboard = runTest(10);
		assertNotNull(scoreboard);
		assertEquals(10, scoreboard.getMatchesWon().getOrDefault("Chennai Super Kings", 0));
		assertEquals(0, scoreboard.getMatchesWon().getOrDefault("Mumbai Indians", 0));
	}

	/**
	 * Test posting and computing scoreboard for first 50 match results. Asserts
	 * expected wins counts for Chennai Super Kings and Mumbai Indians.
	 */
	@Test
	public void first50MatchesTest() throws Exception {
		Scoreboard scoreboard = runTest(50);
		assertNotNull(scoreboard);
		assertEquals(14, scoreboard.getMatchesWon().get("Chennai Super Kings"));
		assertEquals(8, scoreboard.getMatchesWon().get("Mumbai Indians"));
	}

	/**
	 * Test posting and computing scoreboard for all 90 match results. Validates
	 * wins counts and confirms the overall league winner.
	 */
	@Test
	public void all90MatchesTest() throws Exception {
		Scoreboard scoreboard = runTest(90);
		assertNotNull(scoreboard);
		assertEquals(18, scoreboard.getMatchesWon().get("Chennai Super Kings"));
		assertEquals(11, scoreboard.getMatchesWon().get("Mumbai Indians"));
		assertEquals("Chennai Super Kings", scoreboard.getLeagueWinner());
	}

	/**
	 * Helper method to post matches from JSON files and retrieve the resulting
	 * scoreboard.
	 *
	 * @param numberOfMatches number of match JSON files to load and post
	 * @return the computed scoreboard after posting provided matches
	 * @throws Exception on file or JSON processing error
	 */
	private Scoreboard runTest(int numberOfMatches) throws Exception {
		for (int i = 1; i <= numberOfMatches; i++) {
			Class<?> clazz = this.getClass();
			InputStream is = clazz.getResourceAsStream(
					String.format("/sample-cricket-results/match%s.json", String.format("%03d", i)));
			if (is == null) {
				System.err.println("JSON file not found for match number: " + i);
			} else {
				MatchResult matchResult = objectMapper.readValue(is, MatchResult.class);
				template.postForEntity(base.toString() + "/result", matchResult, String.class);
			}
		}
		ResponseEntity<Scoreboard> scores = template.getForEntity(base.toString() + "/scoreboard", Scoreboard.class);
		return scores.getBody();
	}
}
