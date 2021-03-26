package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
//상속에 있어 테이블들 관계 어떻게 설정할지 결정하는 부분
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//컴퓨터는 fk로 자식들이랑 관계 설정 되어있는데
//db만 봤을 때 어떤 자식 객체인지 아는게 좋으니까
//dtype이라는 이름의 컬럼을 추가로 생성해서 그 안에 자식 클래스 명을 담아두게 된다.
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
//item만 단독으로 table에 저장할 일이 있나 없나 판단해야 하는데
//있으면 그냥 class 로 없으면 abstract 로 선언해주면 된다.
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Integer id;

    private String name;

    private int price;

    private int stockQuantity;
}
