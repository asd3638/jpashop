package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

//이미 Member 클래스 만들어놨는데 또 클래스를 만드는 이유는
//domain의 Member에는 회원 가입을 위해 필요한 변수 외에도 다른 변수들도 선언되어 있어서
//안 맞기 때문에
//Form 태그에 맞게 그냥 하나 만들어 주는게 좋다.
//화면에서 넘어오는거랑 도메인에서 넘어오는거 클래스 맞출 필요 없음.
@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
