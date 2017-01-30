package com.example.abhilashreddy.firebase;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class finalMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public ArrayList<objects> receivedarraylist;
    public Float col;
    GPSTracker latlon = new GPSTracker(finalMapsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // arraylist =  (ArrayList<objects>)getIntent().getSerializableExtra("FILES_TO_SEND");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        receivedarraylist = camera.updatedarray;
        // Add a marker in Sydney and move the camera
        for (objects coords : receivedarraylist) {
//            if(coords.getSelectedstring()=="Category"){
//                 col=BitmapDescriptorFactory.HUE_RED;
//            }
//            if(coords.getSelectedstring()=="Rare"){
//                col=BitmapDescriptorFactory.HUE_GREEN;
//            }
//            if(coords.getSelectedstring()=="Exotic"){
//                col=BitmapDescriptorFactory.HUE_BLUE;
//            }
//            Log.e("this",""+col);

            LatLng x = new LatLng(coords.getLat(), coords.getLongi());
            Marker m=mMap.addMarker(new MarkerOptions().position(x).title(coords.getDateitem()).snippet(coords.getValuetext()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(x));
            Log.e("this",""+coords.getSelectedstring());
            if(coords.getSelectedstring()=="Category"){
               // Marker xyz=m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            }
           else if(coords.getSelectedstring()=="Rare"){
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            else{
                m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }

//            m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(x, 15));
            m.showInfoWindow();

        }
        //LatLng x = new LatLng(latlon.getLatitude(), latlon.getLongitude());

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mMap.setMyLocationEnabled(true);
            return;
        }
        mMap.setMyLocationEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);


    }
}
