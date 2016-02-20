package hackathon.polata.getir.flow.product;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.util.ArrayList;

import butterknife.Bind;
import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.network.model.ProductCategory;
import hackathon.polata.getir.util.GridItemDecoration;

/**
 * Created by polata on 20/02/2016.
 */
@FragmentWithArgs
public class ProductCategoriesFragment extends BaseFragment<ProductController>
        implements ProductCategoriesListAdapter.ItemSelectionListener {

    @Arg
    ArrayList<ProductCategory> productCategories;

    @Bind(R.id.fragment_product_categories_recyclerview)
    RecyclerView recyclerViewProductCategories;

    @Override
    protected int getResourceLayoutId() {
        return R.layout.fragment_product_categories;
    }

    @Override
    protected void initUserInterface(LayoutInflater inflater, View rootView) {
        recyclerViewProductCategories.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.product_category_list_column_item_size)));

        recyclerViewProductCategories.setAdapter(new ProductCategoriesListAdapter(productCategories, this));

        recyclerViewProductCategories.addItemDecoration(new GridItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.product_category_list_column_item_spacing),
                getResources().getInteger(R.integer.product_category_list_column_item_size)));

    }

    @Override
    public void onSelectItem(ProductCategory selectedCategory) {
        controller.onSelectProductCategory(selectedCategory);
    }
}