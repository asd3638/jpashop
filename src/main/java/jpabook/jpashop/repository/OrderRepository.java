package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //jpql을 문자로 생성하는 것은 너무 복잡하고 버그 잡기도 어렵고 실수가 나올 확률도 너무 높다.
    //mybatis쓰는 이유가 이런 동적 쿼리들을 해결하는데 간단하기 때문

    /*public List<Order> findAll(OrderSearch orderSearch) {
        QOrder order = QOrder.order;

    }*/
}
