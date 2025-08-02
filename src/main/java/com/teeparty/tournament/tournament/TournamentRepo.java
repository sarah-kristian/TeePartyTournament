package com.teeparty.tournament.tournament;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRepo extends CrudRepository<Tournament, Long> {
    List<Tournament> findAll();
    Optional<Tournament> findById(long id);
    List<Tournament> findTournamentsByStartDate(LocalDateTime startDate);
    List<Tournament> findTournamentsByEndDate(LocalDateTime endDate);

    List<Tournament> findTournamentsByLocation(String location);
}

