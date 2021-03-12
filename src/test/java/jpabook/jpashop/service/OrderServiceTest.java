package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {
    @Autowired EntityManager em;
    //얘는 원래 테스할 때는 필요 없는데 db에 직접 값 넣어보고 싶어서 생성
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();

        Item book = createBook(10000, 10, "test_book1");

        int orderCount = 2;
        //when
        Long saveOrderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order saveOrder = orderRepository.findOne(saveOrderId);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, saveOrder.getStatus());
        assertEquals("주문한 상품의 수가 정확해야 한다.", 1, saveOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다", 10000 * orderCount, saveOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어들어야 한다", 8, book.getStockQuantity());
    }

    private Item createBook(int price, int stockQuantity, String book_name) {
        Item book = new Book();
        book.setName(book_name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("test1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Item book = createBook(10000,10 ,"test_book2");
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);
        //여기서 주문 취소를 했어
        //then
        Order saveOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소 시 상태는 cancel이 되어야 한다.", OrderStatus.CANCEL, saveOrder.getStatus());
        assertEquals("주문 취소 시 재고는 복구 되어야 한다.", 10, book.getStockQuantity());


    }

    @Test(expected = NotEnoughStockException.class)
    public void 주문_수량_초과() throws Exception{
        //given
        Member member = createMember();

        Item book = createBook(10000, 10, "test_book1");
        //when
        int orderCount = 15;
        orderService.order(member.getId(), book.getId(), orderCount);
        //orderCount를 재고 StockQuantity 보다 많이 줬기 때문에 예외가 발생해서 fail로 넘어가지 않고 테스트 종료될 것임.

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

}