package com.abed.leitnerflashcards.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.abed.leitnerflashcards.R;
import com.abed.leitnerflashcards.data.Card;
import com.abed.leitnerflashcards.data.Repository;

import java.util.ArrayList;
import java.util.List;

public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.ViewHolder> {
    private Context context;
    private List<Card> cards;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFrontText;
        TextView tvBackText;
        TextView tvLevel;
        TextView tvDueDate;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFrontText = itemView.findViewById(R.id.tvFrontText);
            tvBackText = itemView.findViewById(R.id.tvBackText);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            tvDueDate = itemView.findViewById(R.id.tvDueDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener((View v) -> {
                int pos = (int) itemView.getTag();
                Repository.getInstance(context.getApplicationContext()).deleteCard(cards.get(pos));
                cards.remove(pos);
                notifyDataSetChanged();
            });
        }
    }

    CardsListAdapter(Context context) {
        this.context = context;
        cards = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.card_list_item, parent, false);
        itemView.setTag(position);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Card card = cards.get(position);

        String frontText = context.getString(R.string.front) + ": " + card.getFrontText();
        String backText = context.getString(R.string.back) + ": " + card.getBackText();
        String levelText = context.getString(R.string.level) + ": " + card.getLevel();
        String dueDateText = context.getString(R.string.due_date) + ": " + card.getDueDateInString();

        viewHolder.tvFrontText.setText(frontText);
        viewHolder.tvBackText.setText(backText);
        viewHolder.tvLevel.setText(levelText);
        viewHolder.tvDueDate.setText(dueDateText);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards(List<Card> c) {
        cards.clear();
        cards.addAll(c);
        notifyDataSetChanged();
    }
}
