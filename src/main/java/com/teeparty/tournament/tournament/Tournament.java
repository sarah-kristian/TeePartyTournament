package com.teeparty.tournament.tournament;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
public class Tournament {
    @Id
    @SequenceGenerator(name = "tournament_sequence", sequenceName = "tournament_sequence", allocationSize = 1)
    @GeneratedValue(generator = "tournament_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private BigDecimal prizeAmount;
    private String prizeDescription;
    private BigDecimal entryFee;

    private boolean isCompleted;

    public Tournament() {
    }

}
