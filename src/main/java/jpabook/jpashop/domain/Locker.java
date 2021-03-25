package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Locker extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "lock_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "lock")
    private Member member;
}
