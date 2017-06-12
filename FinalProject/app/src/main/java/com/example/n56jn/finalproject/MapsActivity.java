package com.example.n56jn.finalproject;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.*;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String resource = "https://api.mediatek.com/mcs/v2/devices/DvwZCSkt/datachannels/";
    private String datapoint = "/datapoints.csv";
    private String TmpId = "Temp_Display";
    private String HumId = "Hum_Display";
    private String CO2Id = "CO2_Display";
    private String PmId = "pm_Display";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        Log.d("map", "startmap");
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
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
    public String decodeinput(String input){
        String [] aftersplit;
        aftersplit = input.split(",");
        return aftersplit[2];
    }
    public String getInfo(String datain) {
        String value = "";
        try {
            URL url = new URL(datain);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try{
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String inputdata = readStream(in);
                value = decodeinput(inputdata);
//                Log.d("Main --", " " + inputdata);

            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return value;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mmap", "startmap");
        String tmp = getInfo(resource + TmpId + datapoint);
        String hum = getInfo(resource + HumId + datapoint);
        String co2 = getInfo(resource + CO2Id + datapoint);
        String pm = getInfo(resource + PmId + datapoint);
        Log.d("mmap", "endmap");

        // Add a marker in Delta108 and move the camera
        LatLng delta108 = new LatLng(24.795949, 120.991871);
        mMap.addMarker(new MarkerOptions().position(delta108).title("tmp: " + tmp + "\nhum: " + hum + "\nco2: " + co2 + "\npm: " + pm));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(delta108));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(delta108, 15));
    }
}