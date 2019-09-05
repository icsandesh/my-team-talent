package com.target.My.Team.Talent.service;

import com.target.My.Team.Talent.model.TeamMemberProfile;
import com.target.My.Team.Talent.repositories.TeamMemberRepository;
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
}
