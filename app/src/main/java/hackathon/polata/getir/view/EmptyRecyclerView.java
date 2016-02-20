package hackathon.polata.getir.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by polata on 20/02/2016.
 */
public class EmptyRecyclerView extends RecyclerView {
    private View emptyView;

    private AdapterDataObserver emptyObserver;

    /**
     * Constructor.
     *
     * @param context context
     */
    public EmptyRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * Constructor.
     *
     * @param context context
     * @param attrs   attribute set
     */
    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor.
     *
     * @param context  context
     * @param attrs    attribute set
     * @param defStyle def style
     */
    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        setEmptyObserver();
    }

    @Override
    public void setAdapter(Adapter adapter) {

        final Adapter oldAdapter = getAdapter();

        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(emptyObserver);
        }

        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        checkEmpty();
    }

    /**
     * Set empty view to be shown when recycler view is empty.
     *
     * @param emptyView empty view
     */
    public void setEmptyView(View emptyView) {

        this.emptyView = emptyView;

        checkEmpty();
    }

    /**
     * Check if recycler view has any item.
     * If empty, display empty view, o/w display recyler view.
     */
    private void checkEmpty() {

        if (emptyView != null && getAdapter() != null) {

            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;

            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);

            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    /**
     * Set adapter data observer to observe item changes on recycler view.
     */
    private void setEmptyObserver() {

        emptyObserver = new AdapterDataObserver() {

            @Override
            public void onChanged() {
                checkEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                checkEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                checkEmpty();
            }
        };
    }
}
