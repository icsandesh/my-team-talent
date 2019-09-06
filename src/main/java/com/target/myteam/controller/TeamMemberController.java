package com.target.myteam.controller;

import com.target.myteam.model.SkillSet;
import com.target.myteam.model.TeamMemberProfile;
import com.target.myteam.model.TeamSkillMatchInput;
import com.target.myteam.model.TeamSkillMatchOutput;
import com.target.myteam.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamMemberController {


    @Autowired
    TeamMemberService teamMemberService;


    @GetMapping("/teamMembers")
    public List<TeamMemberProfile> getTeamMembersInfo() {

        return teamMemberService.fetchAllTeamMemberData();

    }

    @PutMapping("/teamMembers/{teamMemberId}")
    public void updateTeamMember(@PathVariable(value = "teamMemberId") String lanId,
                                 @RequestBody TeamMemberProfile teamMemberProfile) {

        teamMemberService.saveTeamMemberData(lanId, teamMemberProfile);
    }


    @GetMapping("/skillSet")
    public SkillSet getAllTeamMemberSkills() {
        return teamMemberService.fetchSkillSet();
    }


    @PostMapping("/computeSkill")
    public TeamSkillMatchOutput computeTeamSkills(@RequestBody TeamSkillMatchInput teamSkillMatchInput) {
        return teamMemberService.computeTeamSkills(teamSkillMatchInput);
    }

}
