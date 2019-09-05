package com.target.My.Team.Talent;

import com.target.My.Team.Talent.model.TeamMemberProfile;
import com.target.My.Team.Talent.repositories.TeamMemberRepository;
import com.target.My.Team.Talent.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

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

            File file = ResourceUtils.getFile("classpath:data/team_member.json");
            String json = new String(Files.readAllBytes(file.toPath()));

            TeamMemberProfile[] teamMemberProfiles = CommonUtils.getObjectFromStringJson(json, TeamMemberProfile[].class);
            teamMemberRepository.save(teamMemberProfiles[0]);
        };
    }

}
