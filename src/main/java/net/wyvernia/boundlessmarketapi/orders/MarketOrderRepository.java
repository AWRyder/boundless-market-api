package net.wyvernia.boundlessmarketapi.orders;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MarketOrderRepository extends CrudRepository<MarketOrder, Integer> {
    List<MarketOrder> findAll();

    List<MarketOrder> findByPlanetName(String planetName);
    void removeAllByPlanetName(String planetName);
    List<MarketOrder> findByItemIdEqualsAndOrderTypeIs(String itemId, MarketOrderType type);
}
