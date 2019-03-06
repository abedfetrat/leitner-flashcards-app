package com.abed.leitnerflashcards.ui;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abed.leitnerflashcards.R;
import com.abed.leitnerflashcards.data.Card;

import java.util.ArrayList;
import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Card> data;

    public MyViewPagerAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_item, container, false);

        TextView tvIndex = layout.findViewById(R.id.tvIndex);
        String index = position + 1 + " / " + data.size();
        tvIndex.setText(index);

        /*
        if (data.get(position).getImagePath != null) {
            ImageView imv = layout.findViewById(R.id.imageView);
            imv.setVisibility(View.VISIBLE);
            // Get and set image
        }
        */

        TextView tvFrontText = layout.findViewById(R.id.tvFrontText);
        tvFrontText.setText(data.get(position).getFrontText());
        tvFrontText.setOnClickListener((View v) -> {
            // play audio
        });

        TextView tvBackText = layout.findViewById(R.id.tvBackText);
        tvBackText.setText(data.get(position).getBackText());
        tvBackText.setTag(position);
        tvBackText.setOnClickListener((View v) -> {
            // play audio
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
        return data.size();
    }

    // Clears all items when notifydatasetchanged is called
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setData(List<Card> cards) {
        data.clear();
        data.addAll(cards);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }
}
