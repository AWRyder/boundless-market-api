package net.wyvernia.boundlessmarketapi.items;

import com.fasterxml.jackson.databind.node.ObjectNode;
import net.wyvernia.boundlessmarketapi.orders.MarketOrderRepository;
import net.wyvernia.boundlessmarketapi.recipes.Recipe;
import net.wyvernia.boundlessmarketapi.recipes.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemRepository itemRepository;
    private final MarketOrderRepository marketOrderRepository;
    private final RecipeRepository recipeRepository;
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemRepository itemRepository, MarketOrderRepository marketOrderRepository, RecipeRepository recipeRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.marketOrderRepository = marketOrderRepository;
        this.recipeRepository = recipeRepository;
        this.itemService = itemService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profit")
    public ResponseEntity<List<Item>> getProfitableRecipes() {
        //TODO: Finish
        List<Item> items = itemService.getAllItems();

        List<Recipe> recipes = recipeRepository.findAll();
        for (Recipe recipe : recipes) {
            //recipe.getRecipeInputs().stream().map(in -> in.getInputItem().get(0) * in.getInputQuantity().get(2))
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bulk")
    public ResponseEntity<Long> bulkImport(@RequestBody ObjectNode node) {
        try {
            node.fieldNames().forEachRemaining(key -> {
                Item item = new Item();
                ObjectNode itemNode = (ObjectNode) node.get(key);
                item.setId(itemNode.get("id").asInt());
                item.setName(itemNode.get("name").asText());
                itemRepository.save(item);
            });

            return ResponseEntity.ok(0L);
        } catch (Exception e) {
            return ResponseEntity.ok(0L);
        }

    }
}
