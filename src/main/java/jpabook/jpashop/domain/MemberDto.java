package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @NotEmpty
    private String name;

    private Address address;

    public MemberDto(String name, String city, String street, String zipcode) {
        this.name = name;
        this.address = new Address(city, street, zipcode);
    }
}
