package com.target.myteam.model;

import lombok.Data;

@Data
public class HistogramSkillScore{
    private String skill;
    private Integer actual;
    private Integer expected;
}
