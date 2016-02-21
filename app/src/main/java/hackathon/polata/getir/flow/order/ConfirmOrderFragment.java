package hackathon.polata.getir.flow.order;

import android.view.LayoutInflater;
import android.view.View;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.OnClick;
import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.view.GetirTextView;

/**
 * Created by polata on 21/02/2016.
 */
@FragmentWithArgs
public class ConfirmOrderFragment extends BaseFragment<OrderController> {

    @Bind(R.id.fragment_confirm_order_textview_address)
    GetirTextView textViewAddress;

    @Bind(R.id.fragment_confirm_order_textview_amount)
    GetirTextView textViewAmount;

    @Bind(R.id.fragment_confirm_order_textview_promotion)
    GetirTextView textViewPromotion;

    @Arg
    String address;

    @Arg
    BigDecimal amount;

    @Arg
    String promotion;

    @Override
    protected int getResourceLayoutId() {
        return R.layout.fragment_confirm_order;
    }

    @Override
    protected void initUserInterface(LayoutInflater inflater, View rootView) {
        textViewAddress.setText(address);
        textViewAmount.setText(amount.toString() + " TL");
        textViewPromotion.setText(promotion);
    }

    @OnClick(R.id.fragment_confirm_order_button_confirm)
    public void onConfirmClick() {
        controller.onMakeOrderClick();
    }
}
