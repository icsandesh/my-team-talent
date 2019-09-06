package com.target.myteam.model;

import lombok.Data;

import java.util.List;

@Data
public class TeamSkillMatchOutput {

    private Integer contextualTeamScore;
    private Integer diversityScore;
    private Integer absoluteTeamScore;
    private List<HistogramSkillScore> teamHistogramScores;

}

