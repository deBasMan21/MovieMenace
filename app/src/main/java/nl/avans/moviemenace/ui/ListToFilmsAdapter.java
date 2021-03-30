package nl.avans.moviemenace.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;

public class ListToFilmsAdapter extends RecyclerView.Adapter<ListToFilmsAdapter.ListToFilmsViewHolder> {
    public static class ListToFilmsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;

        private ImageView mPosterIv;
        private TextView mTitleTv;

        public ListToFilmsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mPosterIv = itemView.findViewById(R.id.iv_film_viewholder_poster);
            mTitleTv = itemView.findViewById(R.id.tv_film_viewholder_title);
        }

        @Override
        public void onClick(View v) {
            ((Activity) context).finish();
        }
    }

    @NonNull
    @Override
    public ListToFilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_film, parent, false);

        return new ListToFilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListToFilmsViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
