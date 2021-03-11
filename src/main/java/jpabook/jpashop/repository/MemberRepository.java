package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
//여러번 강조했지만 Repository 의 역할은 jpa써서 db랑 연결하는 것임.
//@PersistenceContext annotation 써서 EntityManager 만들어주고 persist, find, createQuery 등 EntityManager에서 제공하는 메소드 사용해서 db접근


//**이해하기 쉽게 설명하면 jpa를 쓰면 일단 repo측에서는 entity를 가지고 놀고 있는것임 그래서 Member.class로 엔티티 불러와서 그거 조회하고 찾고 난리를 치고
//나중에 transaction commit될 때 지지고 볶은 entity가 db에 반영되는 것이다. ?
//아직 확실하진 않음 이건 jpa공부하면서 다시 봐야할 듯**

@Repository
@RequiredArgsConstructor
//스프링 빈으로 등록 component를 내장하고 있어서 이거 하위에 있는 것까지 다 스캔의 대상이 된다.
public class MemberRepository {

    //이 어노테이션이 있으면 EntityManager을 직접 주입해준다.
    //근데 스프링 부트의 제공 기능으로 그냥 private EntityManager em; 를 final로 지정해주고 클래스에 @RequiredArgsConstructor 어노테이션 해주면 일관성 있게 코드를 작성할 수 있다.
    //원래는 꼭 @PersistenceContext로 어노테이션 설정하는게 맞긴해
    private final EntityManager em;

    //em.persist의 의미는 em이 영속성 컨텍스트에 member entity를 넣고 나중에 transaction이 commit되는 순간에 db에 그대로 반영된다.
    //중요한 점은 persist한다고 insert query가 바로 날라가는게 아니고
    //Transaction이 commit될 때 insert query가 날라가는 것이다.
    //jpa사용하면 그래서 Transaction이 중요하다.
    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        //em.createQuery에서 쓰는 sql문이랑 기존 sql문이랑 다른 것은 기존 sql문은 table에서 정보를 가져오는 것이라면
        //jpa는 Member라는 객체에서 정보를 가져오는 것이다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName (String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                //파라미터 바인딩!
                .getResultList();

    }
}
