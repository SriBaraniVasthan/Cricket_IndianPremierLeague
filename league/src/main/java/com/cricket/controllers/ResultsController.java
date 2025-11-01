package com.cricket.controllers;

import java.net.URI;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.cricket.model.MatchResult;
import com.cricket.model.Scoreboard;
import com.cricket.service.ResultNotFoundException;
import com.cricket.service.ResultService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * REST controller that handles cricket league results and scoreboard endpoints.
 * It provides APIs to get individual match results, submit new match results,
 * and retrieve the current scoreboard.
 */
@RestController
public class ResultsController {
	private final ResultService resultService;

	public ResultsController(ResultService resultService) {
		this.resultService = resultService;
	}

	/**
	 * Retrieves a single match result by its unique ID. Throws
	 * ResultNotFoundException if the ID does not exist.
	 *
	 * @param id the unique identifier of the match result
	 * @return the MatchResult for the given ID
	 */
	@GetMapping("/result/{id}")
	public MatchResult getResult(@PathVariable Integer id) {
		MatchResult result = resultService.GetResult(id);
		if (result == null) {
			throw new ResultNotFoundException(id);
		}
		return result;
	}

	/**
	 * Submits a new match result. Validates that the result has a non-null ID.
	 * Returns 201 Created with location of new resource on success. Returns 400 Bad
	 * Request if ID is null.
	 *
	 * @param result the new MatchResult object from request body
	 * @return ResponseEntity indicating success or failure of creation
	 */
	@PostMapping("/result")
	public ResponseEntity<String> newResult(@RequestBody MatchResult result) {
		if (result.getId() != null) {
			resultService.NewResult(result);
			return ResponseEntity.created(URI.create("/result/" + result.getId())).build();
		}
		return ResponseEntity.badRequest().body("Id was null");
	}

	/**
	 * Retrieves the current scoreboard computed from all stored match results.
	 *
	 * @return the Scoreboard representing league standings and stats
	 */
	@GetMapping("/scoreboard")
	public Scoreboard getScoreboard() {
		Map<Integer, MatchResult> data = resultService.GetAll();
		return resultService.GetScoreboard(data);
	}

}