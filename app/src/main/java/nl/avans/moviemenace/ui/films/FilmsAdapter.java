package nl.avans.moviemenace.ui.films;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;

public class FilmsAdapter extends RecyclerView.Adapter {
    public static class FilmViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPosterIv;
        private TextView mTitleTv;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            mPosterIv = itemView.findViewById(R.id.iv_film_viewholder_poster);
            mTitleTv = itemView.findViewById(R.id.tv_film_viewholder_title);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_viewholder, parent, false);

        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

}
