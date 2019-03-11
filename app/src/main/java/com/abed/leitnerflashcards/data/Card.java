package com.abed.leitnerflashcards.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.abed.leitnerflashcards.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity(tableName = "cards")
public class Card {
    public static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    private static final int LEVEL_4 = 4;
    private static final int LEVEL_5 = 5;

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int level;
    private Calendar dueDate;
    private String imageFilePath;
    private String frontText;
    private String frontAudiFilePath;
    private String backText;
    private String backAudioFilePath;

    @Ignore
    public Card(String imageFilePath, String frontText, String frontAudiFilePath, String backText, String backAudioFilePath) {
        level = 1;
        dueDate = DateUtil.getCalendarWithoutTime();

        this.imageFilePath = imageFilePath;
        this.frontText = frontText;
        this.frontAudiFilePath = frontAudiFilePath;
        this.backText = backText;
        this.backAudioFilePath = backAudioFilePath;
    }

    public Card(int id, int level, Calendar dueDate, String imageFilePath, String frontText, String frontAudiFilePath, String backText, String backAudioFilePath) {
        this.id = id;
        this.level = level;
        this.dueDate = dueDate;
        this.imageFilePath = imageFilePath;
        this.frontText = frontText;
        this.frontAudiFilePath = frontAudiFilePath;
        this.backText = backText;
        this.backAudioFilePath = backAudioFilePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getFrontText() {
        return frontText;
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }

    public String getFrontAudiFilePath() {
        return frontAudiFilePath;
    }

    public void setFrontAudiFilePath(String frontAudiFilePath) {
        this.frontAudiFilePath = frontAudiFilePath;
    }

    public String getBackText() {
        return backText;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public String getBackAudioFilePath() {
        return backAudioFilePath;
    }

    public void setBackAudioFilePath(String backAudioFilePath) {
        this.backAudioFilePath = backAudioFilePath;
    }

    public String getDueDateInString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(dueDate.getTime());
    }

    public void levelUp() {
        if (level < LEVEL_5) {
            level++;
            updateDueDate();
        }
    }

    public void levelDown() {
        // if already level 1 then set due today
        if (level == LEVEL_1) {
            dueDate = DateUtil.getCalendarWithoutTime();
        } else {
            level = LEVEL_1;
            updateDueDate();
        }
    }

    private void updateDueDate() {
        switch (level) {
            case LEVEL_1:
                dueDate = DateUtil.getCalendarPlusDays(1);
                break;
            case LEVEL_2:
                dueDate = DateUtil.getCalendarPlusDays(2);
                break;
            case LEVEL_3:
                dueDate = DateUtil.getCalendarPlusDays(4);
                break;
            case LEVEL_4:
                dueDate = DateUtil.getCalendarPlusDays(8);
                break;
            case LEVEL_5:
                dueDate = null;
                break;
        }
    }
}
