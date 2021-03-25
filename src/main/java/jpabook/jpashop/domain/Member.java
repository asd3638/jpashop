package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Entity
@Getter
@Setter
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //일단 단방향으로 봐
    /*@OneToMany
    private List<Order> orders = new ArrayList<>();*/

    private String name;

    @Embedded
    private Address address;

    //역방향이자 양방향 연결관계 설정
    //연관 관계 메소드 만들어야해 (fk키 가진 주인이 뭔지 판단하고 그 주인이 값이 변경될 때 얘는 그냥 반영만 되는거라고 이해)
    //이 경우에서는
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "lock_id")
    private Locker lock;

}
