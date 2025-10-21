package com.sara.note.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sara.note.Entity.NotesEntity;
import com.sara.note.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository notesRepository;
    public LiveData<List<NotesEntity>> getallNotes;
    public LiveData<List<NotesEntity>> high_low;
    public LiveData<List<NotesEntity>> low_high;
    public NotesViewModel(@NonNull Application application) {
        super(application);

        notesRepository=new NotesRepository(application);
        getallNotes=notesRepository.getallNotes;
        high_low=notesRepository.high_low;
        low_high=notesRepository.low_high;

    }
    public void insertNotes(NotesEntity notes){
        notesRepository.insertNotes(notes);
    }
    public void  deleteNotes(int id){
        notesRepository.deleteNotes(id);
    }
    public void updateNotes(NotesEntity notes){
        notesRepository.updateNotes(notes);
    }
    public LiveData<List<NotesEntity>> searchNotes(String query) {
        return notesRepository.searchNotes(query);
    }

}
