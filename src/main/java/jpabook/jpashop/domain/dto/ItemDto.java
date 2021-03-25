package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    @NotEmpty
    private String name;

    private int price;

    private int stockQuantity;
}
