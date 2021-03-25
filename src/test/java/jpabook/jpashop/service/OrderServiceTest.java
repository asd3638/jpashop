/*
package jpabook.jpashop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.dto.MemberDto;
import jpabook.jpashop.domain.dto.OrderDto;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class OrderServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    public void queryOrdersTest() throws Exception{
        //given
        MemberDto memberDto = new MemberDto("member1", "city1", "street1", "zipcode1");
        Member member = memberRepository.save(modelMapper.map(memberDto, Member.class));
        Item item = Item.builder()
                .name("restApi")
                .price(10000)
                .stockQuantity(20)
                .build();
        itemRepository.save(item);

        OrderDto orderDto = new OrderDto(1, 1, 2);

        //when
        mockMvc.perform(post("/jpashop/order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(orderDto)))
                .andDo(print());
        //then
    }

    @Test
    public void queryOrderTest() throws Exception{
        //given
        MemberDto memberDto = new MemberDto("member1", "city1", "street1", "zipcode1");
        //when
        mockMvc.perform(post("/jpashop/member")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(memberDto)))
                .andDo(print());
        //then
    }

    @Test
    public void createOrderTest() throws Exception{
        //given
        MemberDto memberDto = new MemberDto("member1", "city1", "street1", "zipcode1");
        //when
        mockMvc.perform(post("/jpashop/member")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(memberDto)))
                .andDo(print());
        //then
    }
}
*/
