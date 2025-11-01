package com.cricket.service;

import java.util.Map;

import com.cricket.model.MatchResult;
import com.cricket.model.Scoreboard;

public interface ResultService {
	MatchResult GetResult(Integer id);

	void NewResult(MatchResult result);

	Map<Integer, MatchResult> GetAll();

	void reset();

	Scoreboard GetScoreboard(Map<Integer, MatchResult> data);
}
