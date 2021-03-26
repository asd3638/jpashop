package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.dto.OrderDto;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/jpashop/order", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity queryOrders() {
        List<OrderDto> orderDtos = orderRepository.findAll().stream().map(o -> modelMapper.map(o, OrderDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(orderDtos);
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody @Valid OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        orderRepository.save(order);
        return ResponseEntity.ok(orderDto);
    }



}
