package com.teeparty.tournament.tournament;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepo extends CrudRepository<Tournament, Long> {
    List<Tournament> findAll();
}

