package com.abed.leitnerflashcards.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abed.leitnerflashcards.R;
import com.abed.leitnerflashcards.data.Card;
import com.abed.leitnerflashcards.data.FileManager;
import com.abed.leitnerflashcards.data.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    private static final String TAG = MyViewPagerAdapter.class.getSimpleName();
    private OnRequestNextPageListener onRequestNextPageListener;
    private Context context;
    private List<Card> cards;

    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;

    public MyViewPagerAdapter(Context context) {
        this.context = context;
        cards = new ArrayList<>();

        try {
            onRequestNextPageListener = (OnRequestNextPageListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnRequestNextPageListener");
        }

        mediaPlayer1 = new MediaPlayer();
        mediaPlayer2 = new MediaPlayer();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_item, container, false);

        TextView tvIndex = layout.findViewById(R.id.tvIndex);
        String index = position + 1 + " / " + cards.size();
        tvIndex.setText(index);

        Card card = cards.get(position);

        ImageView imv = layout.findViewById(R.id.imageView);
        if (card.getImageFilePath() != null) {
            // Get and set image
            FileManager.getInstance(context).getFile(card.getImageFilePath()).addOnSuccessListener((File file) -> {
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                imv.setVisibility(View.VISIBLE);
                imv.setImageBitmap(bm);
            });
        }
        // Get audio and prepare media player
        if (card.getFrontAudiFilePath() != null) {
            FileManager.getInstance(context).getFile(card.getFrontAudiFilePath()).addOnSuccessListener(this::prepareMediaPlayer1);
        }

        if (card.getBackAudioFilePath() != null) {
            FileManager.getInstance(context).getFile(card.getBackAudioFilePath()).addOnSuccessListener(this::prepareMediaPlayer2);
        }

        TextView tvFrontText = layout.findViewById(R.id.tvFrontText);
        tvFrontText.setText(card.getFrontText());
        tvFrontText.setOnClickListener((View v) -> {
            // play audio
            mediaPlayer1.start();
        });

        TextView tvBackText = layout.findViewById(R.id.tvBackText);
        tvBackText.setText(card.getBackText());
        tvBackText.setOnClickListener((View v) -> {
            // play audio
            mediaPlayer2.start();
        });

        Group actionButtons = layout.findViewById(R.id.actionButtons);
        layout.findViewById(R.id.btnWrong).setOnClickListener((View v) -> {
            /*if (card.getLevel() != Card.LEVEL_1) {
                card.levelDown();
                Repository.getInstance(context).updateCard(card);
                cards.remove(position);
                notifyDataSetChanged();
            }*/
            card.levelDown();
            Repository.getInstance(context).updateCard(card);
            onRequestNextPageListener.onRequestNextPage();
        });
        layout.findViewById(R.id.btnCorrect).setOnClickListener((View v) -> {
            card.levelUp();
            Repository.getInstance(context).updateCard(card);
            /*cards.remove(position);
            notifyDataSetChanged();*/
            onRequestNextPageListener.onRequestNextPage();
        });

        Button btnShow = layout.findViewById(R.id.btnShow);
        btnShow.setOnClickListener((View v) -> {
            tvBackText.setVisibility(View.VISIBLE);
            actionButtons.setVisibility(View.VISIBLE);
            btnShow.setVisibility(View.GONE);
        });

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    // Needed to clear all adapter items when notifydatasetchanged is called
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setCards(List<Card> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
        notifyDataSetChanged();
    }

    private void prepareMediaPlayer1(File file) {
        try {
            mediaPlayer1.setDataSource(file.getPath());
            mediaPlayer1.prepare();
        } catch (Exception e) {
            Log.i(TAG, "" + e.getMessage());
        }
    }

    private void prepareMediaPlayer2(File file) {
        try {
            mediaPlayer2.setDataSource(file.getPath());
            mediaPlayer2.prepare();
        } catch (Exception e) {
            Log.i(TAG, "" + e.getMessage());
        }
    }
}