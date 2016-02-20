package hackathon.polata.getir.flow.product;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hackathon.polata.getir.R;
import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.view.GetirTextView;

/**
 * Created by polata on 20/02/2016.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    /**
     * Interface for delegating list events to fragment.
     */
    public interface ItemSelectionListener {
        /**
         * Callback when a product is selected.
         *
         * @param product selected product
         */
        void onSelectItem(Product product);
    }

    private ArrayList<Product> products;
    private ItemSelectionListener listener;

    /**
     * Constructor.
     *
     * @param products products
     */
    public ProductListAdapter(ArrayList<Product> products, ItemSelectionListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_product, parent, false);
        return new ProductViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.textViewName.setText(products.get(position).getName());
        holder.textViewAmount.setText(products.get(position).getAmount().toString());
        holder.textViewAmount.setTag(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Inner product view holder.
     */
    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.list_item_product_textview_amount)
        GetirTextView textViewAmount;

        @Bind(R.id.list_item_product_textview_name)
        GetirTextView textViewName;

        private ItemSelectionListener listener;

        /**
         * Constructor.
         *
         * @param itemView item view
         */
        public ProductViewHolder(View itemView, ItemSelectionListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onSelectItem((Product) textViewAmount.getTag());
        }
    }
}