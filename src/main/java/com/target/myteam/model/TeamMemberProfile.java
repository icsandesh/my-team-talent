package com.target.myteam.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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

    private String level;

    private String groupRole;

    private Profile profile;

    private List<GroupSkill> groupedSkills;
}


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class Education {
    private String level;
    private String college;
    private String degree;
    private Float gpa;
    private Float percentage;
}


@Data
class ExternalProfileLinks{

    private String src;
    private String link;
}


