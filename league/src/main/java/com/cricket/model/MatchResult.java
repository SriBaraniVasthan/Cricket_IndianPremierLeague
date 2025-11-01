package com.cricket.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchResult {

	private final Integer id;
	private final String location; 
	private final List<TeamResult> teamResults;

	public MatchResult(@JsonProperty("id") Integer id, @JsonProperty("name") String location,@JsonProperty("teamResults") List<TeamResult> teamResults) {
		this.id = id;
		this.location = location;
		this.teamResults = teamResults;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return location;
	}

	public List<TeamResult> getTeamResults() {
		return teamResults;
	}

}