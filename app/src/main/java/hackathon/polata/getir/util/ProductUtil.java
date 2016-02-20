package hackathon.polata.getir.util;

import java.math.BigDecimal;
import java.util.List;

import hackathon.polata.getir.network.model.Product;

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
        for(Product product: products) {
            total = total.add(product.getAmount().setScale(2));
        }

        return total;
    }

    public static String getCartTotalAsString(List<Product> products) {
        return calculateCartTotal(products).toString();
    }
}
