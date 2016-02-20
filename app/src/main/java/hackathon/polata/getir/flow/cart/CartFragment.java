package hackathon.polata.getir.flow.cart;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.view.EmptyRecyclerView;
import hackathon.polata.getir.view.GetirTextView;

/**
 * Created by polata on 20/02/2016.
 */
@FragmentWithArgs
public class CartFragment extends BaseFragment<CartController> {

    @Arg(bundler = ParcelerArgsBundler.class)
    ArrayList<Product> products;

    @Bind(R.id.fragment_cart_textview_amount)
    GetirTextView textViewAmount;

    @Bind(R.id.fragment_cart_textview_empty_placeholder)
    GetirTextView textViewEmptyPlaceholder;

    @Bind(R.id.fragment_cart_recyclerview)
    EmptyRecyclerView emptyRecyclerView;

    @Override
    protected int getResourceLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initUserInterface(LayoutInflater inflater, View rootView) {
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyRecyclerView.setEmptyView(textViewEmptyPlaceholder);
        emptyRecyclerView.setAdapter(new CartListAdapter(products));
    }

    @OnClick(R.id.fragment_cart_view_continue)
    public void onContinueClick() {
        controller.onContinueClick();
    }
}
