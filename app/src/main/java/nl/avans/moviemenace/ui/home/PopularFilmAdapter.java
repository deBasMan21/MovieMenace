package nl.avans.moviemenace.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.FilmDetailActivity;
import nl.avans.moviemenace.R;

public class PopularFilmAdapter extends RecyclerView.Adapter<PopularFilmAdapter.PopularFilmViewHolder> {
    public static class PopularFilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        
        private ImageView mPosterIv;
        private TextView mTitleTv;

        public PopularFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mPosterIv = itemView.findViewById(R.id.iv_film_viewholder_poster);
            mTitleTv = itemView.findViewById(R.id.tv_film_viewholder_title);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, FilmDetailActivity.class));
        }
    }

    @NonNull
    @Override
    public PopularFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_viewholder, parent, false);

        return new PopularFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFilmViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
