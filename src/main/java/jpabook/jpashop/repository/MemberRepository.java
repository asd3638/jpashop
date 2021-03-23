package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    List<Member> findByName(String name);
}


/*    private final EntityManager em;

    //저장
    public void save(Member member){
        em.persist(member);
    }

    //id로 찾기
    public Member findById(Long id){
        Member member = em.find(Member.class, id);
        return member;
    }

    //name으로 찾기
    public List<Member> findByName(String name) {
        List<Member> members = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return members;
    }

    //전체 찾기
    public List<Member> findAll() {
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return members;
    }*/

