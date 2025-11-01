package com.cricket.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.cricket.model.MatchResult;
import com.cricket.model.Scoreboard;
import com.cricket.model.TeamResult;
import org.springframework.stereotype.Component;

@Component
public class MapBasedRepository implements ResultService {
	private final Map<Integer, MatchResult> results;

	public MapBasedRepository() {
		results = new ConcurrentHashMap<>();
	}

	@Override
	public MatchResult GetResult(Integer id) {
		return results.get(id);
	}

	@Override
	public void NewResult(MatchResult result) {
		results.put(result.getId(), result);
	}

	@Override
	public Map<Integer, MatchResult> GetAll() {
		return results;
	}

	/**
	 * Aggregates match results to compute the scoreboard summarizing matches won
	 * per team, overall league winner, and total win percentage accumulated for
	 * each team.
	 *
	 * @param data a map of match results to aggregate
	 * @return a Scoreboard object representing the current league standings
	 */
	@Override
	public Scoreboard GetScoreboard(Map<Integer, MatchResult> data) {
		// Map to count how many matches each team has won
		Map<String, Integer> matchesWon = new ConcurrentHashMap<>();
		String overallWinner = "NA";
		// Map to accumulate total win percentages per team across matches
		Map<String, BigDecimal> totalWinPercentage = new ConcurrentHashMap<>();

		data.entrySet().stream().filter(entry -> entry.getValue() != null).forEach(entry -> {
			MatchResult record = entry.getValue();
			// Determine winner for this match by highest runs scored
			String winner = record.getTeamResults().stream().max(Comparator.comparingInt(TeamResult::getRuns))
					.map(TeamResult::getTeam).orElse("");
			// Increment matches won count for winner
			matchesWon.put(winner, matchesWon.getOrDefault(winner, 0) + 1);
			// Accumulate win percentage per team
			record.getTeamResults().stream().forEach(t -> {
				totalWinPercentage.merge(t.getTeam(), t.getWinPercentage(), BigDecimal::add);
			});
		});

		// Determine overall league winner i.e., team with highest matches won
		overallWinner = findOverallWinner(matchesWon);
		return new Scoreboard(matchesWon, overallWinner, totalWinPercentage);
	}

	/**
	 * Finds the team with the maximum matches won.
	 *
	 * @param teamWins map of teams and their respective match win counts
	 * @return the team name with the highest wins or "NA" if none found
	 */
	private String findOverallWinner(Map<String, Integer> teamWins) {
		return teamWins.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("NA");
	}

	/**
	 * Clears all stored match results, resetting the repository state.
	 */
	@Override
	public void reset() {
		results.clear();
	}
}
