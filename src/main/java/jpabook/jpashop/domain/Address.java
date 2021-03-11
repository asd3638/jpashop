package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
        //얘는 값을 막 생성할 수 없게 막아둬야 한다.
        //jap 스펙상 기본 생성자는 protected로 두는 것이 좋다.
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        //setter를 막고 생성자만 두면 처음에 생성할 때 값을 초기화하고 그 이후엔 맘대로 값을 변경할 수 없다.

    }
}
//이런건 setter 안 달아둬야 한다.
