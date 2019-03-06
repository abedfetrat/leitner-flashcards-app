package com.abed.leitnerflashcards.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository INSTANCE;

    private Executor executor;
    private CardDao dao;

    private LiveData<List<Card>> allCards;
    private LiveData<List<Card>> dueCards;

    public static Repository getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(context);
        }
        return INSTANCE;
    }

    private Repository(Context context) {
        executor = Executors.newSingleThreadExecutor();
        dao = AppDatabase.getDatabase(context).cardDao();
    }

    public LiveData<List<Card>> getAllCards() {
        if (allCards == null)
            allCards = dao.getAll();
        return allCards;
    }

    public LiveData<List<Card>> getDueCards(LocalDate nowDate) {
        if (dueCards == null)
            dueCards = dao.getDue(nowDate);
        return dueCards;
    }

    public void insertCard(Card card) {
        executor.execute(() -> dao.insert(card));
    }

    public void updateCard(Card card) {
        executor.execute(() -> dao.update(card));
    }

    public void deleteCard(Card card) {
        executor.execute(() -> dao.delete(card));
    }
}
