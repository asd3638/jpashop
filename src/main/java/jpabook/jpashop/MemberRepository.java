package jpabook.jpashop;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
//다시 설명하면
//이 어노테이션 하면 @Component한거랑 같은 기능을 해서 componentScan의 대상이 된다.
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    //command랑 query는 분리하는게 좋아서 기능에 필요한 게 아닐면 return값을 만들지 않는게 좋은데
    //id정도는 return하면 조회할 수 있으니까 이렇게 만든다.
    public long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(long id) {
        return em.find(Member.class, id);
    }
}
