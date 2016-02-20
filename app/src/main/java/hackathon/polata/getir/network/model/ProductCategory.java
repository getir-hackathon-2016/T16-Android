package hackathon.polata.getir.network.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by polata on 20/02/2016.
 */
@Parcel
public class ProductCategory {

    @SerializedName("categoryId")
    int categoryId;

    @SerializedName("name")
    String name;

    /**
     * Default constructor disabled.
     */
    private ProductCategory() {
    }

    /**
     * Constructor.
     *
     * @param categoryId category id
     * @param name       name
     */
    public ProductCategory(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
