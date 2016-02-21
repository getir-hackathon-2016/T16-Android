package hackathon.polata.getir.flow.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devspark.appmsg.AppMsg;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import hackathon.polata.getir.R;
import hackathon.polata.getir.core.BaseActivity;
import hackathon.polata.getir.core.BaseFragment;
import hackathon.polata.getir.flow.product.ProductsActivity;

/**
 * Created by polata on 21/02/2016.
 */
public class TrackOrderActivity extends BaseActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap googleMap;
    private PolylineOptions mPolylineOptions;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private LatLng latLng;

    /**
     * Create a new activity intent.
     *
     * @param context        context
     * @param clearBackStack clear back stack flag
     * @return intent
     */
    public static Intent newIntent(Context context, boolean clearBackStack) {
        final Intent intent = new Intent(context, TrackOrderActivity.class);

        if (clearBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    @Override
    protected boolean hasNavigationDrawer() {
        return false;
    }

    @Override
    protected BaseFragment getContainedFragment() {
        return null;
    }

    @Override
    protected int getContentResourceId() {
        return R.layout.activity_track_order;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getUserLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initializeMap();
    }

    private void initializeMap() {
        mPolylineOptions = new PolylineOptions();
        mPolylineOptions.color(ContextCompat.getColor(this, R.color.colorPrimaryDark)).width(7);
    }

    private void updatePolyline() {
        googleMap.clear();
        googleMap.addPolyline(mPolylineOptions.add(latLng));
    }

    private void updateMarker() {
        googleMap.addMarker(new MarkerOptions().position(latLng).icon(
                (BitmapDescriptorFactory.fromResource(R.drawable.ic_truck))));
    }

    private void updateCamera() {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }

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

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        this.latLng = new LatLng(currentLatitude, currentLongitude);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        updateCamera();

        generateMockLocationUpdate();
    }

    /**
     * Generate mock location update by setting a courier to a point and repeatly updating his location till
     * he reaches the destination (which is the address of the user)
     */
    private void generateMockLocationUpdate() {

        final int repeat = 7;
        final int timeEach = 5;

        latLng = new LatLng(latLng.latitude + 0.08, latLng.longitude + 0.08);
        updatePolyline();
        updateMarker();
        updateCamera();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        final ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                latLng = new LatLng(latLng.latitude - 0.08 / repeat, latLng.longitude - 0.08 / repeat);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updatePolyline();
                        updateMarker();
                        updateCamera();
                    }
                });
            }
        }, timeEach, timeEach, TimeUnit.SECONDS);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scheduledFuture.cancel(false);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new MaterialDialog.Builder(TrackOrderActivity.this)
                                .title(R.string.app_name)
                                .content(R.string.order_arrived)
                                .positiveText(R.string.ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        startActivity(ProductsActivity.newIntent(TrackOrderActivity.this, true));
                                    }
                                })
                                .show();
                    }
                });
            }
        }, timeEach * repeat * 1000);
    }
}

