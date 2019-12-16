package net.wyvernia.boundlessmarketapi.orders;

import net.wyvernia.boundlessmarketapi.recipes.Recipe;
import net.wyvernia.boundlessmarketapi.recipes.RecipeRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MarketOrderService {
    private final Logger logger = LoggerFactory.getLogger(MarketOrderService.class);

    private final MarketOrderRepository orderRepository;
    private final RecipeRepository recipeRepository;

    public MarketOrderService(MarketOrderRepository orderRepository, RecipeRepository recipeRepository) {
        this.orderRepository = orderRepository;
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    public Long bulkImport(MultipartFile dataFile) throws Exception {
        Reader fileReader = new InputStreamReader(dataFile.getInputStream());
        CSVParser csvRecords = new CSVParser(fileReader, CSVFormat.DEFAULT);
        Long importedRowsCounter = 0L;
        List<CSVRecord> listedCsvRecords = csvRecords.getRecords();
        if (listedCsvRecords.size() == 0) {
            return 0L;
        }
        //Currently this only supports files with data from one planet at a time. Therefore this shortcut is possible.
        String planetName = listedCsvRecords.stream().map(record -> record.get(2)).findFirst().orElseThrow();
        orderRepository.removeAllByPlanetName(planetName);

        //

        for (CSVRecord csvRecord : listedCsvRecords) {
            try {
                MarketOrder order = new MarketOrder();
                order.setOrderDate( LocalDateTime.parse(csvRecord.get(0), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") ));
                order.setOrderType( MarketOrderType.parse(csvRecord.get(1)));
                order.setPlanetName(csvRecord.get(2));
                order.setItemId(csvRecord.get(3));
                order.setGuild(csvRecord.get(4));
                order.setBeacon(csvRecord.get(5));
                order.setQuantity(Long.parseLong(csvRecord.get(6)));
                order.setCost(Double.parseDouble(csvRecord.get(7)));
                order.setPatrons(Integer.parseInt(csvRecord.get(8)));
                orderRepository.save(order);
                importedRowsCounter++;
            } catch (Exception e) {
                // Continue as normal
                logger.warn(e.getMessage());
            }
        }

        return importedRowsCounter;
    }

    @Transactional
    public String getProfitableRecipes(){
        Iterable<Recipe> allRecipes = recipeRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        allRecipes.forEach(recipe -> {
            String name = recipe.getOutputItem().getName();
            List<MarketOrder> outputItemOrders = orderRepository.findByItemIdEqualsAndOrderTypeIs(name, MarketOrderType.BUYING);
            // No one is buying the output item. Forget recipe.
            Optional<MarketOrder> max = outputItemOrders
                    .stream()
                    .max(Comparator.comparingDouble(order -> order.getCost() * order.getQuantity()));
            if (max.isEmpty()) {
                return;
            }
            MarketOrder bestBuyOrder = max.get();
            List<MarketOrder> inputOrders = new ArrayList<>();

            recipe.getRecipeInputs().forEach(recipeInput -> {
                List<MarketOrder> inputItemOrders = orderRepository.findByItemIdEqualsAndOrderTypeIs(recipeInput.getInputItem().get(0).getName() , MarketOrderType.SELLING);
                Optional<MarketOrder> bestSellOrder = inputItemOrders.stream()
                        .filter(order -> order.getQuantity() < recipeInput.getInputQuantity().get(0))
                        .min(Comparator.comparingDouble(order -> order.getCost() * order.getQuantity()));
                bestSellOrder.ifPresent(inputOrders::add);
            });
            if (recipe.getRecipeInputs().size() != inputOrders.size()){
                return;
            }

            // MarketOrder bestBuyOrder = orders.sort( order );
            stringBuilder.append("\nCraft a ")
                    .append(recipe.getOutputItem().getName())
                    .append(" x")
                    .append(bestBuyOrder.getQuantity())
                    .append(" to sell for ")
                    .append(bestBuyOrder.getCost() * bestBuyOrder.getQuantity())
                    .append(" on ")
                    .append(bestBuyOrder.getPlanetName())
                    .append("@")
                    .append(bestBuyOrder.getBeacon());
            for (MarketOrder inputOrder : inputOrders) {
                stringBuilder.append("\n\tBuy "+ inputOrder.getItemId() + " x??? for" + inputOrder.getCost() + " on " + inputOrder.getPlanetName() + "@" + inputOrder.getBeacon()  );
            }
            stringBuilder.append("\n\n\n");

        });

        return stringBuilder.toString();
    }
}
