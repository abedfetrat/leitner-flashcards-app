package com.abed.leitnerflashcards.data;

import android.content.Context;
import android.os.AsyncTask;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository INSTANCE;

    private Executor executor;
    private CardDao dao;

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

    public GetAllCardsTask getAllCards() {
        GetAllCardsTask task = new GetAllCardsTask(dao);
        task.execute();
        return task;
    }

    public GetDueCardsTask getDueCards(Calendar nowDate) {
        GetDueCardsTask task = new GetDueCardsTask(dao);
        task.execute(nowDate);
        return task;
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

    public static class GetAllCardsTask extends AsyncTask<Void, Void, List<Card>> {
        private OnSuccessListener<List<Card>> listener;
        private CardDao dao;
        GetAllCardsTask (CardDao d) {
            dao = d;
        }

        @Override
        protected List<Card> doInBackground(Void... Void) {
            return dao.getAll();
        }

        @Override
        protected void onPostExecute(List<Card> cards) {
            super.onPostExecute(cards);
            if (cards != null && listener != null)
                listener.onSuccess(cards);
        }

        public void addOnSuccessListener(OnSuccessListener<List<Card>> listener) {
            this.listener = listener;
        }

    }

    public static class GetDueCardsTask extends AsyncTask<Calendar, Void, List<Card>> {
        private OnSuccessListener<List<Card>> listener;
        private CardDao dao;
        GetDueCardsTask (CardDao d) {
            dao = d;
        }

        @Override
        protected List<Card> doInBackground(Calendar... nowDate) {
            return dao.getDue(nowDate[0]);
        }

        @Override
        protected void onPostExecute(List<Card> cards) {
            super.onPostExecute(cards);
            if (cards != null && listener != null)
                listener.onSuccess(cards);
        }

        public void addOnSuccessListener(OnSuccessListener<List<Card>> listener) {
            this.listener = listener;
        }

    }

    public interface OnSuccessListener<T> {
        void onSuccess(T t);
    }
}
