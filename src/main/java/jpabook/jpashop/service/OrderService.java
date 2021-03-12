package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    //이건 식으로 의존 관계 주입이 꽤 많이 되는 상황에서 @RequiredArgsConstructor의 역할이 빛난다.

    /**
     * 주문 (데이터 변경하는 거니까 Transactional 설정 바꿔줘야해)
     * */

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        //왜 클래스 안에 한꺼번에 생성 메소드 만드는지 알 수 있는 부분

        /*
        * 만약 이렇게 안 쓰면
        * OrderItem orderItem = new OrderItem;
        * orderItem.setItem(item);
        * orderItem.setItemPrice(item.getPrice());
        * 이런 식으로 값 하나 하나 다 채워 넣어줘야 하는데 생성 메소드 미리 만들어 두면
        * 어떤 값이 필요한지 파라미터로 딱딱 지정해서 넘겨서 코드 한 줄로 값 다 넣을 수 있으니까
        * 생산성이 좋다고 할 수 있고 코드 반복도 줄일 수 있다.
        * 그리고 이런 식으로 생성 메소드 만들어 놨으면 변수에 맘대로 접근해서 이 방식 이외의 방식으로 값을 넣는 것을 막아야 한다.
        * protected 기본 생성자 만들어 주면 OrderItem orderItem = new OrderItem(); 했을 때 빨간줄 그어지겠지
        * */

        //주문 생성성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        //이때 order만 저장되고 나머지 item이나 delivery에 대한 데이터도 쿼리 함께 날라가는지 의문이 드는데
        //도메인 설정할 때 (테이블 관계 지정할 때) 옵션에 cascade.ALL이거 줬으면
        //한 테이블에 대한 저장 수정이 해당 테이블에도 연결돼서 지정된다.
        //근데 cascade어디까지 설정할 건지에 대한 고민은 해봐야 함
        //만약 order 가 orderitem들을 관리하고 delivery를 관리하는 상황이면
        //order domain에서 orderitem과 delivery에 cascade줘도 된다.
        //만약 다른 애들도 orderitem이나 delivery를 참조해서 사용하고 있는 상황이면 이런 식으로
        //order에서 cascade쓰면 안된다.

        return order.getId();

    }

    /**
     * 검색
     * */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }

    /**
     * 주문 취소 (데이터 변경하는 거니까 Transactional 설정 바꿔줘야해)
     * */
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findOne(id);

        order.cancel();
        //service계층에서는 cancel하나만 날렸지만
        //cancel()메소드로 인해 변경 되는 값들이 있다면 변경내역감지에 의해
        //하나 하나 확인해서 모두 update 쿼리 다시 보내준다.
    }


}
