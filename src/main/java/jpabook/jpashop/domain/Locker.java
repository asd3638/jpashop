package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Locker extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "lock_id")
    private Integer id;

    private String name;

    @OneToOne(mappedBy = "lock", fetch = FetchType.LAZY)
    private Member member;
}
