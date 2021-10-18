package com.example.dragonfly_main;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(NewNote note);
    @Update
    void update(NewNote note);
    @Delete
    void delete(NewNote note);
    @Query("DELETE FROM note_table")
    void deleteAllNotes();
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<NewNote>> getAllNotes();
}
