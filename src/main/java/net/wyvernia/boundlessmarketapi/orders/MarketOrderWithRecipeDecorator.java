package net.wyvernia.boundlessmarketapi.orders;

import net.wyvernia.boundlessmarketapi.recipes.RecipeInput;

public class MarketOrderWithRecipeDecorator {
    private MarketOrder order;
    private RecipeInput recipe;

    public MarketOrderWithRecipeDecorator(MarketOrder order, RecipeInput recipe) {
        this.order = order;
        this.recipe = recipe;
    }

    public RecipeInput getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeInput recipe) {
        this.recipe = recipe;
    }

    public MarketOrder getOrder() {
        return order;
    }

    public void setOrder(MarketOrder order) {
        this.order = order;
    }
}
