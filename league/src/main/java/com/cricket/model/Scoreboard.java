package com.cricket.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Scoreboard {
	private Map<String, Integer> matchesWon;
	private String leagueWinner;
	private Map<String, BigDecimal> totalWinPercentage;

	public Scoreboard() {
		this.matchesWon = new HashMap<>();
		this.leagueWinner = null;
		this.totalWinPercentage = new HashMap<>();
	}

	public Scoreboard(Map<String, Integer> matchesWon, String leagueWinner,
			Map<String, BigDecimal> totalWinPercentage) {
		this.matchesWon = matchesWon;
		this.leagueWinner = leagueWinner;
		this.totalWinPercentage = totalWinPercentage;
	}

	public Map<String, Integer> getMatchesWon() {
		return matchesWon;
	}

	public void setMatchesWon(Map<String, Integer> matchesWon) {
		this.matchesWon = matchesWon;
	}

	public String getLeagueWinner() {
		return leagueWinner;
	}

	public void setLeagueWinner(String leagueWinner) {
		this.leagueWinner = leagueWinner;
	}

	public Map<String, BigDecimal> getTotalWinPercentage() {
		return totalWinPercentage;
	}

	public void setTotalWinPercentage(Map<String, BigDecimal> totalWinPercentage) {
		this.totalWinPercentage = totalWinPercentage;
	}
}
