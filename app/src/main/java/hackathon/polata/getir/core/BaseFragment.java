package hackathon.polata.getir.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * Created by polata on 19/02/2016.
 */
public abstract class BaseFragment<Controller extends BaseController> extends Fragment {

    protected Controller controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Icepick.restoreInstanceState(this, savedInstanceState);

        final View rootView = inflater.inflate(getResourceLayoutId(), container, false);

        ButterKnife.bind(this, rootView);

        initUserInterface(inflater, rootView);

        return rootView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            controller = (Controller) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(getActivity().getLocalClassName() + " must implement controller.", e);
        }

        controller.attach(this);
    }

    @Override
    public void onDetach() {
        controller.detach(this);
        controller = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    protected String getFragmentTag() {
        return this.getClass().getSimpleName();
    }

    protected abstract int getResourceLayoutId();

    protected abstract void initUserInterface(LayoutInflater inflater, final View rootView);
}
