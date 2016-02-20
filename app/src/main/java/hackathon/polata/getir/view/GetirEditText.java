package hackathon.polata.getir.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import hackathon.polata.getir.R;

/**
 * Created by polata on 20/02/2016.
 */
public class GetirEditText extends EditText {
    public GetirEditText(Context context) {
        this(context, null);
    }

    public GetirEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public GetirEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getTextAsString() {
        return getText().toString();
    }
}
