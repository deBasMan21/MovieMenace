package nl.avans.moviemenace.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.logic.MovieListManager;
import nl.avans.moviemenace.ui.lists.ListsFragment;

public class ListToFilmsAdapter extends RecyclerView.Adapter<ListToFilmsAdapter.ListToFilmsViewHolder> implements Filterable {

    private List<Movie> movies;
    private List<Movie> moviesFull;
    private MovieList movieList;
    private Account account;

    public ListToFilmsAdapter(List<Movie> movies, MovieList movieList, Account account) {
        this.movies = movies;
        this.movieList = movieList;
        this.account = account;
        this.moviesFull = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        return ListToFilmFilter;
    }

    private Filter ListToFilmFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Movie> filteredList = new ArrayList<>();

            String filterPattern = constraint.toString().toLowerCase().trim();

            if (constraint.length() == 0) {
                filteredList.addAll(moviesFull);
            } else {
                for (Movie movie : moviesFull) {
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
            movies.clear();
            movies.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ListToFilmsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Account.ACCOUNT_KEY, account);
            Movie movie = movies.get(getAdapterPosition());
            movieList.addMovie(movie);
            Integer[] IDs = {movieList.getId(), movie.getId()};
            new DatabaseTask().execute(IDs);
//            movies.remove(movie);
            context.startActivity(intent);
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
        Movie movie = movies.get(position);
        holder.mTitleTv.setText(movie.getTitle());
        Picasso.get().load(MainActivity.BASE_URL + movie.getUrl()).into(holder.mPosterIv);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMoviesFull(List<Movie> movies) {
        this.moviesFull = movies;
    }

    public class DatabaseTask extends AsyncTask<Integer[], Void, Void> {

        @Override
        protected Void doInBackground(Integer[]... integers) {
            DAOFactory daoFactory = new SQLDAOFactory();
            MovieListManager movieListManager = new MovieListManager(daoFactory);
            DatabaseConnection db = new DatabaseConnection();
            if (!db.connectionIsOpen()) {
                db.openConnection();
            }
            Integer[] resourceIDs = integers[0];
            int listID = resourceIDs[0].intValue();
            int movieID = resourceIDs[1].intValue();
            movieListManager.addMovieToList(listID, movieID);
            db.closeConnection();
            return null;
        }
    }
}
