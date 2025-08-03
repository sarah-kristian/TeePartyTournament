package com.teeparty.tournament.registration;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.teeparty.tournament.member.Member;
import com.teeparty.tournament.tournament.Tournament;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

@Entity
public class Registration {
    @Id
    @SequenceGenerator(name = "registration_sequence", sequenceName = "registration_sequence", allocationSize = 1)
    @GeneratedValue(generator = "registration_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    private LocalDateTime registrationDate;

    public Registration() {
    }

}
