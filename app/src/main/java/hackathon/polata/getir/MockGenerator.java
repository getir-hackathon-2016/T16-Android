package hackathon.polata.getir;

import android.content.Context;

import java.util.ArrayList;
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

    /**
     * Constructor.
     *
     * @param context context
     */
    public MockGenerator(Context context) {
        generateProductCategories(context);
        generateProducts(context);
    }

    /**
     * Generate mock product categories.
     *
     * @param context context
     */
    private void generateProductCategories(Context context) {
        for (int i = 0; i < MAX_COUNT; i++) {
            productCategories.add(new ProductCategory(i, String.format(MOCK_FORMAT,
                    context.getResources().getString(R.string.category), i)));
        }
    }

    /**
     * Generate mock products.
     *
     * @param context context
     */
    private void generateProducts(Context context) {
        for (int i = 0; i < MAX_COUNT; i++) {
            for (int j = 0; j < MAX_COUNT; j++) {
                products.add(new Product(productCategories.get(j),
                        String.format(MOCK_FORMAT,
                                context.getResources().getString(R.string.product),
                                MAX_COUNT),
                        i + j + "",
                        i * generateRandom(MAX_COUNT), (MAX_COUNT * j * 0.9f)));
            }
        }
    }

    /**
     * Generate a random value.
     *
     * @param threshold max threshold
     * @return value
     */
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
