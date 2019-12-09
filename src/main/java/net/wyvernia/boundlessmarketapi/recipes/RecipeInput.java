package net.wyvernia.boundlessmarketapi.recipes;

import net.wyvernia.boundlessmarketapi.items.Item;

import javax.persistence.*;
import java.util.List;

@Entity
public class RecipeInput {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @ManyToMany
    private List<Item> inputItem;

    @ElementCollection(targetClass=Integer.class)
    private List<Integer> inputQuantity;

    private String groupId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Item> getInputItem() {
        return inputItem;
    }

    public void setInputItem(List<Item> inputItem) {
        this.inputItem = inputItem;
    }

    public List<Integer> getInputQuantity() {
        return inputQuantity;
    }

    public void setInputQuantity(List<Integer> inputQuantity) {
        this.inputQuantity = inputQuantity;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
