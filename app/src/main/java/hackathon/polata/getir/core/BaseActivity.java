package hackathon.polata.getir.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import hackathon.polata.getir.R;
import hackathon.polata.getir.util.DialogUtil;
import icepick.Icepick;

/**
 * Created by polata on 19/02/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseController,
        NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private Map<Class, BaseFragment> attachedFragments = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentResourceId());

        final BaseFragment fragment = getContainedFragment();

        if (fragment != null) {
            String tag = (fragment).getFragmentTag();
            addFragment(fragment, tag, false);
        }

        Icepick.restoreInstanceState(this, savedInstanceState);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        if (!hasNavigationDrawer()) {
            toggle.setDrawerIndicatorEnabled(false);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        if (!isNavigationDrawerEnabled()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void attach(BaseFragment fragment) {
        attachedFragments.put(fragment.getClass(), fragment);
    }

    @Override
    public void detach(BaseFragment fragment) {
        attachedFragments.remove(fragment.getClass());
    }

    @Override
    protected void onDestroy() {
        attachedFragments.clear();
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int id = item.getItemId();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends BaseFragment> T getRegisteredFragment(Class<T> fragment) {
        return (T) attachedFragments.get(fragment);
    }

    protected int getContentResourceId() {
        return R.layout.activity_base;
    }

    protected int getBaseFrameLayoutId() {
        return R.id.activity_base_frame;
    }

    protected void addFragment(BaseFragment fragment) {
        addFragment(fragment, fragment.getFragmentTag());
    }

    protected void addFragment(BaseFragment fragment, String tag) {
        addFragment(fragment, tag, true);
    }

    protected void addFragment(BaseFragment fragment, String tag, boolean addToBackStack) {
        addFragment(getSupportFragmentManager(), fragment, tag, getBaseFrameLayoutId(), true);
    }

    protected void replaceFragment(BaseFragment fragment) {
        replaceFragment(fragment, fragment.getFragmentTag());
    }

    protected void replaceFragment(BaseFragment fragment, String tag) {
        replaceFragment(fragment, tag, true);
    }

    protected void replaceFragment(BaseFragment fragment, String tag, boolean addToBackStack) {
        replaceFragment(getSupportFragmentManager(), fragment, tag, getBaseFrameLayoutId(), true);
    }

    /**
     * Show progress dialog.
     */
    protected void showProgressDialog() {
        DialogUtil.showProgressDialog(this);
    }

    /**
     * Show progress dialog with a custom message.
     *
     * @param resourceId message resource id
     */
    protected void showProgressDialog(@StringRes int resourceId) {
        DialogUtil.showProgressDialog(this, resourceId);
    }

    /**
     * Hide progress dialog.
     */
    protected void hideProgressDialog() {
        DialogUtil.hideProgressDialog();
    }

    private void addFragment(
            FragmentManager fragmentManager,
            BaseFragment fragment,
            String tag,
            int frameLayoutId,
            boolean addToBackStack) {

        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(frameLayoutId, fragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    private void replaceFragment(
            FragmentManager fragmentManager,
            BaseFragment fragment,
            String tag,
            int frameLayoutId,
            boolean addToBackStack) {

        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(frameLayoutId, fragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    protected boolean isNavigationDrawerEnabled() {
        return true;
    }

    protected boolean hasNavigationDrawer() {
        return true;
    }

    protected abstract BaseFragment getContainedFragment();
}
