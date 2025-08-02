package com.teeparty.tournament.registration;

import com.teeparty.tournament.member.Member;
import com.teeparty.tournament.tournament.Tournament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RegistrationRepo extends CrudRepository<Registration, Long> {
    Optional<Registration> findByMemberAndTournament(Member member, Tournament tournament);
    Optional<Registration> findByMember_IdAndTournament_Id(long memberId, long tournamentId);

    boolean existsByMemberAndTournament(Member member, Tournament tournament);

}

