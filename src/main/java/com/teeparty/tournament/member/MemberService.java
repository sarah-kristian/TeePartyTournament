package com.teeparty.tournament.member;

import com.teeparty.tournament.util.util;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


import java.util.List;
import java.util.Optional;



@Service
public class MemberService {

    private final MemberRepo memberRepo;

    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    public List<Member> getAllMembers(){
        return memberRepo.findAll();
    }

    public Optional<Member> getMemberById(long id) {
        return memberRepo.findById(id);
    }
    public Optional<Member> getMemberByPhoneNumber(String phoneNumber) {
        return memberRepo.findByPhone(phoneNumber);
    }
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepo.findByEmail(email);
    }
    public Optional<Member> getMemberByUsername(String username) {
        return memberRepo.findByUsername(username);
    }
    public List<Member> getMembersByMembershipStartDate(String startDate) {
        LocalDateTime parsedDate = util.parseDate(startDate);
        return memberRepo.findMembersByMembershipStartDate(parsedDate);
    }
    public List<Member> getMembersByMembershipEndDate(String endDate) {
        LocalDateTime parsedDate = util.parseDate(endDate);
        return memberRepo.findMembersByMembershipEndDate(parsedDate);
    }
    public List<Member> getMembersByMembershipStartDateBefore(String startDate) {
        LocalDateTime parsedDate = util.parseDate(startDate);
        return memberRepo.findMembersByMembershipStartDateBefore(parsedDate);
    }

    @Transactional
    public Member create(Member member) {
        return memberRepo.save(member);
    }

    @Transactional
    public Member update(long id, Member updatedMember) {
        return memberRepo.findById(id)
                .map(existing -> {
                    existing.setEmail(updatedMember.getEmail());
                    existing.setMembershipEndDate(updatedMember.getMembershipEndDate());
                    existing.setMembershipStartDate(updatedMember.getMembershipStartDate());
                    existing.setPhone(updatedMember.getPhone());
                    existing.setUsername(updatedMember.getUsername());
                    return memberRepo.save(existing);
                })
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
    }

    @Transactional
    public boolean delete(Member member) {
        try {
            memberRepo.delete(member);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Transactional
    public boolean deleteMemberById(Long id) {
        try {
            memberRepo.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }
}
