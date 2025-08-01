package com.teeparty.tournament.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
public class MemberService {

    private final MemberRepo memberRepo;

    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    public Member getMemberById(Long id) {
        return memberRepo.findById(id).orElse(null);
    }

    public List<Member> getAllMembers(){
        return memberRepo.findAll();
    }
}
