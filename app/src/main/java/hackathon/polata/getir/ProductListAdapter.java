package hackathon.polata.getir;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import hackathon.polata.getir.network.model.Product;

/**
 * Created by polata on 20/02/2016.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private ArrayList<Product> products;

    /**
     * Constructor.
     *
     * @param products products
     */
    public ProductListAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_product_categories, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Inner product view holder.
     */
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        /**
         * Constructor.
         *
         * @param itemView item view
         */
        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}