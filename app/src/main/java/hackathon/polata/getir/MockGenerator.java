package hackathon.polata.getir;

import android.content.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import hackathon.polata.getir.network.model.AccessToken;
import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.network.model.ProductCategory;
import hackathon.polata.getir.network.model.User;

/**
 * Created by polata on 20/02/2016.
 */
public class MockGenerator {

    private static final String MOCK_FORMAT = "%1$s_%2$s";
    private static final int MAX_COUNT = 10;

    private static ArrayList<ProductCategory> productCategories = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();

    public AccessToken login(User user) {
        return new AccessToken("4213423423dadassda");
    }

    public MockGenerator(Context context) {
        generateProductCategories(context);
        generateProducts(context);
    }

    private void generateProductCategories(Context context) {
        for (int i = 0; i < MAX_COUNT; i++) {
            productCategories.add(new ProductCategory(i, String.format(MOCK_FORMAT,
                    context.getResources().getString(R.string.category), i)));
        }
    }

    private void generateProducts(Context context) {
        for (int i = 0; i < MAX_COUNT; i++) {
            for (int j = 0; j < MAX_COUNT; j++) {
                products.add(new Product(productCategories.get(j),
                        String.format(MOCK_FORMAT,
                                context.getResources().getString(R.string.product),
                                i + j),
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
