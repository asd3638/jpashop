package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    //왜 비즈니스 로직이 여기 있을까 이 클래스 객체를 가져와서 바꾸면 될텐데
    //변수가 선언된데에서 변수에 대한 값 변경 비즈닉스 로직을 집합체에 직접 넣어주는게 응집성이 있어서 더 좋은 방법이다.
    //setter를 넣어놓긴 했지만 좋은 코드는 setter로 아무 곳에서나 도메인 변수에 대한 값 변경이 가능한게 아니라
    //setter는 막아두고 도메인 안에 변수에 대한 값 설정을 할 수 있는 비즈니스 로직 메소드를 직접 생성해서 사용하는 것이다.
    //이게 가장 객체 지향적인 방법이다.
    public void addStock(int Quantity) {
        this.stockQuantity += Quantity;
    }
    public void removeStock(int Quantity) {
        int restStock = this.stockQuantity - Quantity;
        if (restStock >= 0) {
            this.stockQuantity = restStock;
        } else {
            throw new NotEnoughStockException("need more stock");
        }
    }
}
