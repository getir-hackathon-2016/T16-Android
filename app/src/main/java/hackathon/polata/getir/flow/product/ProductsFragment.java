package hackathon.polata.getir.flow.product;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;

import java.util.ArrayList;

import butterknife.Bind;
import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.util.GridItemDecoration;

/**
 * Created by polata on 20/02/2016.
 */
@FragmentWithArgs
public class ProductsFragment extends BaseFragment<ProductController>
        implements ProductListAdapter.ItemSelectionListener {

    @Arg(bundler = ParcelerArgsBundler.class)
    ArrayList<Product> products;

    @Bind(R.id.fragment_products_recyclerview)
    RecyclerView recyclerView;

    @Override
    protected int getResourceLayoutId() {
        return R.layout.fragment_products;
    }

    @Override
    protected void initUserInterface(LayoutInflater inflater, View rootView) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.product_selection_list_column_item_size)));

        recyclerView.setAdapter(new ProductListAdapter(products, this));

        recyclerView.addItemDecoration(new GridItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.product_category_list_column_item_spacing),
                getResources().getInteger(R.integer.product_selection_list_column_item_size)));
    }

    @Override
    public void onSelectItem(Product product) {
        controller.onSelectProduct(product);
    }
}
