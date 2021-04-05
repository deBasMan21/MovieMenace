package nl.avans.moviemenace.ui;

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
import java.util.Collection;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;

public class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.ListFilmsViewHolder> implements Filterable {

    private List<Movie> movies;
    private List<Movie> moviesFull;
    private Account account;

    public ListFilmAdapter(List<Movie> movies, Account account) {
        this.movies = movies;
        this.moviesFull = movies;
        this.account = account;
    }

    @Override
    public Filter getFilter() {
        return ListDetailFilter;
    }

    private Filter ListDetailFilter = new Filter() {
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

    public class ListFilmsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;

        private ImageView mPosterIv;
        private TextView mTitleTv;
        private TextView mDescTv;

        public ListFilmsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mPosterIv = itemView.findViewById(R.id.iv_list_film_viewholder_poster);
            mTitleTv = itemView.findViewById(R.id.tv_list_film_viewholder_title);
            mDescTv = itemView.findViewById(R.id.tv__list_film_viewholder_desc);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, FilmDetailActivity.class);
            intent.putExtra(FilmDetailActivity.MOVIE_KEY, movies.get(getAdapterPosition()));
            intent.putExtra(Account.ACCOUNT_KEY, account);
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ListFilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_list_film, parent, false);

        return new ListFilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFilmsViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.mTitleTv.setText(movie.getTitle());
        holder.mDescTv.setText(movie.getOverview());
        Picasso.get().load(MainActivity.BASE_URL + movie.getUrl()).into(holder.mPosterIv);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public List<Movie> getMoviesFull() {
        return moviesFull;
    }
}
