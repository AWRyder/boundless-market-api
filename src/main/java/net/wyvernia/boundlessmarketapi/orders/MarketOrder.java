package net.wyvernia.boundlessmarketapi.orders;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "market_order")
public class MarketOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    private LocalDateTime orderDate;

    @Enumerated
    private MarketOrderType orderType;

    private String planetName;

    private String itemId;

    private String guild;

    private String beacon;

    private Long quantity;

    private Double cost;

    private Integer patrons;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public MarketOrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(MarketOrderType orderType) {
        this.orderType = orderType;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getBeacon() {
        return beacon;
    }

    public void setBeacon(String beacon) {
        this.beacon = beacon;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getPatrons() {
        return patrons;
    }

    public void setPatrons(Integer patrons) {
        this.patrons = patrons;
    }
}
