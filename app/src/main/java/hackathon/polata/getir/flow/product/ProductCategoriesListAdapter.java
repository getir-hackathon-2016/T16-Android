package hackathon.polata.getir.flow.product;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hackathon.polata.getir.R;
import hackathon.polata.getir.network.model.ProductCategory;
import hackathon.polata.getir.view.GetirTextView;

/**
 * Created by polata on 20/02/2016.
 */
public class ProductCategoriesListAdapter extends
        RecyclerView.Adapter<ProductCategoriesListAdapter.ProductCategoriesViewHolder> {

    /**
     * Interface for delegating list events to fragment.
     */
    public interface ItemSelectionListener {
        /**
         * Callback when a category is selected.
         *
         * @param selectedCategory selected category
         */
        void onSelectItem(ProductCategory selectedCategory);
    }

    private ArrayList<ProductCategory> productCategories;
    private ItemSelectionListener listener;

    /**
     * Constructor.
     *
     * @param productCategories product categories
     */
    public ProductCategoriesListAdapter(ArrayList<ProductCategory> productCategories, ItemSelectionListener listener) {
        this.productCategories = productCategories;
        this.listener = listener;
    }

    @Override
    public ProductCategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_product_categories, parent, false);
        return new ProductCategoriesViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ProductCategoriesViewHolder holder, int position) {
        holder.textViewName.setText(productCategories.get(position).getName());
        holder.textViewName.setTag(productCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return productCategories.size();
    }

    /**
     * Inner product categories view holder class.
     */
    public static class ProductCategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.list_item_product_categories_textview_name)
        GetirTextView textViewName;

        @Bind(R.id.list_item_product_categories_imageview_image)
        ImageView imageViewImage;

        private ItemSelectionListener listener;

        /**
         * Constructor.
         *
         * @param itemView item view
         */
        public ProductCategoriesViewHolder(View itemView, ItemSelectionListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onSelectItem((ProductCategory) textViewName.getTag());
        }
    }
}
