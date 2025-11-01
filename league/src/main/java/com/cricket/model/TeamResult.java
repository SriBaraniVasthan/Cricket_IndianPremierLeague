package com.cricket.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamResult {
	private final String team;
	private final Integer runs;
	private final BigDecimal winPercentage;

	public TeamResult(@JsonProperty("team") String team, @JsonProperty("runs") Integer runs,
			@JsonProperty("winPercentage") BigDecimal winPercentage) {
		this.team = team;
		this.runs = runs;
		this.winPercentage = winPercentage;
	}

	public String getTeam() {
		return team;
	}

	public Integer getRuns() {
		return runs;
	}

	public BigDecimal getWinPercentage() {
		return winPercentage;
	}

}