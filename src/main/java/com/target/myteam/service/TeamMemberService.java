package com.target.myteam.service;

import com.target.myteam.model.*;
import com.target.myteam.repositories.TeamMemberRepository;
import com.target.myteam.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

        List<TeamMemberProfile> all = teamMemberRepository.findAll();

        List<GroupSkill> groupedSkills = all.get(0).getGroupedSkills();

        SkillSet skillSet = new SkillSet();

        List<String> higherLevelSkills = new ArrayList<>();
        List<String> lowerLevelSkills = new ArrayList<>();

        for (GroupSkill groupedSkill : groupedSkills) {
            higherLevelSkills.add(groupedSkill.getGroupName());
            groupedSkill.getSkills().forEach(x -> lowerLevelSkills.add(x.getName()));
        }

        skillSet.setHigherLevelSkills(higherLevelSkills);
        skillSet.setLowerLevelSkills(lowerLevelSkills);
        return skillSet;
    }


    public TeamSkillMatchOutput computeTeamSkills(TeamSkillMatchInput teamSkillMatchInput) {
        List<TeamMemberProfile> teamMemberProfiles = teamMemberRepository.findAll();

        List<String> teamMemberLanIds = teamSkillMatchInput.getTeamMemberLanIds();
        List<TeamMemberProfile> selectedMembers = teamMemberProfiles.stream().filter(profile -> teamMemberLanIds
                .contains(profile.getLanId())).collect(Collectors.toList());

        Map<String, Map<String, Integer>> lanIdToSkillMap = buildLanIdToSkillMap(selectedMembers);

        Integer absoluteScore = calculateAbsouluteTeamScore(lanIdToSkillMap);
        Integer contextScore = calculateContextualTeamScore(lanIdToSkillMap, teamSkillMatchInput.getExpectedSkill());
        Integer diversityScore = calculateDiversityScore(selectedMembers);
        List<HistogramSkillScore> histogramSkillScores = calculateHistogramScores(lanIdToSkillMap, teamSkillMatchInput.getExpectedSkill());


        TeamSkillMatchOutput teamSkillMatchOutput = new TeamSkillMatchOutput();

        teamSkillMatchOutput.setAbsoluteTeamScore(absoluteScore);
        teamSkillMatchOutput.setContextualTeamScore(contextScore);
        teamSkillMatchOutput.setDiversityScore(diversityScore);
        teamSkillMatchOutput.setTeamHistogramScores(histogramSkillScores);

        return teamSkillMatchOutput;
    }

    private List<HistogramSkillScore> calculateHistogramScores(Map<String, Map<String, Integer>> lanIdToSkillMap, List<Skill> expectedSkills) {


        List<HistogramSkillScore> histogramSkillScores = new ArrayList<>();


        for (Skill expectedSkill : expectedSkills) {

            float numerator = 0;
            HistogramSkillScore histogramSkillScore = new HistogramSkillScore();
            for (Map.Entry<String, Map<String, Integer>> memberSkill : lanIdToSkillMap.entrySet()) {


                for (Map.Entry<String, Integer> skillEntry : memberSkill.getValue().entrySet()) {

                    if (skillEntry.getKey().equalsIgnoreCase(expectedSkill.getName())) {

                        if (skillEntry.getValue() >= expectedSkill.getScore()) {
                            numerator += 1;
                        } else {
                            numerator += (float) skillEntry.getValue() / expectedSkill.getScore();
                        }
                    }
                }
            }

            histogramSkillScore.setSkill(expectedSkill.getName());
            histogramSkillScore.setExpected(expectedSkill.getScore());
            histogramSkillScore.setActual((int) (numerator * 100 / lanIdToSkillMap.keySet().size()));
            histogramSkillScores.add(histogramSkillScore);
        }

        return histogramSkillScores;
    }

    private Integer calculateDiversityScore(List<TeamMemberProfile> selectedMembers) {

        int maleCount = 0;
        int femaleCount = 0;
        for (TeamMemberProfile member : selectedMembers) {
            if (member.getProfile().getGender().equalsIgnoreCase("male")) {
                maleCount++;
            } else {
                femaleCount++;
            }
        }
        int score =  (int)Math.ceil(((float)femaleCount * 100 / maleCount ));
        if(score > 100){
            return (int)Math.ceil(((float)100 / score) * 100);
        }
        return score;
    }

    private Integer calculateAbsouluteTeamScore(Map<String, Map<String, Integer>> selectedMembers) {

        int numerator = 0;

        for (Map.Entry<String, Map<String, Integer>> memberSkill : selectedMembers.entrySet()) {

            for (Map.Entry<String, Integer> skillEntry : memberSkill.getValue().entrySet()) {

                if (skillEntry.getValue() != null) {
                    numerator += skillEntry.getValue();
                }
            }
        }

        SkillSet skillSet = fetchSkillSet();
        int denominator = skillSet.getHigherLevelSkills().size() + skillSet.getLowerLevelSkills().size();

        return (int)Math.ceil(((double) numerator / (denominator * 100 * selectedMembers.keySet().size())) * 100);
    }

    private Integer calculateContextualTeamScore(Map<String, Map<String, Integer>> lanIdToSkillMap, List<Skill> expectedSkills) {



        List<Float> perSkillNumerator = new ArrayList<>();

        for (Skill expectedSkill : expectedSkills) {


            List<Integer> skillScores = new ArrayList<>();
            for (Map.Entry<String, Map<String, Integer>> memberSkill : lanIdToSkillMap.entrySet()) {
                Integer memberSkillScore = memberSkill.getValue().get(expectedSkill.getName());
                if (memberSkillScore != null) {

                    skillScores.add(memberSkillScore);
                }

            }

            double skillAvg = skillScores.stream().mapToInt(val -> val).average().orElse(0.0);
            double perSkill = skillAvg / expectedSkill.getScore();
            perSkillNumerator.add(perSkill >= 1 ? 1 : (float) perSkill);
        }


        double sum = perSkillNumerator.stream().mapToDouble(a -> a).sum();

        double contextScore = sum * 100 / expectedSkills.size();

        return (int)contextScore;
    }


    private Map<String, Map<String, Integer>> buildLanIdToSkillMap(List<TeamMemberProfile> teamMemberProfiles) {


        Map<String, Map<String, Integer>> lanIdToSkillMap = new HashMap<>();
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
