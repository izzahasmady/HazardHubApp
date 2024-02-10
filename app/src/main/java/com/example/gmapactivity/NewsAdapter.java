package com.example.gmapactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<ListNews> newsList;

    public NewsAdapter(List<ListNews> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListNews news = newsList.get(position);

        // Set the title in the TextView
        holder.titleTextView.setText(news.getTitle());

        // Set the source in the TextView (optional)
        holder.sourceTextView.setText(news.getSource());

        // Set the text color for sourceTextView to black
        holder.sourceTextView.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.black));

        // Set the description in the TextView
        holder.descTextView.setText(news.getDescription());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView sourceTextView;
        TextView descTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            sourceTextView = itemView.findViewById(R.id.textViewSource);
            descTextView = itemView.findViewById(R.id.textViewDesc);
        }
    }
}
