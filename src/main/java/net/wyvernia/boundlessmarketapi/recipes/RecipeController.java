package net.wyvernia.boundlessmarketapi.recipes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.wyvernia.boundlessmarketapi.items.Item;
import net.wyvernia.boundlessmarketapi.items.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeInputRepository recipeInputRepository;
    private final ItemRepository itemRepository;

    public RecipeController(RecipeRepository recipeRepository, RecipeInputRepository recipeInputRepository, ItemRepository itemRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeInputRepository = recipeInputRepository;
        this.itemRepository = itemRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bulk")
    public ResponseEntity<Long> bulkImport(@RequestBody ObjectNode node){
        try {
            ArrayNode recipesNode = (ArrayNode)node.get("recipes");
            recipesNode.forEach(recipeNode -> {
                Recipe recipe = new Recipe();
                //No need to treat the optional here as we don't mind if the whole operation fails.
                Item outputItem = itemRepository.findById(recipeNode.get("outputItem").asInt()).get();
                recipe.setOutputItem(outputItem);

                recipe.setRecipeInputs(new ArrayList<>());
                ArrayNode inputs = (ArrayNode) recipeNode.get("inputs");
                for (JsonNode input : inputs) {
                    RecipeInput recipeInput = new RecipeInput();
                    recipeInput.setInputQuantity(new ArrayList<>());
                    for (JsonNode inputQuantity : (ArrayNode) input.get("inputQuantity")) {
                        recipeInput.getInputQuantity().add(inputQuantity.asInt());
                    }

                    recipeInput.setInputItem( new ArrayList<>());
                    for (JsonNode inputItemsNode : (ArrayNode) input.get("inputItems")) {
                        int itemId = inputItemsNode.asInt();
                        Item inputItem = itemRepository.findById(itemId).get();
                        recipeInput.getInputItem().add(inputItem);
                    }

                    if (input.has("groupId")) {
                        recipeInput.setGroupId(input.get("groupId").asText());
                    }
                    recipeInputRepository.save(recipeInput);
                    recipe.getRecipeInputs().add(recipeInput);
                }

                recipe.setOutputQuantity(new ArrayList<>());
                for (JsonNode outputQnt : ((ArrayNode)recipeNode.get("outputQuantity")) ) {
                    recipe.getOutputQuantity().add(outputQnt.asInt());
                }

                recipe.setId(recipeNode.get("ID").asInt());
                recipe.setCraftXP(recipeNode.get("craftXP").asInt());
                if (recipeNode.has("machine")) {
                    recipe.setMachine(recipeNode.get("machine").asText());
                    recipe.setMachineLevel(recipeNode.get("machineLevel").asText());
                }
                recipe.setCanHandcraft(recipeNode.get("canHandCraft").asBoolean());
                recipe.setPowerRequired(recipeNode.get("powerRequired").asInt());
                recipe.setHeat(recipeNode.get("heat").asInt());
                recipe.setRecipeGroupName(recipeNode.get("recipeGroupName").asText());
                recipe.setKnowledgeUnlockLevel(recipeNode.get("knowledgeUnlockLevel").asInt());

                recipeRepository.save(recipe);
            });

            return ResponseEntity.ok(0L);
        } catch (Exception e){

            throw e;
        }
    }
}
