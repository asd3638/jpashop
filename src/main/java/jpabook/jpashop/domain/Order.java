package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //member에 대한 정보를 가지고 있어야해
    //누가 주문했는지 알아야 하니까
    /*@Column(name = "member_id")
    private Long memberId;*/
    //위 방법은 객체를 사용했다기 보단 관계형 데이터 베이스 기반으로 코드를 짠 것임. 이걸 객체처럼 사용할 수 있게 바꿔줘야함.
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //역방향
    //게다가 비즈니스적으로 의미도 있는 양방향 연관관계이다.
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //연관관계 편의 메소드
    //member를 정하면 jpa규칙에 의해 양방향 연관관계를 가지고 있는 Member class의 order도 변하지만
    //메모리상에서의 로직 확인을 위해서는 이런 식으로 자동으로 양쪽 값 넣어주는게 중요하다.
    //근데 항상 무한루프 조심!
    //==연관 관계 메소드==//
    public void changeMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void changeDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
