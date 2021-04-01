package nl.avans.moviemenace.ui.films;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.ui.FilmDetailActivity;
import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.MainActivity;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.FilmViewHolder> implements Filterable {

    private List<Movie> movieList;
    private Account account;
    private List<Movie> movieListFull;

    public FilmsAdapter(List<Movie> movieList, Account account) {
        this.movieList = movieList;
        this.account = account;
        this.movieListFull = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        return filmsFilter;
    }

    private Filter filmsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            String filterPattern = constraint.toString().toLowerCase().trim();

            if (constraint.length() == 0) {
                filteredList.addAll(movieListFull);
            } else {
                for (Movie movie : movieListFull) {
                    if (movie.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(movie);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movieList.clear();
            movieList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;

        public ImageView mPosterIv;
        public TextView mTitleTv;

        public FilmViewHolder(@NonNull View itemView) {
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
            intent.putExtra(Account.ACCOUNT_KEY, account);
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_film, parent, false);

        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
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

    public List<Movie> getMovieListFull() {
        return movieListFull;
    }

    public void setMovieListFull(List<Movie> movieList) {
        this.movieListFull.addAll(movieList);
    }
}
