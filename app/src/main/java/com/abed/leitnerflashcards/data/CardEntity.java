package com.abed.leitnerflashcards.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "cards")
public class CardEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int level;
    public String dueDate;
    public String imageFilePath;
    @NonNull
    public String frontText;
    public String frontAudiFilePath;
    public String backText;
    public String backAudioFilePath;

    public CardEntity(int level, String dueDate, String imageFilePath, @NonNull String frontText,
                      String frontAudiFilePath, String backText, String backAudioFilePath) {
        this.level = level;
        this.dueDate = dueDate;
        this.imageFilePath = imageFilePath;
        this.frontText = frontText;
        this.frontAudiFilePath = frontAudiFilePath;
        this.backText = backText;
        this.backAudioFilePath = backAudioFilePath;
    }
}
