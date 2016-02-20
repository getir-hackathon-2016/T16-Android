package hackathon.polata.getir.flow.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import hackathon.polata.getir.MockGenerator;
import hackathon.polata.getir.core.BaseActivity;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.network.model.ProductCategory;

/**
 * Created by polata on 20/02/2016.
 */
public class ProductsActivity extends BaseActivity implements ProductController {

    public static Intent newIntent(Context context, boolean clearBackStack) {
        final Intent intent = new Intent(context, ProductsActivity.class);

        if (clearBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    @Override
    protected BaseFragment getContainedFragment() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MockGenerator generator = new MockGenerator();
        final ProductCategoriesFragment fragment = new ProductCategoriesFragmentBuilder(
                generator.getProductCategories()).build();
        addFragment(fragment);
    }

    @Override
    public void onSelectProductCategory(ProductCategory selectedCategory) {
        //ToDo navigate to selection screen
    }
}
