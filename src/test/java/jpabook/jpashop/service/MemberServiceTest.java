package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

//순수한 단위 테스트를 만들게 아니고 부트 실행시켜서 직접 돌려보고 만들고 싶으면 넣어야 하는 어노테이션
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//test에 transactional 있어야 rollback이 된다.
//이 결과만 봐도 repo에서 me써서 persist 해도 집합체 가지고 노는거지 db에 바로바로 insert문 날리고 그러는게 아니라는 것을 알 수 있다.
//transactional commit 하고 flush 하고 이래야 db 에 저장되는 것이다.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("service test member 1");
        //when
        Long id = memberService.join(member);
        //then - 내가 넣은 거랑 조회한 거랑 같으면 테스트 성공
        //em.flush 해주면 영속성 계층에 있는거 db로 날려서 실제 db에 반영되는 것까지 확인할 수 있다.
        assertEquals(member, memberRepository.findOne(id));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("duplicatedMemberCheck");
        Member member2 = new Member();
        member2.setName("duplicatedMemberCheck");

        memberService.join(member1);
        memberService.join(member2);
        //when
        /*try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }*/
        //이거 대신해서 바로 사용할 수 있는게 있다.
        //then - IllegalStateException 나와야해
        org.junit.jupiter.api.Assertions.fail("오류 발생");
    }
}