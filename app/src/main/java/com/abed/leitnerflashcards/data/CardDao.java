package com.abed.leitnerflashcards.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards ORDER BY level ASC")
    public List<Card> getAll();

    @Query("SELECT * FROM cards WHERE CAST(dueDate as date) <= CAST(:nowDate as date) ORDER BY level DESC")
    public List<Card> getDue(Calendar nowDate);

    @Insert
    public void insert(Card card);

    @Update
    public void update(Card card);

    @Delete
    public void delete(Card card);

}
