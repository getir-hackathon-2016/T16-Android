package hackathon.polata.getir.flow.product;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hackathon.polata.getir.MockGenerator;
import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseActivity;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.flow.cart.CartController;
import hackathon.polata.getir.flow.cart.CartFragment;
import hackathon.polata.getir.flow.cart.CartFragmentBuilder;
import hackathon.polata.getir.flow.order.ConfirmOrderFragment;
import hackathon.polata.getir.flow.order.ConfirmOrderFragmentBuilder;
import hackathon.polata.getir.flow.order.OrderController;
import hackathon.polata.getir.flow.settings.SettingsController;
import hackathon.polata.getir.network.CustomCallback;
import hackathon.polata.getir.network.GetirServiceProvider;
import hackathon.polata.getir.network.model.ApiResponse;
import hackathon.polata.getir.network.model.AuthenticatedUser;
import hackathon.polata.getir.network.model.CustomError;
import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.network.model.ProductCategory;
import hackathon.polata.getir.util.GridItemDecoration;
import hackathon.polata.getir.util.PermissionUtils;
import hackathon.polata.getir.util.ProductUtil;
import hackathon.polata.getir.view.GetirTextView;

/**
 * Created by polata on 20/02/2016.
 */
public class ProductsActivity extends BaseActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ProductCategoriesListAdapter.ItemSelectionListener,
        ProductListAdapter.ItemSelectionListener,
        CartController,
        SettingsController,
        OrderController{

    /**
     * Enum for the visible screen on activity.
     */
    public enum ActiveScreen {
        PRODUCT, PRODUCT_CATEGORY;
    }

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @Bind(R.id.activity_product_categories_recyclerview)
    RecyclerView recyclerViewProduct;

    @Bind(R.id.activity_product_textview_time)
    GetirTextView textViewTime;

    @Bind(R.id.activity_products_container_cart_overlay)
    View viewCartOverlay;

    @Bind(R.id.activity_products_textview_cart_overlay_total)
    GetirTextView textViewTotal;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;

    SupportMapFragment mapFragment;

    private GoogleMap googleMap;

    private UiSettings uISettings;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private MockGenerator generator;

    private String address;

    ActiveScreen activeScreen;

    ProductCategory selectedCategory;

    ArrayList<Product> selectedProducts;

    ArrayList<Product> products;

    private GridItemDecoration productCategoriesItemDecoration;
    private GridItemDecoration productsItemDecoration;

    /**
     * Create a new activity intent.
     *
     * @param context        context
     * @param clearBackStack clear back stack flag
     * @return intent
     */
    public static Intent newIntent(Context context, boolean clearBackStack) {
        final Intent intent = new Intent(context, ProductsActivity.class);

        if (clearBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    @Override
    protected int getContentResourceId() {
        return R.layout.activity_products;
    }

    @Override
    protected int getBaseFrameLayoutId() {
        return R.id.activity_base_frame;
    }

    @Override
    protected BaseFragment getContainedFragment() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generator = new MockGenerator(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showProgressDialog();
        GetirServiceProvider.getService().init(isUserAuthenticated() ? getAccessToken() : "")
                .enqueue(new CustomCallback<ApiResponse<AuthenticatedUser>>() {
                    @Override
                    public void onFailure(CustomError error) {
                        hideProgressDialog();
                        AppMsg.makeText(ProductsActivity.this, error.getErrorMessage(), AppMsg.STYLE_ALERT)
                                .setLayoutGravity(Gravity.BOTTOM).show();
                    }

                    @Override
                    public void onResponse(ApiResponse<AuthenticatedUser> response) {
                        hideProgressDialog();
                        setUserData(response.getData());
                    }
                });

        GetirServiceProvider.getService().getProducts(isUserAuthenticated() ? getAccessToken() : "")
                .enqueue(new CustomCallback<ApiResponse<ArrayList<Product>>>() {
                    @Override
                    public void onFailure(CustomError error) {
                        hideProgressDialog();
                        AppMsg.makeText(ProductsActivity.this, error.getErrorMessage(), AppMsg.STYLE_ALERT)
                                .setLayoutGravity(Gravity.BOTTOM).show();
                    }

                    @Override
                    public void onResponse(ApiResponse<ArrayList<Product>> response) {
                        hideProgressDialog();
                        setProductData(response.getData());
                    }
                });

        setDecorations();
        getUserLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_chart) {
            onClickCartTotal();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        uISettings = googleMap.getUiSettings();

        uISettings.setZoomControlsEnabled(true);
        uISettings.setMyLocationButtonEnabled(true);
        googleMap.setMyLocationEnabled(true);
        uISettings.setZoomGesturesEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_PERMISSION_REQUEST_CODE) {
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                uISettings.setMyLocationButtonEnabled(true);
            }

        } else if (requestCode == LOCATION_LAYER_PERMISSION_REQUEST_CODE) {
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                googleMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            AppMsg.makeText(this, connectionResult.getErrorMessage(), AppMsg.STYLE_INFO);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onSelectItem(ProductCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

        recyclerViewProduct.setAdapter(null);
        recyclerViewProduct.setLayoutManager(null);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.product_selection_list_column_item_size)));

        recyclerViewProduct.setAdapter(new ProductListAdapter(
                ProductUtil.getProductByCategory(products, selectedCategory), this));

        if (productCategoriesItemDecoration != null) {
            recyclerViewProduct.removeItemDecoration(productCategoriesItemDecoration);
        }
        recyclerViewProduct.addItemDecoration(productsItemDecoration);

        activeScreen = ActiveScreen.PRODUCT;
    }

    @Override
    public void onSelectItem(Product product) {
        if (selectedProducts == null) {
            selectedProducts = new ArrayList<>();
        }
        selectedProducts.add(product);

        viewCartOverlay.setVisibility(View.VISIBLE);
        textViewTotal.setText(ProductUtil.getCartTotalAsString(selectedProducts) + " TL");
    }

    @OnClick(R.id.activity_products_container_cart_overlay)
    public void onClickCartTotal() {
        if (getRegisteredFragment(CartFragment.class) == null) {
            CartFragment fragment = new CartFragmentBuilder(selectedProducts).build();
            addFragment(fragment);
        }
    }

    @Override
    public void onContinueClick() {
        final String newAddress = address.equals("") ? generator.getAddress() : address;
        ConfirmOrderFragment fragment = new ConfirmOrderFragmentBuilder(newAddress,
                ProductUtil.calculateCartTotal(selectedProducts),
                generator.getPromotion()).build();
        replaceFragment(fragment);
    }

    @Override
    public void onLanguageChanged() {
        recreate();
    }

    @Override
    public void onBackPressed() {
        if (getRegisteredFragment(CartFragment.class) != null) {
            super.onBackPressed();
            return;
        }

        if (activeScreen == ActiveScreen.PRODUCT) {
            getProductCategories();
            return;
        }

        if (activeScreen == ActiveScreen.PRODUCT_CATEGORY &&
                slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onMakeOrderClick() {
        //ToDo make order
    }

    /**
     * Set item decorations of recyclerviews once.
     */
    private void setDecorations() {
        productCategoriesItemDecoration = new GridItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.product_category_list_column_item_spacing),
                getResources().getInteger(R.integer.product_category_list_column_item_size));

        productsItemDecoration = new GridItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.product_category_list_column_item_spacing),
                getResources().getInteger(R.integer.product_selection_list_column_item_size));
    }

    /**
     * Bind authenticated user data to views.
     *
     * @param user authenticated user
     */
    private void setUserData(AuthenticatedUser user) {
        ((TextView) ButterKnife.findById(navigationView, R.id.nav_header_textview_email))
                .setText(user.getEmail());
        ((TextView) ButterKnife.findById(navigationView, R.id.nav_header_textview_username))
                .setText(user.getEmail().substring(0, user.getEmail().indexOf("@")));
    }

    /**
     * Bind product list.
     *
     * @param products product list
     */
    private void setProductData(ArrayList<Product> products) {
        this.products = products;
        getProductCategories();
    }

    /**
     * Create product categories list and its adapter.
     */
    private void getProductCategories() {
        recyclerViewProduct.setAdapter(null);
        recyclerViewProduct.setLayoutManager(null);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.product_category_list_column_item_size)));

        recyclerViewProduct.setAdapter(new ProductCategoriesListAdapter(ProductUtil.getProductCategories(products)
                , this));

        if (productsItemDecoration != null) {
            recyclerViewProduct.removeItemDecoration(productsItemDecoration);
        }
        recyclerViewProduct.addItemDecoration(productCategoriesItemDecoration);

        textViewTime.setText(10 + " " + getString(R.string.min));

        activeScreen = ActiveScreen.PRODUCT_CATEGORY;
    }

    /**
     * Get user location.
     */
    private void getUserLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    /**
     * A fallback map initializer.
     */
    private void setUpMapIfNeeded() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (googleMap != null) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
            }
        }
    }

    /**
     * Handle new location when location changes.
     *
     * @param location location
     */
    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(getString(R.string.marker_address))
                .draggable(true);

        googleMap.addMarker(options).showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    address = placeLikelihood.getPlace().getAddress().toString();
                    break;
                }
                likelyPlaces.release();
            }
        });
    }
}
