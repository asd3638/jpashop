package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "parent_id")
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "parent")
    private List<Child> children = new ArrayList<>();

    //==양방향연관관계메소드==//
    public void changeParent(Child child) {
        children.add(child);
        child.setParent(this);
    }
}
