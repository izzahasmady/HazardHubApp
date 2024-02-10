package com.example.gmapactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.view.MenuItem;

public class News extends AppCompatActivity {

    private String URL = "http://10.20.189.155/ICT602/HazardHub/all1.php";
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<ListNews> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsList = new ArrayList<>();
        adapter = new NewsAdapter(newsList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadNews();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.map) {
                    startActivity(new Intent(News.this, GMapActivity.class));
                    return true;
                } else if (itemId == R.id.home) {
                    return true; // Do nothing as the user is already in the News section
                } else if (itemId == R.id.about) {
                    startActivity(new Intent(News.this, About.class));
                    return true;
                } else if (itemId == R.id.logout) {
                    // Handle logout
                    logout();
                    return true;
                }
                return false;
            }
        });
    }

    private void loadNews() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Gson gson = new Gson();
                            ListNews[] newsArray = gson.fromJson(response.toString(), ListNews[].class);

                            newsList.addAll(Arrays.asList(newsArray));
                            adapter.notifyDataSetChanged();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            Log.e("TAG", "JSON parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "Volley error: " + error.getMessage());
                    }
                });

        queue.add(jsonArrayRequest);
    }

    private void logout() {
        // Implement your logout logic here
        // For example, clearing user data, closing sessions, etc.

        // After logging out, navigate to the login or splash screen
        Intent intent = new Intent(News.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
