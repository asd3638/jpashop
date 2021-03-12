package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//기본 생성자는 protected로 막을 수 있는 어노테이션
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

    //==생성 메서드==//
    //OrderItem 객체 생성해서 파라미터에 하나씩 값 넣으면 한번에 생성해주는 역할
    //이것 저것 값 넣을 때마다 찾아다닐 필요가 없다.
    //orderItem 생성하면서 재고는 줄여야 한다.
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }
}
