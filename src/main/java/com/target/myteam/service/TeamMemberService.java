package com.target.myteam.service;

import com.target.myteam.model.SkillSet;
import com.target.myteam.model.TeamMemberProfile;
import com.target.myteam.repositories.TeamMemberRepository;
import com.target.myteam.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TeamMemberService {


    @Autowired
    TeamMemberRepository teamMemberRepository;

    public List<TeamMemberProfile> fetchAllTeamMemberData() {

        return teamMemberRepository.findAll();

    }


    public SkillSet fetchSkillSet(){

        String skillSet = CommonUtils.readContentFromFile("data/SkillSet.json");
        return CommonUtils.getObjectFromStringJson(skillSet, SkillSet.class);
    }




}
