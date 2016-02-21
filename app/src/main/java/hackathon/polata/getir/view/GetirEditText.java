package hackathon.polata.getir.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import hackathon.polata.getir.R;

/**
 * Created by polata on 20/02/2016.
 */
public class GetirEditText extends EditText {

    /**
     * Constructor.
     *
     * @param context context
     */
    public GetirEditText(Context context) {
        this(context, null);
    }

    /**
     * Constructor.
     *
     * @param context context
     * @param attrs   attribute set
     */
    public GetirEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    /**
     * Constructor
     *
     * @param context      context
     * @param attrs        attribute set
     * @param defStyleAttr def style attr
     */
    public GetirEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Get text as string.
     *
     * @return text as string
     */
    public String getTextAsString() {
        return getText().toString();
    }
}
