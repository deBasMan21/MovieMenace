package nl.avans.moviemenace.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;

public class FilmToListAdapter extends RecyclerView.Adapter<FilmToListAdapter.FilmToListViewHolder> {
    public static class FilmToListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;

        private TextView mTitleTv;

        public FilmToListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mTitleTv = itemView.findViewById(R.id.tv_list_viewholder_title);
        }

        @Override
        public void onClick(View v) {
            ((Activity) context).finish();
        }
    }

    @NonNull
    @Override
    public FilmToListAdapter.FilmToListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_list, parent, false);
        return new FilmToListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmToListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
