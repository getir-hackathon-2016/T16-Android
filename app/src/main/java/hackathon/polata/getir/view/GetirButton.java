package hackathon.polata.getir.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by polata on 20/02/2016.
 */
public class GetirButton extends Button {

    /**
     * Constructor.
     *
     * @param context context
     */
    public GetirButton(Context context) {
        this(context, null);
    }

    /**
     * Constructor.
     *
     * @param context context
     * @param attrs   attribute set
     */
    public GetirButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor.
     *
     * @param context      context
     * @param attrs        attribute set
     * @param defStyleAttr def style attr
     */
    public GetirButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
