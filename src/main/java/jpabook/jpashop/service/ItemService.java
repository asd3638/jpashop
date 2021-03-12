package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//Service는 코딩해보면 알겠지만 단순하게 repository 에 위임만 해주는 클래스이다.
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    //기본 초기 설정을 readOnly로 해놔서 값을 변경하는 경우가 있는 메소드의 경우 transactional의 옵션을 바꿔줘야함
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    //기본 초기 설정을 readOnly로 해놔서 값을 변경하는 경우가 있는 메소드의 경우 transactional의 옵션을 바꿔줘야함
    public void updateItem(Long itemId, Book param) {
        Book findItem = (Book) itemRepository.findOne(itemId);

        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
        findItem.setAuthor(param.getAuthor());
        findItem.setIsbn(param.getIsbn());
        //이러면 얘는 영속성 계층에서 관리하고 있는 애라
        //값이 변경되면 db에도 자동으로 관리 된다.
        //이게 영속성 계층에서의 변경 감지 기능을 사용한 수정 방식이다.
        //이러면 merge 안써도 된다
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

}
