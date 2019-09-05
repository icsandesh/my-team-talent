package com.target.myteam.model;

import lombok.Data;

import java.util.List;

@Data
public class SkillSet {
    private List<String> higherLevelSkills;
    private List<String> lowerLevelSkills;
}
