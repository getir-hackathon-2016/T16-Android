package hackathon.polata.getir.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.network.model.ProductCategory;
import okhttp3.Interceptor;

/**
 * Created by polata on 20/02/2016.
 */
public final class ProductUtil {

    /**
     * Default constructor disabled.
     */
    private ProductUtil() {
    }

    public static BigDecimal calculateCartTotal(List<Product> products) {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : products) {
            total = total.add(product.getAmount().setScale(2));
        }

        return total;
    }

    public static String getCartTotalAsString(List<Product> products) {
        return calculateCartTotal(products).toString();
    }

    public static ArrayList<ProductCategory> getProductCategories(ArrayList<Product> products) {
        final HashMap<Integer, ProductCategory> hashMap = new HashMap<>();

        for (Product product : products) {
            if (!hashMap.containsKey(product.getCategory().getCategoryId())) {
                hashMap.put(product.getCategory().getCategoryId(), product.getCategory());
            }
        }

        final ArrayList<ProductCategory> categories = new ArrayList<>();
        for (Integer key : hashMap.keySet()) {
            categories.add(hashMap.get(key));
        }

        return categories;
    }

    public static ArrayList<Product> getProductByCategory(ArrayList<Product> products, ProductCategory category) {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().getCategoryId() == category.getCategoryId()) {
                productList.add(product);
            }
        }

        return productList;
    }
}
