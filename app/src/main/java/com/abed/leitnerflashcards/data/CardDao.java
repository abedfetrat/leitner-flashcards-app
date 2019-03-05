package com.abed.leitnerflashcards.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards ORDER BY id DESC")
    public CardEntity[] getAll();

    @Query("SELECT * FROM cards WHERE dueDate = :nowDate ORDER BY level DESC")
    public CardEntity[] getDue(String nowDate);

    @Insert
    public void insert(CardEntity card);

    @Update
    public void update(CardEntity card);

    @Delete
    public void delete(CardEntity card);

}
