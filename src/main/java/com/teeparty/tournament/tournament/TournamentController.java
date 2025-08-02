package com.teeparty.tournament.tournament;

import com.teeparty.tournament.member.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tournament")
public class TournamentController {
    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAll() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping("/{tournamentId}/members")
    public ResponseEntity<List<Member>> getMembersForTournament(@PathVariable Long tournamentId) {
        List<Member> members = tournamentService.getMembersForTournament(tournamentId);
        return ResponseEntity.ok(members);
    }

}
