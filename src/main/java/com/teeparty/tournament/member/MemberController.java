package com.teeparty.tournament.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/member")

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAll() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getById(@PathVariable long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/phonenumber/{phoneNumber}")
    public ResponseEntity<Member> getByPhoneNumber(@PathVariable String phoneNumber) {
        return memberService.getMemberByPhoneNumber(phoneNumber)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getByEmail(@PathVariable String email) {
        return memberService.getMemberByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Member> getByUsername(@PathVariable String username) {
        return memberService.getMemberByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/startdate")
    public ResponseEntity<List<Member>> getByMembershipStartDate(@RequestParam String startDate) {
        return ResponseEntity.ok(memberService.getMembersByMembershipStartDate(startDate));
    }

    @GetMapping("/enddate")
    public ResponseEntity<List<Member>> getByMembershipEndDate(@RequestParam String endDate) {
        return ResponseEntity.ok(memberService.getMembersByMembershipEndDate(endDate));
    }

    @GetMapping("/startdate")
    public ResponseEntity<List<Member>> getByMembershipStartDateBefore(@RequestParam String startDate) {
        return ResponseEntity.ok(memberService.getMembersByMembershipStartDateBefore(startDate));
    }

    @PostMapping
    public ResponseEntity<Member> create(@RequestBody Member member) {
        Member saved = memberService.create(member);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Member> update(
            @PathVariable long id,
            @RequestBody Member member) {
        Member updatedMember = memberService.update(id, member);
        return (updatedMember != null) ? ResponseEntity.ok(updatedMember) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return memberService.deleteMemberById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
