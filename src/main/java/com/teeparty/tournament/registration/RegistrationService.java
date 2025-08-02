package com.teeparty.tournament.registration;

import com.teeparty.tournament.member.Member;
import com.teeparty.tournament.member.MemberRepo;
import com.teeparty.tournament.tournament.Tournament;
import com.teeparty.tournament.tournament.TournamentRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrationService {

    private final RegistrationRepo registrationRepo;
    private final MemberRepo memberRepo;
    private final TournamentRepo tournamentRepo;

    public RegistrationService(RegistrationRepo registrationRepo,
                                         MemberRepo memberRepo,
                                         TournamentRepo tournamentRepo) {
        this.registrationRepo = registrationRepo;
        this.memberRepo = memberRepo;
        this.tournamentRepo = tournamentRepo;
    }

    public Optional<Registration> getRegistrationById(Long id) {
        return registrationRepo.findById(id);
    }

    public Iterable<Registration> getAllRegistrations() {
        return registrationRepo.findAll();
    }

    public boolean isMemberRegisteredForTournament(long memberId, long tournamentId) {
        return registrationRepo.findByMember_IdAndTournament_Id(memberId, tournamentId).isPresent();
    }

    @Transactional
    public Registration registerMemberForTournament(Long memberId, Long tournamentId) {
        Member member = memberRepo.findById(memberId).orElseThrow(() ->
                new EntityNotFoundException("Member not found with ID: " + memberId));
        Tournament tournament = tournamentRepo.findById(tournamentId).orElseThrow(() ->
                new EntityNotFoundException("Tournament not found with ID: " + tournamentId));

        boolean isAlreadyRegistered = isMemberRegisteredForTournament(memberId, tournamentId);
        if (isAlreadyRegistered) {
            throw new IllegalStateException("Member is already registered for this tournament.");
        }

        Registration registration = new Registration();
        registration.setMember(member);
        registration.setTournament(tournament);
        registration.setRegistrationDate(LocalDateTime.now());

        return registrationRepo.save(registration);
    }

    @Transactional
    public boolean unregisterMemberForTournament(long memberId, long tournamentId) {
        Registration registration = registrationRepo.findByMember_IdAndTournament_Id(memberId, tournamentId).orElse(null);

        if (registration == null) {
            return false;
        }

        registrationRepo.delete(registration);

        return true;
    }

    @Transactional
    public boolean deleteRegistrationById(Long id) {
        try {
            registrationRepo.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Transactional
    public boolean delete(Registration registration) {
        try {
            registrationRepo.delete(registration);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

}

