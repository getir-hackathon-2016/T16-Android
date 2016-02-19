package hackathon.polata.getir.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by polata on 20/02/2016.
 */
public class GetirTextView extends TextView {

    public GetirTextView(Context context) {
        this(context, null);
    }

    public GetirTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GetirTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
