package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.math.BigDecimal;

/**
 * Created by polata on 20/02/2016.
 */

@Parcel
public class Product {

    @SerializedName("id")
    String productId;

    @SerializedName("stock")
    int quantity;

    @SerializedName("name")
    String name;

    @SerializedName("category")
    ProductCategory category;

    @SerializedName("price")
    float amount;

    @SerializedName("image")
    String imageUrl;

    private Product() {
    }

    /**
     * Constructor.
     *
     * @param category  category
     * @param name      name
     * @param productId product id
     * @param quantity  quantity
     */
    public Product(ProductCategory category, String name, String productId, int quantity, float amount) {
        this.category = category;
        this.name = name;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Product(float amount, ProductCategory category, String imageUrl, String name, String productId, int quantity) {
        this.amount = amount;
        this.category = category;
        this.imageUrl = imageUrl;
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

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getAmount() {
        return new BigDecimal(amount).setScale(2);
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
