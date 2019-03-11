package com.abed.leitnerflashcards.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.abed.leitnerflashcards.R;
import com.abed.leitnerflashcards.data.Card;
import com.abed.leitnerflashcards.utils.DateUtil;
import com.abed.leitnerflashcards.data.Repository;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRequestNextPageListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyViewPagerAdapter adapter;
    private NonSwipeableViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.viewPager);
        adapter = new MyViewPagerAdapter(this);
        pager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDueCards();
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

    private void getDueCards() {
        Repository.getInstance(getApplication()).getDueCards(DateUtil.getCalendarWithoutTime()).addOnSuccessListener((List<Card> cards) -> {
            if (!cards.isEmpty())
                adapter.setCards(cards);
        }); /*Consider removing success listener on activity onDestroy to prevent leaks.
        Leaks might happen if activity is destroyed before asyntask is completed. Because asynctask will have reference to this activity
         it will not be garbage collected. */
    }

    @Override
    public void onRequestNextPage() {
        int lastIndex = adapter.getCount() - 1;
        int nexIndex = pager.getCurrentItem() + 1;

        if (nexIndex <= lastIndex) {
            pager.setCurrentItem(nexIndex);
        } else {
            recreate(); // Recreates the activity Todo: find better way to display level 1 cards again when on last page.
        }
    }
}
