package com.abed.leitnerflashcards.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abed.leitnerflashcards.R;
import com.abed.leitnerflashcards.data.Card;
import com.abed.leitnerflashcards.data.Repository;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyViewPagerAdapter adapter;
    private NonSwipeableViewPager pager;
    private Group actionButtons;
    private Button btnShow;
    private TextView tvEmptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "OnCreate");
        pager = findViewById(R.id.viewPager);
        btnShow = findViewById(R.id.btnShow);
        actionButtons = findViewById(R.id.actionButtons);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);

        adapter = new MyViewPagerAdapter(this);
        pager.setAdapter(adapter);

        Repository.getInstance(getApplication()).getDueCards(LocalDate.now()).observe(this, (List<Card> cards) -> {
            if (cards != null && !cards.isEmpty()) {
                hideEmptyState();
                adapter.setData(cards);
            }
        });

        btnShow.setOnClickListener((View v) -> {
            pager.findViewWithTag(pager.getCurrentItem()).setVisibility(View.VISIBLE); // show card back text
            btnShow.setVisibility(View.GONE);
            actionButtons.setVisibility(View.VISIBLE);
        });

        findViewById(R.id.btnCorrect).setOnClickListener((View v) -> {
            // Level up card
            int pos = pager.getCurrentItem();
            Card card = adapter.getCard(pos);

            Log.i(TAG, "Card info: id: " + card.getId() + " level: " + card.getLevel() + " dueDate: " + card.getDueDate());

            card.levelUp();
            Repository.getInstance(getApplication()).updateCard(card);
            // then
            showNextPage();
        });

        findViewById(R.id.btnWrong).setOnClickListener((View v) -> {
            // Level down card
            int pos = pager.getCurrentItem();
            Card card = adapter.getCard(pos);

            Log.i(TAG, "Card info: id: " + card.getId() + " level: " + card.getLevel() + " dueDate: " + card.getDueDate());

            card.levelDown();
            Repository.getInstance(getApplication()).updateCard(card);
            // then
            showNextPage();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(this, AddActivity.class));
                break;
            case R.id.delete:
                break;
        }
        return true;
    }

    private void showEmptyState() {
        tvEmptyMessage.setVisibility(View.VISIBLE);
        btnShow.setVisibility(View.GONE);
    }

    private void hideEmptyState() {
        tvEmptyMessage.setVisibility(View.GONE);
        btnShow.setVisibility(View.VISIBLE);
    }

    private void showNextPage() {
        //int last = pager.getChildCount() + 1;
        int last = adapter.getCount() - 1;
        int next = pager.getCurrentItem() + 1;
        Log.i(TAG, "next: " + next + " last: " + last);
        if (!(next > last)) {
            pager.setCurrentItem(next);
            actionButtons.setVisibility(View.GONE);
            btnShow.setVisibility(View.VISIBLE);
        } else {
            actionButtons.setVisibility(View.GONE);
            btnShow.setVisibility(View.GONE);
            adapter.clearData();
            showEmptyState();
        }
    }
}
