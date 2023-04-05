package com.example.koreanrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.koreanrestaurantapp.Common.Common;
import com.example.koreanrestaurantapp.Interface.ItemClickListener;
import com.example.koreanrestaurantapp.ViewHolder.MenuViewHolder;
import com.example.koreanrestaurantapp.model.Category;
import com.example.koreanrestaurantapp.model.Order;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.koreanrestaurantapp.databinding.ActivityHomeBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.ktx.Firebase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");

        //init  Firebase
        database= FirebaseDatabase.getInstance();
        category= database.getReference("Category");

        setSupportActionBar(binding.appBarHome.toolbar);
        FloatingActionButton fab= (FloatingActionButton)  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent= new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set Name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName= (TextView) headerView.findViewById(R.id.txtFullName);
       // txtFullName.setText(Common.currentUser.getName());

        nv= findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id== R.id.nav_menu){

                } else if (id == R.id.nav_cart) {
                    Intent cartIntent= new Intent(Home.this, Cart.class);
                    startActivity(cartIntent);
                    Toast.makeText(Home.this,"Cart",Toast.LENGTH_SHORT).show();
                } else if (id==R.id.nav_orders) {
                    Intent orderIntent= new Intent(Home.this, OrderStatus.class);
                    startActivity(orderIntent);
                    Toast.makeText(Home.this,"Order",Toast.LENGTH_SHORT).show();
                } else if (id==R.id.nav_logout) {
                    Intent signOutIntent= new Intent(Home.this, MainActivity.class);
                    signOutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(signOutIntent);
                }
                DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        //Load Menu
        recycler_menu=(RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();
    }

    private void loadMenu() {

        adapter= new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                menuViewHolder.txtMenuName.setText((category.getName()));
                Picasso.with(getBaseContext()).load(category.getImage()).into(menuViewHolder.imageView);
                menuViewHolder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(Home.this,""+ category.getImage(),Toast.LENGTH_SHORT).show();
                        //Get Category and send to new Activity
                        Intent foodList= new Intent(Home.this,FoodList.class);
                        //
                        foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id== R.id.nav_menu){

        } else if (id == R.id.nav_cart) {
            Intent cartIntent= new Intent(Home.this, Cart.class);
            startActivity(cartIntent);
            Toast.makeText(Home.this,"Cart",Toast.LENGTH_SHORT).show();
        } else if (id==R.id.nav_orders) {
            Intent orderIntent= new Intent(Home.this, OrderStatus.class);
            startActivity(orderIntent);
        } else if (id==R.id.nav_logout) {
            Intent signOutIntent= new Intent(Home.this, MainActivity.class);
            signOutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signOutIntent);
        }
        DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }
*/


}