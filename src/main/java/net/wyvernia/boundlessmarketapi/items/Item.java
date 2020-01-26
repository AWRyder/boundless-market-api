package net.wyvernia.boundlessmarketapi.items;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Item {

    @Id
    private Integer id;

    private String name;

    @Transient
    private Double averageCost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(Item.class)
    public Double getAverageCost() {
        return averageCost;
    }

    @JsonSetter
    public void setAverageCost(Double averageCost) {
        this.averageCost = averageCost;
    }
}
