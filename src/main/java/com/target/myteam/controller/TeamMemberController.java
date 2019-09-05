package com.target.myteam.controller;

import com.target.myteam.model.SkillSet;
import com.target.myteam.model.TeamMemberProfile;
import com.target.myteam.service.TeamMemberService;
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

    @GetMapping("/skillSet")
    public SkillSet getAllTeamMemberSkills(){
        return teamMemberService.fetchSkillSet();
    }




}
