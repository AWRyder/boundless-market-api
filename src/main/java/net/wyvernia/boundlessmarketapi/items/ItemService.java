package net.wyvernia.boundlessmarketapi.items;

import net.wyvernia.boundlessmarketapi.orders.MarketOrder;
import net.wyvernia.boundlessmarketapi.orders.MarketOrderRepository;
import net.wyvernia.boundlessmarketapi.orders.MarketOrderType;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final MarketOrderRepository marketOrderRepository;

    public ItemService(ItemRepository itemRepository, MarketOrderRepository marketOrderRepository) {
        this.itemRepository = itemRepository;
        this.marketOrderRepository = marketOrderRepository;
    }

    public List<Item> getAllItems(){
        List<Item> items = itemRepository.findAll();
        List<MarketOrder> markerOrders = marketOrderRepository.findAll();
        for (Item item : items) {
            markerOrders.stream()
                    .filter(order -> order.getItemId().equals(item.getName()) && order.getQuantity() > 100 && order.getOrderType() == MarketOrderType.SELLING)
                    .min(Comparator.comparingDouble(MarketOrder::getCost))
                    .ifPresent(val -> item.setAverageCost(val.getCost()));

        }
        return items;
    }
}
