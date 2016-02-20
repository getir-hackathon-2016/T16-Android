package hackathon.polata.getir.flow.cart;

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
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartItemViewHolder> {

    private ArrayList<Product> products;

    /**
     * Constructor.
     *
     * @param products products
     */
    public CartListAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder holder, int position) {
        holder.textViewAmount.setText(products.get(position).getAmount().toString());
        holder.textViewName.setText(products.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Inner product view holder.
     */
    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.list_item_cart_textview_name)
        GetirTextView textViewName;

        @Bind(R.id.list_item_cart_textview_amount)
        GetirTextView textViewAmount;

        /**
         * Constructor.
         *
         * @param itemView item view
         */
        public CartItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
