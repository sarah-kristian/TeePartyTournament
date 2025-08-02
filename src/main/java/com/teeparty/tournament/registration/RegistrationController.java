package com.teeparty.tournament.registration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/api/registration")

public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Registration>> getAll() {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registration> getById(@PathVariable long id) {
        return registrationService.getRegistrationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/unregister/{memberId}/{tournamentId}")
    public ResponseEntity<Void> unregisterMemberForTournament(@PathVariable long memberId, @PathVariable long tournamentId) {
        boolean success = registrationService.unregisterMemberForTournament(memberId, tournamentId);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/register/{memberId}/{tournamentId}")
    public ResponseEntity<Registration> registerMemberforTournament(@PathVariable long memberId, @PathVariable long tournamentId) {

        Registration registration = registrationService.registerMemberForTournament(memberId, tournamentId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registration.getId())
                .toUri();
        return ResponseEntity.created(location).body(registration);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrationById(@PathVariable long id) {
        boolean success = registrationService.deleteRegistrationById(id);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
