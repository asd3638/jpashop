package jpabook.jpashop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.domain.dto.MemberDto;
import jpabook.jpashop.repository.MemberRepository;
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
public class MemberServiceTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Before
    public void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    public void 회원가입() throws Exception{
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