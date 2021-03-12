package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String create(BookForm form) {
        //받아온 값으로 책 object다 만들었으면 이제 db연동하면 된다.
        //실무에서는 이렇게 entity 직접 가져다 쓰는 방식은 선호되지 않는다.
        //Order 에서 했던 것 처럼 setter는 막아두고(생성자 protected로 해줘서) 내부 생성 메소드를 만들어둬서
        //값을 파라미터에만 넣으면 객체 생성해서 반화해주는 방법 쓰는게 좋다.
        itemService.saveItem(Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn()));

        return "redirect:/items";
    }

    @GetMapping("items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    //변경될 수 있는 값이 분기에 들어갈 때는 일단 이렇게 지정해두고 post로 들어오는 정보 넣어준다?
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        //setId제대로 안하면 수정 못함..
        //이건 들고 다녀야 하는 정보니까 꼭 뷰에도 넘겨주고 뷰에서도 받아와야 한다.
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    //변경될 수 있는 값이 분기에 들어갈 때는 일단 이렇게 지정해두고 post로 들어오는 정보 넣어준다?
    public String updateItem(@ModelAttribute("form") BookForm form) {
        //값을 업데이트 하기 위해 controller 계층에서 어설프게 entity만들지 말자
        //form에서 직접 끌어와서 사용하던가
        //아님 DTO 또 새롭게 생성해서 만들자
        Book book = new Book();
        book.setId(form.getId());
        //수정이 아니라 새로 생성하는거라면 id값을 지정하지 않아
        //서비스 계층에서 이 id의 유뮤에 따라 생성인지 수정인지 판단하기 때문에 이게 매우 중요
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        //여기서 book에 아무리 값을 바꿔봐도 book 객체는 transactional에 의해 관리되는 영속성 계층에 포함되지 않기 때문에
        //db에 변경이 반영되진 않는다.
        //방법은 두가지 인데
        //1번째는 merge 이용하는 것
        //2번째는 변경 감지 기능을 이용하는 것

        //추천 하는 방식은 후자이며
        //아래 코드처럼 service 계층에서 memberRepository 에서 영속성 계층에서 관리되는 entity를 가져와서 그 값을 직접 수정하는 것이다.
        //위에서 수정한 것과는 다르게 영속성 계층에서 관리하고 있는 애가 수정한 것이라서 transactional 이 commit하고 flush할 때 값의 변경이 감지돼서 db에 새롭게
        //update 쿼리 날려서 반영을 해준다.

        //근데 후자를 선택해야 하는 이유는
        //merge는 전자의 방식을 이용해서 테이블의 데이터 전부를 변경하는 것이다.
        //선택해서 변경할 수 없기 때문에 만약 변경을 지정해주지 않으면 그 변수 값은 null로 수정되어 버린다.
        //하나라도 데이터를 그렇게 초기화 해버리면 안되기 때문에
        //가급적으로 데이터를 선택해서 변경할 수 있는 변경 감지 기법을 사용하는 것을 권장한다.

        itemService.updateItem(form.getId(), book);

        return "redirect:/items";
    }



}
