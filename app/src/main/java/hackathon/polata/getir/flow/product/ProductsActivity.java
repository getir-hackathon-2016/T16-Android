package hackathon.polata.getir.flow.product;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.devspark.appmsg.AppMsg;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import hackathon.polata.getir.MockGenerator;
import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseActivity;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.network.model.Product;
import hackathon.polata.getir.network.model.ProductCategory;
import hackathon.polata.getir.util.GridItemDecoration;
import hackathon.polata.getir.util.PermissionUtils;
import hackathon.polata.getir.view.GetirTextView;

/**
 * Created by polata on 20/02/2016.
 */
public class ProductsActivity extends BaseActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ProductCategoriesListAdapter.ItemSelectionListener, ProductListAdapter.ItemSelectionListener {

    public enum ActiveScreen {
        PRODUCT, PRODUCT_CATEGORY;
    }

    @Bind(R.id.activity_product_categories_recyclerview)
    RecyclerView recyclerViewProduct;

    @Bind(R.id.activity_product_textview_time)
    GetirTextView textViewTime;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;

    SupportMapFragment mapFragment;

    private GoogleMap googleMap;

    private UiSettings uISettings;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private MockGenerator mockGenerator;

    private ActiveScreen activeScreen;

    private ProductCategory selectedCategory;

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
        return 0;
    }

    @Override
    protected BaseFragment getContainedFragment() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mockGenerator = new MockGenerator();
        getProductCategories();
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
            return true;
            //ToDo show chart
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
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.product_selection_list_column_item_size)));

        recyclerViewProduct.setAdapter(new ProductListAdapter(mockGenerator.getProducts(), this));

        recyclerViewProduct.addItemDecoration(new GridItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.product_category_list_column_item_spacing),
                getResources().getInteger(R.integer.product_selection_list_column_item_size)));

        activeScreen = ActiveScreen.PRODUCT;
    }

    @Override
    public void onSelectItem(Product product) {
    }

    @Override
    public void onBackPressed() {
        if(activeScreen == ActiveScreen.PRODUCT) {
            getProductCategories();
            return;
        }
        super.onBackPressed();
    }
    private void getProductCategories() {
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.product_category_list_column_item_size)));

        recyclerViewProduct.setAdapter(new ProductCategoriesListAdapter(mockGenerator.getProductCategories()
                , this));

        recyclerViewProduct.addItemDecoration(new GridItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.product_category_list_column_item_spacing),
                getResources().getInteger(R.integer.product_category_list_column_item_size)));

        textViewTime.setText(10 + " " + getString(R.string.min));

        activeScreen = ActiveScreen.PRODUCT_CATEGORY;
    }

    private void getUserLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    private void setUpMapIfNeeded() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (googleMap != null) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
            }
        }
    }

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
    }
}
