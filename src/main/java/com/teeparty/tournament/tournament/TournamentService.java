package com.teeparty.tournament.tournament;

import com.teeparty.tournament.member.MemberRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class TournamentService {
    private final MemberRepo memberRepo;
    private final TournamentRepo tournamentRepo;

    public TournamentService(MemberRepo memberRepo, TournamentRepo tournamentRepo) {
        this.memberRepo = memberRepo;
        this.tournamentRepo = tournamentRepo;
    }

    public Tournament getTournamentById(Long id) {
        return tournamentRepo.findById(id).orElse(null);
    }
    public Iterable<Tournament> getAllTournaments() {
        return tournamentRepo.findAll();
    }
}
