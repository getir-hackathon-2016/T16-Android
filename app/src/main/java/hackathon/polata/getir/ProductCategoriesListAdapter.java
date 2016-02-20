package hackathon.polata.getir;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import hackathon.polata.getir.network.model.ProductCategories;

/**
 * Created by polata on 20/02/2016.
 */
public class ProductCategoriesListAdapter extends
        RecyclerView.Adapter<ProductCategoriesListAdapter.ProductCategoriesViewHolder> {

    private ArrayList<ProductCategories> productCategories;

    /**
     * Constructor.
     *
     * @param productCategories product categories
     */
    public ProductCategoriesListAdapter(ArrayList<ProductCategories> productCategories) {
        this.productCategories = productCategories;
    }

    @Override
    public ProductCategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_product_categories, parent, false);
        return new ProductCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductCategoriesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productCategories.size();
    }

    /**
     * Inner product categories view holder class.
     */
    public static class ProductCategoriesViewHolder extends RecyclerView.ViewHolder {
        /**
         * Constructor.
         *
         * @param itemView item view
         */
        public ProductCategoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
