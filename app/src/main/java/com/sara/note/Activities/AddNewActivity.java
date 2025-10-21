package com.sara.note.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.sara.note.Entity.NotesEntity;
import com.sara.note.R;
import com.sara.note.ViewModel.NotesViewModel;
import com.sara.note.databinding.ActivityAddnewBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewActivity extends AppCompatActivity {
    ActivityAddnewBinding binding;
    String title,description;
    NotesViewModel notesViewModel;  // variable of NotesViewModel

    String priorityStandard="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding= ActivityAddnewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        notesViewModel= new ViewModelProvider(this).get(NotesViewModel.class);
        //ViewModelProvider(this) → Tells Android: “Give me the ViewModel that belongs to this Activity.”
        //.get(NotesViewModel.class) → Specifies which ViewModel you want (here, NotesViewModel).


        binding.black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.black.setImageResource(R.drawable.tick);
                binding.grey.setImageResource(0);
                binding.white.setImageResource(0);
                Toast.makeText(AddNewActivity.this, "Priority set to low", Toast.LENGTH_SHORT).show();

                priorityStandard="1";
            }
        });

        binding.grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.black.setImageResource(0);
                binding.grey.setImageResource(R.drawable.tick);
                binding.white.setImageResource(0);

                Toast.makeText(AddNewActivity.this, "Priority set to medium", Toast.LENGTH_SHORT).show();

                priorityStandard="2";
            }
        });

        binding.white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.black.setImageResource(0);
                binding.grey.setImageResource(0);
                binding.white.setImageResource(R.drawable.tick);

                Toast.makeText(AddNewActivity.this, "Priority set to high", Toast.LENGTH_SHORT).show();

                priorityStandard="3";
            }
        });



        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=binding.title.getText().toString();
                description=binding.description.getText().toString();

                CreateNotes(title,description);
            }
        });
    }
    private void CreateNotes(String title, String description) {
        NotesEntity notes=new NotesEntity();

        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("MMMM, d, yyyy");

        String formattedDate = format.format(date);
        notes.notesTitle=title;
        notes.notesDescription=description;
        notes.notesDate = formattedDate;
        notes.notesPriority=priorityStandard;

        notesViewModel.insertNotes(notes);
        finish();
    }
}