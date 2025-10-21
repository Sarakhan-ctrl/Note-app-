package com.sara.note.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sara.note.Dao.NotesDao;
import com.sara.note.Entity.NotesEntity;

@Database(entities = {NotesEntity.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    // THIS IS A METHOD, NOT A FIELD
    public abstract NotesDao notesDao();

    private static NotesDatabase INSTANCE;

    public static NotesDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDatabase.class, "NotesDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
