package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    //내장 타입을 포함하고 있다고 명시해준다.
    //Embeddable 이나 Embedded 둘 중 하나만 해도 되긴 하는데 그냥 둘 다 한다.
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    //이러면 order 테이블에 있는 member라는 변수에 의해 업데이트 된다는 뜻이다.
    //order의 member가 변하면 이 부분도 자동으로 업데이트 되게 설정해준 것이다.
    //member에 의해 order가 변해야지 라고 생각할 수 있지만
    //외래키를 가진 애가 주인이 되고 그 나머지가 이 @OneToMany(mappedBy = "member") 속성을 가지게 설정해줘야한다.
    //여기에 값을 넣는다고 해서 fk가 변하진 않는다 얘는 말그대로 거울!
    private List<Order> orders = new ArrayList<>();
    //컬렉션은 한 번 생성하면 hibernate가 자기가 관리하기 쉬운 컬렉션 형태로 바꾸어버린다.
    //그러니까 가급적이면 컬렉션은 한번 선언하고 초기화하면 그 값을 바꾸거나 설정을 바꾸려고 들면 안된다.
    //그냥 hibernate가 지정한 그대로 사용하는 것이 편하다. (내가 바꾸면 그 메카니즘으로 동작 안할 수 있으니까)

}
