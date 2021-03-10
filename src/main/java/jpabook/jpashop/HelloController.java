package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        //Model 개념이 계속 헷갈리는데 이거 그냥 html로 보낼 때 model에다가 값 달고 갈 수 있는거임.
        //정리하면 화면에 보이고 싶은 데이터를 담아서 return 할 때 같이 보내준다.
        //html에서 이 데이터를 attributeName으로 찾아서 사용하겠지.
        model.addAttribute("data", "hello!!!!!");
        return "hello";
        //자동으로 main/resources/templates에 가서 해당하는 이름과 같은 html 파일을 찾아서 렌더링 진행
    }

}
