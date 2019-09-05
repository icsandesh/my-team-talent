package com.target.myteam;

import com.target.myteam.model.TeamMemberProfile;
import com.target.myteam.repositories.TeamMemberRepository;
import com.target.myteam.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;


@Slf4j
@SpringBootApplication
public class MyTeamTalentApplication {


    @Autowired
    private TeamMemberRepository teamMemberRepository;


    public static void main(String[] args) {
        SpringApplication.run(MyTeamTalentApplication.class, args);
    }


    @Bean
    public CommandLineRunner addDataToMongdoDB() {

        return args -> {

            teamMemberRepository.deleteAll();

            log.info("Reading from resource file");
            String json = CommonUtils.readContentFromFile("data/team_member.json");

            TeamMemberProfile[] teamMemberProfiles = CommonUtils.getObjectFromStringJson(json, TeamMemberProfile[].class);

            log.info("teamMember profiles {}", teamMemberProfiles.toString());

            Arrays.stream(teamMemberProfiles).forEach(teamMemberProfile -> teamMemberRepository.save(teamMemberProfile));

        };
    }

}
