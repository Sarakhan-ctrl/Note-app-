package com.sara.note.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.sara.note.Dao.NotesDao;
import com.sara.note.Entity.NotesEntity;
import com.sara.note.database.NotesDatabase;

import java.util.List;

public class NotesRepository {
    public NotesDao notesDao;
    public LiveData<List<NotesEntity>> getallNotes;
    public LiveData<List<NotesEntity>> high_low;
    public LiveData<List<NotesEntity>> low_high;
    public NotesRepository(Application application){
       NotesDatabase database=NotesDatabase.getDatabaseInstance(application);
       notesDao=database.notesDao();
       getallNotes= notesDao.getallNotes();
       high_low=notesDao.high_low();
       low_high=notesDao.low_high();
    }
    public void insertNotes(NotesEntity notes){
        notesDao.insertNotes(notes);
    }
    public void deleteNotes(int id){
        notesDao.deleteNotes(id);
    }
    public void updateNotes(NotesEntity notes){
        notesDao.updateNotes(notes);
    }
    public LiveData<List<NotesEntity>> searchNotes(String query) {
        return notesDao.searchNotes("%" + query + "%");
    }

}
