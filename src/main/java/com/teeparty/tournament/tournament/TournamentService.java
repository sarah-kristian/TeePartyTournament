package com.teeparty.tournament.tournament;

import com.teeparty.tournament.member.Member;
import com.teeparty.tournament.registration.Registration;
import com.teeparty.tournament.registration.RegistrationRepo;
import com.teeparty.tournament.util.util;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TournamentService {
    private final TournamentRepo tournamentRepo;
    private final RegistrationRepo registrationRepo;

    public TournamentService(TournamentRepo tournamentRepo, RegistrationRepo registrationRepo) {
        this.tournamentRepo = tournamentRepo;
        this.registrationRepo = registrationRepo;
    }


    public Optional<Tournament> getTournamentById(long id) {
        return tournamentRepo.findById(id);
    }

    public List<Tournament> getTournamentsByLocation(String location) {
        return tournamentRepo.findTournamentsByLocation(location);
    }
    public List<Tournament> getTournamentsByStartDate(String startDate) {
        LocalDateTime parsedDate = util.parseDate(startDate);
        return tournamentRepo.findTournamentsByStartDate(parsedDate);
    }

    public List<Tournament> getTournamentsByEndDate(String endDate) {
        LocalDateTime parsedDate = util.parseDate(endDate);
        return tournamentRepo.findTournamentsByEndDate(parsedDate);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepo.findAll();
    }

    public List<Member> getMembersForTournament(Long tournamentId) {
        List<Registration> registrations = registrationRepo.findByTournamentId(tournamentId);
        return registrations.stream()
                .map(Registration::getMember)
                .collect(Collectors.toList());
    }

    @Transactional
    public Tournament create(Tournament tournament) {
        return tournamentRepo.save(tournament);
    }
    @Transactional
    public Tournament update(long id, Tournament updatedTournament){
        return tournamentRepo.findById(id)
                .map(existing -> {
                    existing.setName(updatedTournament.getName());
                    existing.setStartDate(updatedTournament.getStartDate());
                    existing.setEndDate(updatedTournament.getEndDate());
                    existing.setLocation(updatedTournament.getLocation());
                    existing.setPrizeAmount(updatedTournament.getPrizeAmount());
                    existing.setPrizeDescription(updatedTournament.getPrizeDescription());
                    existing.setEntryFee(updatedTournament.getEntryFee());
                    return tournamentRepo.save(existing);
                })
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with Id: " + id));
    }


    @Transactional
    public boolean delete(Tournament tournament) {
        try {
            tournamentRepo.delete(tournament);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Transactional
    public boolean deleteTournamentById(long id) {
        try {
            tournamentRepo.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }
}
