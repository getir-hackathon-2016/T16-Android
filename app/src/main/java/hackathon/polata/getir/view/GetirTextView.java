package hackathon.polata.getir.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by polata on 20/02/2016.
 */
public class GetirTextView extends TextView {

    /**
     * Constructor.
     *
     * @param context context
     */
    public GetirTextView(Context context) {
        this(context, null);
    }

    /**
     * Constructor.
     *
     * @param context context
     * @param attrs   attribute set
     */
    public GetirTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor.
     *
     * @param context      context
     * @param attrs        attribute set
     * @param defStyleAttr def style attr
     */
    public GetirTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Get text as string.
     *
     * @return text
     */
    public String getTextAsString() {
        return getText().toString();
    }
}
