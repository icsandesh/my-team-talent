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
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;


@Slf4j
@SpringBootApplication(scanBasePackages = {"com.target.myteam"})
public class MyTeamTalentApplication {


    @Autowired
    private TeamMemberRepository teamMemberRepository;


    public static void main(String[] args) {
        SpringApplication.run(MyTeamTalentApplication.class, args);
    }


    @Bean
    public CommandLineRunner addDataToMongdoDB() {


        return args -> {

            log.info("Reading from resource file");
            File file = ResourceUtils.getFile("classpath:data/team_member.json");
            String json = new String(Files.readAllBytes(file.toPath()));

            TeamMemberProfile[] teamMemberProfiles = CommonUtils.getObjectFromStringJson(json, TeamMemberProfile[].class);

            log.info("teamMember profiles {}", teamMemberProfiles.toString());
            teamMemberRepository.save(teamMemberProfiles[0]);
        };
    }

}
