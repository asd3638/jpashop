package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)

//클래스 계층에서 transactional annotation 해주면 하위 메소드에 다 적용이 되긴 하는데 성능을 위해 따로 옵션을 메소드 별로 줄 수도 있다.
//기본적으로 성능이 더 가벼운 readOnly 옵션을 먹인 것으로 하위 메소드들에 대한 초기 transactional 설정을 해주고
//특별하게 조회 기능말고 쓰기 기능까지 추가된 메소드가 있다면 따로 @Transactional 어노테이션을 해주면 된다.
//강조했듯이 스프링은 넓은 범위와 좁은 범위가 있다면 무조건 좁은 범위가 우선권은 가진다.
/*
* 지금은 그렇게 안했는데 @RequiredArgsConstructor 쓰면 final로 선언된 객체들에 한해 의존성 주입 생성자 생성을 자동으로 해줘서
* @AllArgsConstructor 쓰는 것보다 변경하기 수월하게 의존성 주입을 할 수 있다.
* */
public class MemberService {
    //서비스는 repository를 쓸 것이기 때문에 불러와야 하는데
    //스프링 공부하면서 계속 공부했던 의존성 주입의 개념을 여기서 사용해야 한다.
    //생성자 주입을 통해서 의존성 주입을 해도 되고
    //생성자가 하나 뿐이라면 어노테이션으로 설정해줄 수도 있다.
    private final MemberRepository memberRepository;
    //필드 주입 안하고 생성자 주입을 하는 이유는 값을 변경 가능하게 하기 위함이다.
    //생성자 주입을 하면 초기화도 되고 변경할 일도 없으니까 final로 명시할 수 있다. (권장함)

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    //기능1. 회원 가입 + (중복 회원은 가입할 수 없는 로직을 하나 넣음)

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
        //ID는 항상 있을까?
        //persist할 때 영속성 컨텍스트에 entity를 넣는데 그 때 자동적으로 key값인 id를 생성해서 value로 넣게 설정이 되어 있음
        //그러니까 id는 무조건 있다고 보장할 수 있다.
        //이렇게 db랑 연결될 때는 이 값이 항상 있을 수 있나? 까지 생각하는게 중요하다.
    }

    //중복 회원을 검증하는 비즈니스 로직
    //이렇게 한 클래스 내에서만 사용되는 메소드들은 private으로 선언해서 구분해주는게 좋아
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            //회원을 찾아봤는데 뭐가 있으면 값이 할당될거야
            //값이 할당되면 중복되는 회원이 있다는 뜻이니까 예외처리해서 날려버린다.
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //이렇게 readOnly옵션 넣어주면 값을 변경하는 작업까지 포함하는 작업을 위한 transaction의 없무를 줄여줘서 성능을 높여준다.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    //기능2. 회원 전체 조회


}
