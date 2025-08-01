package com.teeparty.tournament.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepo extends CrudRepository<Member, Long> {
    List<Member> findAll();
}
