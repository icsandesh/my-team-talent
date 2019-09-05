package com.target.myteam.repositories;

import com.target.myteam.model.TeamMemberProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TeamMemberRepository extends MongoRepository<TeamMemberProfile, String> {

    TeamMemberRepository findByLanId(String lanId);
}