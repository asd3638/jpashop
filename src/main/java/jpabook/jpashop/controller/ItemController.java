package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.dto.ItemDto;
import jpabook.jpashop.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/jpashop/item", produces = MediaTypes.HAL_JSON_VALUE)
public class ItemController {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemController(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity queryitems() {
        List<Item> items = itemRepository.findAll();

        List<ItemDto> itemDtos = items.stream().map(i -> modelMapper.map(i, ItemDto.class)).collect(Collectors.toList());

        return ResponseEntity.ok(itemDtos);
    }

    @PostMapping
    public ResponseEntity createItem(@RequestBody @Valid ItemDto itemDto) {

        itemRepository.save(modelMapper.map(itemDto, Item.class));

        return ResponseEntity.ok(itemDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity queryItem(@PathVariable Integer id) {
        Item item = itemRepository.findById(id).get();
        ItemDto itemDto = modelMapper.map(item, ItemDto.class);

        return ResponseEntity.ok(itemDto);
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

        ItemDto changedItemDto = modelMapper.map(beforeItem, ItemDto.class);

        return ResponseEntity.ok(changedItemDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable Integer id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        itemRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
