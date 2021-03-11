package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    //컬럼명을 지정하는 이유는 테이블마다 id라는 컬럼이 있어서 중복으로 생성되는 것을 막고 나중에 외래키로 쓰든 할 때 테이블 명 지정해주기 위함이다.
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //orderitem입장에서는 order을 한개 가질 수 있는 다대일 관계임
    //테이블은 구조 상 일대다 관계에서 다에 해당하는 부분에 fk가 들어가게 된다.
    //이 fk를 기준으로 테이블을 합치는 JoinColumn어노테이션을 사용한게 된다.
    //얘가 fk를 가지니까 order와 orderitem간의 연관관계의 주인은 orderitem이 된다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;



}
