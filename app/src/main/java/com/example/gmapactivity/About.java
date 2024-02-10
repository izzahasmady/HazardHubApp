package com.example.gmapactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.map) {
                    startActivity(new Intent(About.this, GMapActivity.class));
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(About.this, News.class));
                    return true;
                } else if (itemId == R.id.about) {
                    startActivity(new Intent(About.this, About.class));
                    return true;
                } else if (itemId == R.id.logout) {
                    // Handle logout when the Log Out menu item is clicked
                    logout();
                    return true;
                }
                return false;
            }
        });
    }

    private void logout() {
        // Implement your logout logic here
        // For example, clearing user data, closing sessions, etc.

        // After logging out, navigate to the login or splash screen
        Intent intent = new Intent(About.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Close the current activity
    }

    // Method to handle opening GitHub link
    public void openGitHubLink(View view) {
        Uri uri = Uri.parse("https://github.com/izzahasmady/HazardHubApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
