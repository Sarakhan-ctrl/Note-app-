package com.sara.note.Activities;

import android.app.ComponentCaller;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.sara.note.Adapter.NotesAdapter;
import com.sara.note.R;
import com.sara.note.ViewModel.NotesViewModel;

  public class MainActivity extends AppCompatActivity {
      ImageView addBtn;
      EditText searchBtn;
      NotesViewModel notesViewModel;  // variable of NotesViewModel
      RecyclerView recycler;
      NotesAdapter notesAdapter;
      TextView noFilter, high_low, low_high;
      NavigationView navigationLayout;
      DrawerLayout drawerLayout;
      Toolbar toolbar;
      ImageView myImage;

      public  final int CAMERA_REQUEST_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true){
                    @Override
                    public void handleOnBackPressed() {
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                            return;
                        } else{
                            MainActivity.super.onBackPressed();
                        }
                    }
                });

        drawerLayout=findViewById(R.id.drawerLayout);
        navigationLayout=findViewById(R.id.navigationLayout);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationLayout.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();
                if (id==R.id.home){
                    Intent iHome=new Intent(MainActivity.this, Home.class);
                    startActivity(iHome);
                } else if (id==R.id.trash) {
                    Intent iHome=new Intent(MainActivity.this, trash.class);
                    startActivity(iHome);
                } else if (id==R.id.account) {
                    Intent iHome=new Intent(MainActivity.this, account.class);
                    startActivity(iHome);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



        addBtn=findViewById(R.id.addBtn);
        recycler=findViewById(R.id.recycler);
        addBtn.setOnClickListener(new View.OnClickListener() {    // switching to new activity name "AddNewActivity" after clicking to + button.
            @Override
            public void onClick(View view) {
                Intent i;
                i=new Intent(MainActivity.this, AddNewActivity.class);
                startActivity(i);
            }
        });

        notesViewModel= new ViewModelProvider(this).get(NotesViewModel.class);
        //ViewModelProvider(this) → Tells Android: “Give me the ViewModel that belongs to this Activity.”
        // .get(NotesViewModel.class) → Specifies which ViewModel you want (here, NotesViewModel).




        notesViewModel.getallNotes.observe(this,notesEntities -> {
            recycler.setLayoutManager(new GridLayoutManager(this,2));
            notesAdapter=new NotesAdapter(MainActivity.this,notesEntities);
            recycler.setAdapter(notesAdapter);


            searchBtn = findViewById(R.id.searchBtn);
            searchBtn.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().isEmpty()) {
                        notesViewModel.searchNotes(s.toString()).observe(MainActivity.this, notes -> {
                            notesAdapter = new NotesAdapter(MainActivity.this, notes);
                            recycler.setAdapter(notesAdapter);
                        });
                    } else {
                        // Show all notes when search is cleared
                        notesViewModel.getallNotes.observe(MainActivity.this, notes -> {
                            notesAdapter = new NotesAdapter(MainActivity.this, notes);
                            recycler.setAdapter(notesAdapter);
                        });
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {}
            });


        });
        noFilter=findViewById(R.id.noFilter);
        high_low=findViewById(R.id.high_low);
        low_high=findViewById(R.id.low_high);

        noFilter.setBackgroundResource(R.drawable.light_grey_bar_selected);

        noFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noFilter.setBackgroundResource(R.drawable.light_grey_bar_selected);
                high_low.setBackgroundResource(R.drawable.light_grey_bar);
                low_high.setBackgroundResource(R.drawable.light_grey_bar);

                notesViewModel.getallNotes.observe(MainActivity.this,notesEntities -> {
                    recycler.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    notesAdapter=new NotesAdapter(MainActivity.this,notesEntities);
                    recycler.setAdapter(notesAdapter);

                });

            }
        });

        high_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                high_low.setBackgroundResource(R.drawable.light_grey_bar_selected);
                low_high.setBackgroundResource(R.drawable.light_grey_bar);
                noFilter.setBackgroundResource(R.drawable.light_grey_bar);

                notesViewModel.high_low.observe(MainActivity.this,notesEntities -> {
                    recycler.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    notesAdapter=new NotesAdapter(MainActivity.this,notesEntities);
                    recycler.setAdapter(notesAdapter);

                });
            }
        });

        low_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low_high.setBackgroundResource(R.drawable.light_grey_bar_selected);
                high_low.setBackgroundResource(R.drawable.light_grey_bar);
                noFilter.setBackgroundResource(R.drawable.light_grey_bar);

                notesViewModel.low_high.observe(MainActivity.this,notesEntities -> {
                    recycler.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    notesAdapter=new NotesAdapter(MainActivity.this,notesEntities);
                    recycler.setAdapter(notesAdapter);

                });
            }
        });

        //________________________________________________camera import______________
        navigationLayout=findViewById(R.id.navigationLayout);
        View headerView = navigationLayout.getHeaderView(0);
        myImage = headerView.findViewById(R.id.myImage);

        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageSet=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(imageSet,CAMERA_REQUEST_CODE);
            }
        });
    }

      @Override
      public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          if(resultCode==RESULT_OK){
              if(requestCode==CAMERA_REQUEST_CODE){
                  Bitmap img=(Bitmap)data.getExtras().get("data");
                  myImage.setImageBitmap(img);
              }
          }
      }

  }