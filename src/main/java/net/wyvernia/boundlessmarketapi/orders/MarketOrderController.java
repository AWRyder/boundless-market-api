package net.wyvernia.boundlessmarketapi.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/market-order")
public class MarketOrderController {

    private final Logger logger = LoggerFactory.getLogger(MarketOrderController.class);

    private final MarketOrderRepository orderRepository;
    private final MarketOrderService orderService;

    @Autowired
    public MarketOrderController(MarketOrderRepository orderRepository, MarketOrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<MarketOrder>> getOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<MarketOrder> createOrder(){
        MarketOrder order = new MarketOrder();
        order = orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bulk")
    public ResponseEntity<Long> bulkImport(@RequestParam("data") MultipartFile dataFile){
        try {
            Long affectedRows = orderService.bulkImport(dataFile);
            return ResponseEntity.ok(affectedRows);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return ResponseEntity.badRequest().body(0L);
        }

    }

}
