package com.teeparty.tournament.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepo extends CrudRepository<Member, Long> {
    List<Member> findAll();
    Optional<Member> findByPhone(String phoneNumber);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);
    List<Member> findMembersByMembershipStartDate(LocalDateTime startDate);
    List<Member> findMembersByMembershipEndDate(LocalDateTime endDate);
    List<Member> findMembersByMembershipStartDateBefore(LocalDateTime startDate);
}
