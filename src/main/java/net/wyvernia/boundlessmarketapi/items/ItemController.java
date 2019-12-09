package net.wyvernia.boundlessmarketapi.items;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bulk")
    public ResponseEntity<Long> bulkImport(@RequestBody ObjectNode node){
        try {
            node.fieldNames().forEachRemaining(key -> {
                Item item = new Item();
                ObjectNode itemNode = (ObjectNode)node.get(key);
                item.setId(itemNode.get("id").asInt());
                item.setName(itemNode.get("name").asText());
                itemRepository.save(item);
            });

            return ResponseEntity.ok(0L);
        } catch (Exception e){
            return ResponseEntity.ok(0L);
        }

    }
}
