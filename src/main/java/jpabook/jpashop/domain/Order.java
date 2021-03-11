package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
//얘는 테이블 이름 정해준 이유는 관례로 클래스 명인 Order가 테이블 명으로 설정되기 때문
//sql 구문 중에서 orderby랑 헷갈려서 orders라고 따로 테이블 명을 지정해준다.
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //이제 member와의 관계를 세팅해야 하는데
    //order랑 member은 다대일의 관계 이다. 즉 member 한 개에 order여러개 올 수 있다.
    //연관 관계 있으면 이렇게 적어줘야 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    //두 테이블이 합쳐지려면 기준이 되는 컬럼이 존재해야 하는데 그 기준 컬럼을 명시하는 어노테이션이다.
    //외래키의 이름이 member_id가 된다.
    //근데 중요한 것은 지금 테이블 간의 관계가 양방향 관계이다.
    //양방향인지 아닌지 알 수 있는 방법은 두 테이블에서 관리하는 데이터가 겹치면 양방향 관계인 것이다.
    //테이블을 보면 Member에서 orders리스트로 order를 가지고 있고 order에서도 member변수로 member를 가지고 있다.
    //그럼 이 두 변수 중 어디를 변화시켜야 하는지 jpa가 구분하기 어렵다.

    //테이블 관계에 있어서 중요한게 주인 관계를 파악하는 것인데 외래키를 가진 애가 주인이 된다.
    //여기선 그럼 Member가 주인 테이블이겠다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    //OrderItems라는 테이블과 연관 관계가 설정되는데 하나의 Order당 OrderItems가 여러개 올 수 있는 일대다 관계니까 일단 OneToMany 어노테이션 설정하고
    //주인 관계에 있어서는 fk키를 가지고 있는 OrderItems가 주인이기 때문에 얘는 걔가 변화하면 자동으로 업데이터 되게 설정하면 된다.
    //이게 변한다고 OrderItems가 변하진 않아

    //cascade 옵션은 만약 orderItems 들이 저장되는 상황이라고 가정하면
    //원래 로직대로 라면
    // persist(item1)
    // persist(item1)
    // persist(item1)
    // persist(order)
    //이런식으로 연관된 컬렉션 데이터들이 저장되고 그걸 담고 있던 데이터가 저장되는데 cascade옵션 쓰면
    // persist(order) 만 해줘도 알아서 위 과정을 진행해준다는 뜻이다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;
    //자바 8 부터는 DATETYPE에 대한 어노테이션 매핑이 필요했는데 이제 자동으로 지원
    //이게 테이블로 보면 다 컬럼에 해당하는데 스프링은 자동적으로 이 이름을
    // 카멜 -> 언더바
    // 대문자 - > 소문자
    //규칙을 적용시켜 바꾼다.
    //따라서 orderDate 는 order_date로 바뀌게 된다.

    @Enumerated(EnumType.STRING)
    //이건 enum 썼다고 명시 해야 하는데
    //중요한 것은 EnumType 지정할 때 default 인 ORDINARY 쓰면 숫자로 매핑해서 인덱싱 밀리면 큰일난다.
    //STIRNG 으로 매핑 할 수 있게 직접 지정해주는게 좋다.
    private OrderStatus status;

    //==연관 관계 메서드==//
    //이런 연관 관계 메서드는 핵심적으로 컨트롤 하는 쪽에 넣어두는 것이 좋다.
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
