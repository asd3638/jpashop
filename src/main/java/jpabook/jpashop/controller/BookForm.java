package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;
    //이건 수정을 위해 필요하다.

    private String name;
    private int price;
    private int stockQuantity;
    //여기가지는 상품의 공통 속성

    private String author;
    private String isbn;
}
