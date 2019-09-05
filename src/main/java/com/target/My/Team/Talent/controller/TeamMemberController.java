package com.target.My.Team.Talent.controller;

import com.target.My.Team.Talent.model.TeamMemberProfile;
import com.target.My.Team.Talent.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TeamMemberController {


    @Autowired
    TeamMemberService teamMemberService;


    @GetMapping("/teamMembers")
    public List<TeamMemberProfile> getTeamMembersInfo() {

        return teamMemberService.fetchAllTeamMemberData();

    }

}
