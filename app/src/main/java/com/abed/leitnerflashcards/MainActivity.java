package com.abed.leitnerflashcards;

import android.os.Debug;
import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private NonSwipeableViewPager pager;
    private Group actionButtons;
    private Button btnShow;
    private TextView tvEmptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.viewPager);
        btnShow = findViewById(R.id.btnShow);
        actionButtons = findViewById(R.id.actionButtons);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);

        List<String> data = Arrays.asList("one", "two", "three", "four", "five"); //new ArrayList<>();
        hideEmptyState();

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, data);
        pager.setAdapter(adapter);

        btnShow.setOnClickListener((View v) -> {
            pager.findViewWithTag(pager.getCurrentItem()).setVisibility(View.VISIBLE); // show card back text
            btnShow.setVisibility(View.GONE);
            actionButtons.setVisibility(View.VISIBLE);
        });

        findViewById(R.id.btnCorrect).setOnClickListener((View v) -> {
            // Level up card
            int pos = pager.getCurrentItem();
            Log.i(TAG, "item position: " + pos + "item: " + data.get(pos));
            // then
            showNextPage();
        });

        findViewById(R.id.btnWrong).setOnClickListener((View v) -> {
            // Level down card
            int pos = pager.getCurrentItem();
            Log.i(TAG, "item position: " + pos + "item: " + data.get(pos));
            // then
            showNextPage();
        });
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
        int last = pager.getChildCount() + 1;
        int next = pager.getCurrentItem() + 1;
        if (!(next > last)) {
            pager.setCurrentItem(next);
            actionButtons.setVisibility(View.GONE);
            btnShow.setVisibility(View.VISIBLE);
        } else {
            actionButtons.setVisibility(View.GONE);
            btnShow.setVisibility(View.GONE);
            pager.setAdapter(null);
            showEmptyState();
        }
    }
}
