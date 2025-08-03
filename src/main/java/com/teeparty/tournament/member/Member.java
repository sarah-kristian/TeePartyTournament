package com.teeparty.tournament.member;

import com.teeparty.tournament.registration.Registration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter

@Entity
public class Member {
    @Id
    @SequenceGenerator(name = "member_sequence", sequenceName = "member_sequence", allocationSize = 1)
    @GeneratedValue(generator = "member_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String email;
    private String phone;
    private LocalDateTime membershipStartDate;
    private LocalDateTime membershipEndDate;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registeredTournaments;  // List of tournament registrations


    public Member() {
    }

    public boolean isActiveMember() {
        return membershipEndDate.isAfter(LocalDateTime.now());
    }

    public boolean isExpiredMember() {
        return membershipEndDate.isBefore(LocalDateTime.now());
    }
    public float getMembershipDuration() {
        LocalDateTime date = isExpiredMember() ? membershipEndDate: LocalDateTime.now();
        Duration duration = Duration.between(membershipStartDate, date);
        return duration.toDays();
    }
    @PrePersist
    public void initializeDates() {
        if (membershipStartDate == null) {
            membershipStartDate = LocalDateTime.now();
        }
        if (membershipEndDate == null) {
            membershipEndDate = membershipStartDate.plusYears(1);
        }
    }

}
