package hackathon.polata.getir.flow.product;

import hackathon.polata.getir.core.BaseController;
import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.network.model.ProductCategory;

/**
 * Created by polata on 20/02/2016.
 */
public interface ProductController extends BaseController {

    /**
     * Callback when a product category is selected.
     *
     * @param selectedCategory selected category
     */
    void onSelectProductCategory(ProductCategory selectedCategory);

    /**
     * Callback when a product is selected.
     *
     * @param product selected product
     */
    void onSelectProduct(Product product);
}
