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
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import hackathon.polata.getir.R;
import hackathon.polata.getir.flow.settings.SettingsFragment;
import hackathon.polata.getir.network.model.AccessToken;
import hackathon.polata.getir.util.DialogUtil;
import hackathon.polata.getir.util.PrefUtil;
import icepick.Icepick;

/**
 * Created by polata on 19/02/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseController,
        NavigationView.OnNavigationItemSelectedListener {

    protected final String KEY_TOKEN = "keyToken";

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
        if (id == R.id.activity_main_drawer_language) {
           replaceFragment(new SettingsFragment());
        }
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

    /**
     * Return registered fragment attached to activity
     *
     * @param fragment fragment class
     * @param <T>      generic class tye
     * @return registeredfragment
     */
    @SuppressWarnings("unchecked")
    protected <T extends BaseFragment> T getRegisteredFragment(Class<T> fragment) {
        return (T) attachedFragments.get(fragment);
    }

    /**
     * Return the layout of activity.
     * Can be overridden by parent activities to define a custom layout.
     *
     * @return resource id
     */
    protected int getContentResourceId() {
        return R.layout.activity_base;
    }

    /**
     * Return the base frame layout of activity, this is the frame that will hold the fragments.
     * Can be overridden by parent activities to define a custom layout (and hence a new base frame).
     *
     * @return base frame layout id
     */
    protected int getBaseFrameLayoutId() {
        return R.id.activity_base_frame;
    }

    /**
     * If false navigation drawer will be replaced by native back button.
     * Can be overridden by parent activities.
     *
     * @return boolean
     */
    protected boolean isNavigationDrawerEnabled() {
        return true;
    }

    /**
     * If false navigation drawer is completely removed from the toolbar.
     * Can be overriden by parent activities.
     *
     * @return boolean
     */
    protected boolean hasNavigationDrawer() {
        return true;
    }

    /**
     * Parent activities define their own contained fragment by overriding this method.
     *
     * @return fragment
     */
    protected abstract BaseFragment getContainedFragment();

    /**
     * Add new fragment to base frame layout (and to fragment stack).
     *
     * @param fragment fragment
     */
    protected void addFragment(BaseFragment fragment) {
        addFragment(fragment, fragment.getFragmentTag());
    }

    /**
     * Add new fragment to base frame layout (and to fragment stack).
     *
     * @param fragment fragment
     * @param tag      fragment tag
     */
    protected void addFragment(BaseFragment fragment, String tag) {
        addFragment(fragment, tag, true);
    }

    /**
     * Add new fragment to base frame layout.
     *
     * @param fragment       fragment
     * @param tag            fragment tag
     * @param addToBackStack add to back stack condition
     */
    protected void addFragment(BaseFragment fragment, String tag, boolean addToBackStack) {
        addFragment(getSupportFragmentManager(), fragment, tag, getBaseFrameLayoutId(), true);
    }

    /**
     * Replace fragment in the base frame layout (and add transaction to back stack).
     *
     * @param fragment fragment
     */
    protected void replaceFragment(BaseFragment fragment) {
        replaceFragment(fragment, fragment.getFragmentTag());
    }

    /**
     * Replace fragment from the base frame layout (and add transaction to back stack).
     *
     * @param fragment fragment
     * @param tag      tag
     */
    protected void replaceFragment(BaseFragment fragment, String tag) {
        replaceFragment(fragment, tag, true);
    }

    /**
     * Replace fragment from the base frame layout.
     *
     * @param fragment       fragment
     * @param tag            tag
     * @param addToBackStack add to back stack condition
     */
    protected void replaceFragment(BaseFragment fragment, String tag, boolean addToBackStack) {
        replaceFragment(getSupportFragmentManager(), fragment, tag, getBaseFrameLayoutId(), addToBackStack);
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

    /**
     * Return access token of session (if any).
     *
     * @return access token
     */
    public String getAccessToken() {
        return PrefUtil.getString(this, KEY_TOKEN, "");
    }

    /**
     * Return if user is authenticated.
     *
     * @return authentication condition
     */
    public boolean isUserAuthenticated() {
        final String accessToken = PrefUtil.getString(this, KEY_TOKEN, "");
        return !TextUtils.equals(accessToken, "");
    }

    /**
     * Add fragment to frame layout.
     *
     * @param fragmentManager fragment manager
     * @param fragment        fragment
     * @param tag             tag
     * @param frameLayoutId   frame layout id
     * @param addToBackStack  add to back stack condition.
     */
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

    /**
     * Replace fragment of the top of frame layout.
     *
     * @param fragmentManager fragment manager
     * @param fragment        fragment
     * @param tag             tag
     * @param frameLayoutId   frame layout id
     * @param addToBackStack  add to back stack condition
     */
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
}
