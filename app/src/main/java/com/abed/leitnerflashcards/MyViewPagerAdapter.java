package com.abed.leitnerflashcards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> data;

    public MyViewPagerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_item, container, false);

        LinearLayout back = layout.findViewById(R.id.back);
        back.setTag(position);

        TextView tvFront = layout.findViewById(R.id.textView);
        tvFront.setText(data.get(position));
        /*
        TextView tvBack = layout.findViewById(R.id.textView2);
        tvBack.setText(data.get(position));
        */
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

}
