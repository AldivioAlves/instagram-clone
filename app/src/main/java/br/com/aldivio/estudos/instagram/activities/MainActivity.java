package br.com.aldivio.estudos.instagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import br.com.aldivio.estudos.instagram.R;
import br.com.aldivio.estudos.instagram.helpers.FirebaseUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private Toolbar mainToolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        setNavigation();
    }

    private void initComponents() {
        mainToolbar = findViewById(R.id.main_toolbar);
        mainToolbar.setTitle("Instagram");
        setSupportActionBar(mainToolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

    }

    private void setNavigation() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_navigation_menu_home:
                        Log.d(TAG, "tab_navigation_menu_home");
                        break;
                    case R.id.tab_navigation_menu_search:
                        Log.d(TAG, "tab_navigation_menu_search");
                        break;
                    case R.id.tab_navigation_menu_add_post:
                        Log.d(TAG, "tab_navigation_menu_add_post");
                        break;
                    case R.id.tab_navigation_menu_profile:
                        Log.d(TAG, "tab_navigation_menu_profile");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.main_menu_logout:
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth auth = FirebaseUtils.getAuth();
        auth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
