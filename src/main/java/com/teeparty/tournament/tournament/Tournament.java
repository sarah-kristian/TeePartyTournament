package com.teeparty.tournament.tournament;

import com.teeparty.tournament.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<Member> members;
    private boolean isCompleted;

    public Tournament() {
    }

}
