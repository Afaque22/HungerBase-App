package com.example.hungerbase;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.TaskStackBuilder;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hungerbase.Adapters.resRVAdapter;
import com.example.hungerbase.Models.DetialsModel;
import com.example.hungerbase.Models.resRVModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    FirebaseAuth firebaseAuth;
    NavigationView navigationView;
    RecyclerView recyclerView;
    resRVAdapter adapter;
    String mCity , notCity;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawe_open, R.string.nav_drawe_close);
        toggle.syncState();

        firebaseAuth = FirebaseAuth.getInstance();
        txt = findViewById(R.id.txtAll);


               readCity(new firebaseCallback() {
                   @Override
                   public void onCallback(String s) {
                     mCity = s;
                   }
               });

               DetialsModel model = new DetialsModel();
               txt.setText(mCity);



        FirebaseRecyclerOptions<resRVModel> options = new FirebaseRecyclerOptions.Builder<resRVModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Restaurants").orderByChild("city").equalTo(mCity), resRVModel.class)
                .build();


        recyclerView = findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new resRVAdapter(options);
        recyclerView.setAdapter(adapter);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.logOut_menu) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(Home.this, MainActivity.class));
                }

                return true;
            }
        });

        onStart();


    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private interface firebaseCallback {
        void onCallback(String s);
    }

    private void readCity(firebaseCallback firebaseCallback){

        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DetialsModel model = snapshot.getValue(DetialsModel.class);
                mCity = model.getCity();
              //  model.setCity(mCity);
                firebaseCallback.onCallback(mCity);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}