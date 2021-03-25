package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.dto.ItemDto;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/jpashop/item", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity queryitems() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity createItem(@RequestBody @Valid ItemDto itemDto) {
        return ResponseEntity.ok(itemRepository.save(modelMapper.map(itemDto, Item.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity queryItem(@PathVariable Integer id) {
        Item item = itemRepository.findById(id).get();
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateItem(@PathVariable Integer id, @RequestBody @Valid ItemDto itemDto) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Item beforeItem = optionalItem.get();
        beforeItem.setName(itemDto.getName());
        beforeItem.setPrice(itemDto.getPrice());
        beforeItem.setStockQuantity(itemDto.getStockQuantity());
        itemRepository.save(beforeItem);

        return ResponseEntity.ok(itemRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable Integer id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        itemRepository.deleteById(id);

        return ResponseEntity.ok(itemRepository.findAll());
    }
}
