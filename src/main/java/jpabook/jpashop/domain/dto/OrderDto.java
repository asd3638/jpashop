package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Integer memberId;

    private LocalDateTime orderDate;

    private OrderStatus status;

    private Address address;

}
