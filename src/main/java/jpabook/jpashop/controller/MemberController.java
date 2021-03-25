package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.dto.MemberDto;
import jpabook.jpashop.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/jpashop/member", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController{
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MemberController(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public ResponseEntity queryMembers() {
        List<Member> members = memberRepository.findAll();
        return ResponseEntity.ok(members);
    }

    @PostMapping
    public ResponseEntity createMember(@RequestBody @Valid MemberDto memberDto) {
        List<Member> memberRepositoryByName = memberRepository.findByName(memberDto.getName());
        if (!memberRepositoryByName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(memberRepository.save(modelMapper.map(memberDto, Member.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity queryMember(@PathVariable Integer id) {
        Member member = memberRepository.getOne(id);
        return ResponseEntity.ok(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMember(@PathVariable Integer id, @RequestBody @Valid MemberDto memberDto) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Member beforeMember = optionalMember.get();
        beforeMember.setName(memberDto.getName());
        beforeMember.setAddress(memberDto.getAddress());
        memberRepository.save(beforeMember);

        return ResponseEntity.ok(memberRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMember(@PathVariable Integer id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        memberRepository.deleteById(id);

        return ResponseEntity.ok(memberRepository.findAll());
    }
}
