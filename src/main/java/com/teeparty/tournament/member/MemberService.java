package com.teeparty.tournament.member;

import com.teeparty.tournament.registration.Registration;
import com.teeparty.tournament.registration.RegistrationRepo;
import com.teeparty.tournament.tournament.Tournament;
import com.teeparty.tournament.util.util;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MemberService {

    private final MemberRepo memberRepo;
    private final RegistrationRepo registrationRepo;

    public MemberService(MemberRepo memberRepo, RegistrationRepo registrationRepo) {
        this.memberRepo = memberRepo;
        this.registrationRepo = registrationRepo;
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

    public List<Member> getActiveMembers() {
        return memberRepo.findAll().stream()
                .filter(Member::isActiveMember)
                .collect(Collectors.toList());
    }

    public List<Member> getExpiredMembers() {
        return memberRepo.findAll().stream()
                .filter(Member::isExpiredMember)
                .collect(Collectors.toList());
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
    public List<Tournament> getTournamentsForMember(Long memberId) {
        List<Registration> registrations = registrationRepo.findByMemberId(memberId);
        return registrations.stream()
                .map(Registration::getTournament)
                .collect(Collectors.toList());
    }
    @Transactional
    public Member create(Member member) {
        if (member.getMembershipStartDate() == null) {
            member.setMembershipStartDate(LocalDateTime.now());
        }

        member.setMembershipEndDate(member.getMembershipStartDate().plusYears(1));

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
