package com.target.myteam.model;

import lombok.Data;

import java.util.List;

@Data
public class TeamSkillMatchInput {

    List<Skill> expectedSkill;
    List<String> teamMemberLanIds;
}