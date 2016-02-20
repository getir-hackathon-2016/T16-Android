package hackathon.polata.getir.network.model;

/**
 * Created by polata on 20/02/2016.
 */
public class Product {
    int productId;
    int quantity;
    String name;
    ProductCategory category;

    /**
     * Constructor.
     *
     * @param category  category
     * @param name      name
     * @param productId product id
     * @param quantity  quantity
     */
    public Product(ProductCategory category, String name, int productId, int quantity) {
        this.category = category;
        this.name = name;
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
