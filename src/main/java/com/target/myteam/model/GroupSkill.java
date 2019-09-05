package com.target.myteam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public
class GroupSkill {
    private String groupName;
    private Integer groupScore;
    private List<Skill> skills;
}
