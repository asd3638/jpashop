package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        //이건 view로 넘어갈 때 가지고 가는 데이터인데 지금 데이터에 어떤 정보도 담지 않았으니까
        //그냥 빈 껍데기인데 이거라도 일단 가지고 간다.
        return "members/createMemberForm";
    }
    //뷰 단에서 입력한 값 받아서 처리하는 부분
    //get은 return보고 뷰로 가서 렌더링 할 수 있게 하는 거고
    //post는 웹에서 데이터 받아서 처리하는 부분
    //데이터를 받아오는 통을 파라미터로 받아
    //현재 예제에서는 MemberForm이 데이터 받아오는 통이다.
    //얘는 값 처리만 하는데 무슨 return값이 필요한가 생각하겠지만
    //처리하고 나서 어디로 갈 지 return이 정해줘야 한다.
    @PostMapping("members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        if (result.hasErrors()) {
            //이름 제대로 입력 안하면 다시 입력하는 페이지로 넘어갈거야
            //근데 result즉 오류까지도 담고 화면으로 넘어간다.
            //view에서 확인하면 알겠지만
            //MemberForm에서 @NotEmpty에서 옵션으로 message에 넣어뒀던 거 출력해주면서 오류 메세지 보여준다.
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode()));

        memberService.join(member);
        return "redirect:/";
    }
    //MemberForm 에 name변수에 notempty 걸어놨고 여기서 @Valid 어노테이션 주면 name이 empty면
    //오류 페이지가 나오게 된다.
    //근데 사실 오류 페이지가 나오는게 아니라 에러 종류에 따라 해결책을 제시해주는 쪽으로 가는게 맞음
    //그렇게 할 수 있게 해주는게 BindingResult
    //원래 파라미터쪽에서 오류가 있으면 해당 메소드를 실행하지 않고 바로 오류 페이지로 빠져 버리는데
    //BindingResult가 있으면 오류 있어도 해당 메소드를 실행하게 돼.
    //메소드 안에 오류가 난 상황에 대한 처리를 해주면 된다.

    @GetMapping("members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        //post받을 때는 MemberForm이용했는데 여기선 entity를 그대로 넘겼다.
        //사실 이러면 안된다.
        //얘도 entity에서 받아서 다른 객체로 다시 저장해두고 화면에 넘겨야 한다. (DTO나 MemberForm)
        //그리고 api쓸 때에는 무.조.건 후자 방식으로 해야한다.

        model.addAttribute("members", members);
        //members라는 키에 members라는 리스트를 넣은 것
        return "members/memberList";
    }
}
