package com.abed.leitnerflashcards;

import android.support.constraint.Group;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyViewPagerAdapter adapter;
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
        actionButtons = findViewById(R.id.buttons);
        Button btnCorrect = findViewById(R.id.btnCorrect);
        Button btnWrong = findViewById(R.id.btnWrong);
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage);

        List<String> data = Arrays.asList("one", "two", "three", "four", "five"); //new ArrayList<>();
        hideEmptyMessage();
        adapter = new MyViewPagerAdapter(this, data);
        pager.setAdapter(adapter);

        btnShow.setOnClickListener((View v) -> {
            pager.findViewWithTag(pager.getCurrentItem()).setVisibility(View.VISIBLE); // show card back text
            btnShow.setVisibility(View.GONE);
            actionButtons.setVisibility(View.VISIBLE);

        });

        btnCorrect.setOnClickListener((View v) -> {
            showNext();
            // logic
        });

        btnWrong.setOnClickListener((View v) -> {
            showNext();
            // logic
        });
    }

    private void showEmptyMessage() {
        tvEmptyMessage.setVisibility(View.VISIBLE);
        btnShow.setVisibility(View.GONE);
    }

    private void hideEmptyMessage() {
        tvEmptyMessage.setVisibility(View.GONE);
        btnShow.setVisibility(View.VISIBLE);
    }

    private void showNext() {
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
            showEmptyMessage();
        }
    }
}
