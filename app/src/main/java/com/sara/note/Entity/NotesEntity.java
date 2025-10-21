package com.sara.note.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// to make columns


@Entity(tableName = "NotesDatabase")
public class NotesEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "notesTitle")
    public String notesTitle;

    @ColumnInfo(name = "notesDescription")
    public String notesDescription;

    @ColumnInfo(name = "notesDate")
    public String notesDate;

    @ColumnInfo(name = "notesPriority")
    public String notesPriority;
}
