package com.sara.note.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sara.note.Entity.NotesEntity;

import java.util.List;

@Dao
public interface NotesDao { //get data

    @Query("SELECT * FROM NotesDatabase")
    LiveData<List<NotesEntity>> getallNotes();

    @Query("SELECT * FROM NotesDatabase ORDER BY notesPriority DESC")
    LiveData<List<NotesEntity>> high_low();

    @Query("SELECT * FROM NotesDatabase ORDER BY notesPriority ASC")
    LiveData<List<NotesEntity>> low_high();

    @Query("SELECT * FROM NotesDatabase WHERE notesTitle LIKE :query OR notesDescription LIKE :query")
    LiveData<List<NotesEntity>> searchNotes(String query);

    @Insert
    void insertNotes(NotesEntity notes);

    @Query("DELETE FROM NotesDatabase WHERE id= :id")
    void deleteNotes(int id);

    @Update
    void  updateNotes(NotesEntity notes);
}
