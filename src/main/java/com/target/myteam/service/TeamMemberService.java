package com.target.myteam.service;

import com.target.myteam.model.*;
import com.target.myteam.repositories.TeamMemberRepository;
import com.target.myteam.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
public class TeamMemberService {


    @Autowired
    TeamMemberRepository teamMemberRepository;

    public List<TeamMemberProfile> fetchAllTeamMemberData() {

        return teamMemberRepository.findAll();

    }


    public void saveTeamMemberData(String lanId, TeamMemberProfile teamMemberProfile) {
        teamMemberProfile.setLanId(lanId);
        teamMemberRepository.save(teamMemberProfile);
    }


    public SkillSet fetchSkillSet() {

        String skillSet = CommonUtils.readContentFromFile("data/SkillSet.json");
        return CommonUtils.getObjectFromStringJson(skillSet, SkillSet.class);
    }


    public TeamSkillMatchOutput computeTeamSkills(TeamSkillMatchInput teamSkillMatchInput) {
        List<TeamMemberProfile> teamMemberProfiles = teamMemberRepository.findAll();

        List<String> teamMemberLanIds = teamSkillMatchInput.getTeamMemberLanIds();
        List<TeamMemberProfile> selectedMembers = teamMemberProfiles.stream().filter(profile -> teamMemberLanIds
                .contains(profile.getLanId())).collect(Collectors.toList());

        Map<String, Map<String, Integer>> lanIdToSkillMap = buildLanIdToSkillMap(selectedMembers);

        Integer absoluteScore = calculateAbsouluteTeamScore(selectedMembers);
        Integer contextScore = calculateContextualTeamScore(lanIdToSkillMap, teamSkillMatchInput.getExpectedSkill());
        Integer diversityScore = calculateDiversityScore(selectedMembers);


        TeamSkillMatchOutput teamSkillMatchOutput = new TeamSkillMatchOutput();

        teamSkillMatchOutput.setAbsoluteTeamScore(absoluteScore);
        teamSkillMatchOutput.setContextualTeamScore(contextScore);
        teamSkillMatchOutput.setDiversityScore(diversityScore);

        return teamSkillMatchOutput;
    }

    private Integer calculateDiversityScore(List<TeamMemberProfile> selectedMembers) {
        return 0;
    }

    private Integer calculateAbsouluteTeamScore(List<TeamMemberProfile> selectedMembers) {
        return 0;
    }

    private Integer calculateContextualTeamScore(Map<String, Map<String, Integer>> lanIdToSkillMap, List<Skill> expectedSkill) {
        return 0;
    }


    private Map<String, Map<String,Integer>> buildLanIdToSkillMap(List<TeamMemberProfile> teamMemberProfiles){


        Map<String, Map<String,Integer>> lanIdToSkillMap = new HashMap<>();
        for (TeamMemberProfile profile : teamMemberProfiles) {

            List<GroupSkill> groupedSkills = profile.getGroupedSkills();

            Map<String, Integer> skillMap = new HashMap<>();

            for (GroupSkill groupedSkill : groupedSkills) {

                skillMap.put(groupedSkill.getGroupName(), groupedSkill.getGroupScore());
                groupedSkill.getSkills().forEach(
                        skill -> skillMap.put(skill.getName(), skill.getScore())
                );
            }
            lanIdToSkillMap.put(profile.getLanId(), skillMap);
        }

        return lanIdToSkillMap;
    }

}
