package jpabook.jpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    /*private final MemberRepository memberRepository;

    private void validateDuplicatedMember(Member member) {
        if (!memberRepository.findByName(member.getName()).isEmpty()) {
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Long join(Member member) {
        validateDuplicatedMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        Member member = memberRepository.findById(id);
        return member;
    }

    public List<Member> findMembers() {
        List<Member> members = memberRepository.findAll();
        return members;
    }*/
}
