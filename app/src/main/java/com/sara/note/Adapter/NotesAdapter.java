package com.sara.note.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sara.note.Activities.MainActivity;
import com.sara.note.Activities.UpdateActivity;
import com.sara.note.Entity.NotesEntity;
import com.sara.note.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MainActivity mainActivity;
    List<NotesEntity> notesEntities;

    public NotesAdapter(MainActivity mainActivity, List<NotesEntity> notesEntities) {
        this.mainActivity=mainActivity;
        this.notesEntities=notesEntities;
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=(LayoutInflater.from(mainActivity).inflate(R.layout.recycler_item,parent,false));
        return new notesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(notesViewHolder holder, int position) {

        NotesEntity note=notesEntities.get(position);

        if ("1".equals(note.notesPriority)) {
            holder.notesPriority.setImageResource(R.drawable.black_circle);
        } else if ("2".equals(note.notesPriority)) {
            holder.notesPriority.setImageResource(R.drawable.grey_circle);
        } else if ("3".equals(note.notesPriority)) {
            holder.notesPriority.setImageResource(R.drawable.white_circle);
        }

        holder.title.setText(note.notesTitle);
        holder.description.setText(note.notesDescription);
        holder.date.setText(note.notesDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() { // when click on an item pass the intent to update activity with the data
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(mainActivity, UpdateActivity.class);
// to pass data through key and data
            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("description",note.notesDescription);
            intent.putExtra("priority",note.notesPriority);
            mainActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesEntities.size();
    }

    public void updateList(List<NotesEntity> newList) {
        this.notesEntities = newList;
        notifyDataSetChanged();
    }

    public static class notesViewHolder extends RecyclerView.ViewHolder{
        TextView title,description,date;
        ImageView notesPriority;

        public notesViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.notes_title);
            description=itemView.findViewById(R.id.notes_description);
            date=itemView.findViewById(R.id.notes_date);
            notesPriority=itemView.findViewById(R.id.notesPriority);
        }
    }

}
