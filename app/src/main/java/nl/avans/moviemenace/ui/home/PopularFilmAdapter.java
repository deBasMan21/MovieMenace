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

import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.ui.FilmDetailActivity;
import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.MainActivity;

public class PopularFilmAdapter extends RecyclerView.Adapter<PopularFilmAdapter.PopularFilmViewHolder> {
    public Account account;
    public List<Movie> movieList;

    public PopularFilmAdapter(List<Movie> movieList, Account account) {
        this.movieList = movieList;
        this.account = account;
    }

    public class PopularFilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            Intent intent = new Intent(context, FilmDetailActivity.class);
            intent.putExtra(FilmDetailActivity.MOVIE_KEY, movieList.get(getAdapterPosition()));
            intent.putExtra("account_temp", account);
            context.startActivity(intent);
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
        Picasso.get().load(MainActivity.BASE_URL + movie.getUrl()).into(holder.mPosterIv);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }
}
