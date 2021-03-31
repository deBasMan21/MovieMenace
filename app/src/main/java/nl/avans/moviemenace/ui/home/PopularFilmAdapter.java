package nl.avans.moviemenace.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.ui.FilmDetailActivity;
import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.MainActivity;

public class PopularFilmAdapter extends RecyclerView.Adapter<PopularFilmAdapter.PopularFilmViewHolder> {
    public List<Movie> movieList;

    public PopularFilmAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public static class PopularFilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        
        public ImageView mPosterIv;
        public TextView mTitleTv;

        public PopularFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mPosterIv = itemView.findViewById(R.id.iv_film_viewholder_poster);
            mTitleTv = itemView.findViewById(R.id.tv_film_viewholder_title);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            Intent intent = new Intent(context, FilmDetailActivity.class);
            context.startActivity(new Intent(context, FilmDetailActivity.class));
        }
    }

    @NonNull
    @Override
    public PopularFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_film, parent, false);

        return new PopularFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFilmViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.mTitleTv.setText(movie.getTitle());
        Picasso.get().load(MainActivity.BASE_URL + movie.getUrl()).resize(80, 80).into(holder.mPosterIv);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public interface onClickHandler{
        void onItemClicked();
    }
}
