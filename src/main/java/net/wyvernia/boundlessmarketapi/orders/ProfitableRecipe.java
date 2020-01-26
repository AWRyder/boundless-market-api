package net.wyvernia.boundlessmarketapi.orders;

import net.wyvernia.boundlessmarketapi.recipes.Recipe;

import java.util.List;

public class ProfitableRecipe {

    private MarketOrder productToSell;

    private List<MarketOrderWithRecipeDecorator> reagentsToBuy;

    private Recipe recipe;

    private Double profit;

    public ProfitableRecipe(MarketOrder productToSell, List<MarketOrderWithRecipeDecorator> reagentsToBuy, Recipe recipe, Double profit) {
        this.productToSell = productToSell;
        this.reagentsToBuy = reagentsToBuy;
        this.recipe = recipe;
        this.profit = profit;
    }

    public MarketOrder getProductToSell() {
        return productToSell;
    }

    public List<MarketOrderWithRecipeDecorator> getReagentsToBuy() {
        return reagentsToBuy;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Double getProfit() {
        return profit;
    }
}
