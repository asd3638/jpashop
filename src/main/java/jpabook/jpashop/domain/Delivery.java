package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    //One to One 인 경우는 fk를 어디에 놓든 상관이 별로 없다.
    //근데 배민 선생님은 주로 엑세스를 많이 하는 테이블에 fk를 넣으시는 편이다.
    //여기선 order table에 대한 엑세스가 더 많기 때문에 fk를 거기에 둔다.
    private Order order;

    private DeliveryStatus status; //READY, COPM

    @Embedded
    private Address address;
}
