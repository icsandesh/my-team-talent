package com.target.myteam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "team_member_profile")
@Data
public class TeamMemberProfile {

    @Id
    private String lanId;

    private String firstName;

    private String lastName;

    private String pyramid;

    private String workLocation;

    private String email;

    private String groupRole;

    private GroupSkill groupSkills;
}


@Data
class GroupSkill{
    private String groupName;
    private Integer groupScore;
}
