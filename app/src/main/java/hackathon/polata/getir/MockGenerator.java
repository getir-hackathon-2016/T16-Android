package hackathon.polata.getir;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.network.model.ProductCategory;

/**
 * Created by polata on 20/02/2016.
 */
public class MockGenerator {

    private static final String MOCK_FORMAT = "%1$s_%2$s";
    private static final int MAX_COUNT = 10;

    private static ArrayList<ProductCategory> productCategories = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();

    public MockGenerator() {
        generateProductCategories();
        generateProducts();
    }

    private void generateProductCategories() {
        for (int i = 0; i < MAX_COUNT; i++) {
            productCategories.add(new ProductCategory(i, String.format(MOCK_FORMAT, "Kategori", i)));
        }
    }

    private void generateProducts() {
        for (int i = 0; i < MAX_COUNT; i++) {
            for (int j = 0; j < MAX_COUNT; j++) {
                products.add(new Product(productCategories.get(j),
                        String.format(MOCK_FORMAT, "Urun", i),
                        i,
                        i * generateRandom(MAX_COUNT), new BigDecimal(MAX_COUNT * j)));
            }
        }
    }

    private int generateRandom(int threshold) {
        final Random random = new Random();
        return random.nextInt(threshold);
    }

    public ArrayList<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
