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
import java.util.List;

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

    public String getProfitableRecipes(){
        Iterable<Recipe> allRecipes = recipeRepository.findAll();
        return "ok";
    }
}
