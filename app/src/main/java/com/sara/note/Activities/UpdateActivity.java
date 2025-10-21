package com.sara.note.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sara.note.Entity.NotesEntity;
import com.sara.note.R;
import com.sara.note.ViewModel.NotesViewModel;
import com.sara.note.databinding.ActivityAddnewBinding;
import com.sara.note.databinding.ActivityUpdateBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {
    ActivityUpdateBinding binding;

    String priorityStandard="1";
    String sTitle, sDescription,sPriority;
    int sId;
    NotesViewModel notesViewModel;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding= ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog sheetDialog=new BottomSheetDialog(UpdateActivity.this);

                View Del= LayoutInflater.from(UpdateActivity.this)
                        .inflate(R.layout.dalete_bottom_sheet,(LinearLayout)findViewById(R.id.delete_main));
                sheetDialog.setContentView(Del);

                TextView yesBtn, noBtn;

                yesBtn=Del.findViewById(R.id.yesBtn);
                noBtn=Del.findViewById(R.id.noBtn);

                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    notesViewModel.deleteNotes(sId);
                    finish();
                    }
                });
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sheetDialog.dismiss();
                    }
                });
                sheetDialog.show();
            }
        });



        notesViewModel= new ViewModelProvider(this).get(NotesViewModel.class);

        sId=getIntent().getIntExtra("id",0);
        sTitle=getIntent().getStringExtra("title");
        sDescription=getIntent().getStringExtra("description");
        sPriority=getIntent().getStringExtra("priority");

        binding.UpTitle.setText(sTitle);
        binding.UpDescription.setText(sDescription);
        if ("1".equals(sPriority)) {
            binding.black.setImageResource(R.drawable.tick);
        } else if ("2".equals(sPriority)) {
            binding.grey.setImageResource(R.drawable.tick);
        } else if ("3".equals(sPriority)) {
            binding.white.setImageResource(R.drawable.tick);
        }

        binding.black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.black.setImageResource(R.drawable.tick);
                binding.grey.setImageResource(0);
                binding.white.setImageResource(0);
                Toast.makeText(UpdateActivity.this, "Priority set to maximum", Toast.LENGTH_SHORT).show();

                priorityStandard="1";
            }
        });

        binding.grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.black.setImageResource(0);
                binding.grey.setImageResource(R.drawable.tick);
                binding.white.setImageResource(0);
                Toast.makeText(UpdateActivity.this, "Priority set to medium", Toast.LENGTH_SHORT).show();
                priorityStandard="2";
            }
        });

        binding.white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.black.setImageResource(0);
                binding.grey.setImageResource(0);
                binding.white.setImageResource(R.drawable.tick);
                Toast.makeText(UpdateActivity.this, "Priority set to minimum", Toast.LENGTH_SHORT).show();
                priorityStandard="3";
            }
        });

        binding.UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title=binding.UpTitle.getText().toString();
                String description=binding.UpDescription.getText().toString();

                UpdateNotes(title,description);

            }

            private void UpdateNotes(String title, String description) {
                NotesEntity updateNotes=new NotesEntity();

                Date date=new Date();
                SimpleDateFormat format=new SimpleDateFormat("MMMM, d, yyyy");
                String formattedDate = format.format(date);

                updateNotes.id=sId;
                updateNotes.notesTitle=title;
                updateNotes.notesDescription=description;
                updateNotes.notesDate = formattedDate;
                updateNotes.notesPriority=priorityStandard;

                notesViewModel.updateNotes(updateNotes);
                finish();
            }

        });
    }
}