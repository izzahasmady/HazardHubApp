package com.example.gmapactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.Vector;

public class GMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Vector<MarkerOptions> markerOptions;
    private String URL = "http://10.20.189.155/ICT602/HazardHub/all.php";
    RequestQueue requestQueue;
    Gson gson;
    Hazard[] hazards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap);

        gson = new GsonBuilder().create();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);

        markerOptions = new Vector<>();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks
                int itemId = item.getItemId();
                if (itemId == R.id.map) {
                    // Do nothing as the user is already on the map
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(GMapActivity.this, News.class));
                    return true;
                } else if (itemId == R.id.about) {
                    startActivity(new Intent(GMapActivity.this, About.class));
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.custom_info_window, null);

                TextView titleTextView = view.findViewById(R.id.title);
                TextView snippetTextView = view.findViewById(R.id.snippet);

                titleTextView.setText(marker.getTitle());
                snippetTextView.setText(marker.getSnippet());

                return view;
            }
        });

        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        LatLng centerOfMalaysia = new LatLng(4.2105, 101.9758); // Adjust the coordinates as needed

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerOfMalaysia, 8));

        // Optionally, you can animate the camera movement:
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerOfMalaysia, 8), 1000, null);

        sendRequest();
    }



    public void sendRequest() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, onSuccess, onError);
        requestQueue.add(stringRequest);
    }

    public Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hazards = gson.fromJson(response, Hazard[].class);

            Log.d("Hazard", "Number of Hazard Data Point : " + hazards.length);

            if (hazards.length < 1) {
                Toast.makeText(getApplicationContext(), "Problem retrieving JSON data", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Hazard info : hazards) {
                Double lat = Double.parseDouble(info.lat);
                Double lng = Double.parseDouble(info.lng);

                Log.d("Hazard", "Lat: " + lat + ", Lng: " + lng);

                String title = info.hazardType;
                String snippet = "Date: " + info.date + "\nTime: " + info.time + "\nReporter: " + info.reporter;

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(title)
                        .snippet(snippet);

                mMap.addMarker(marker);
            }
        }
    };

    public Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    
}
