package nl.avans.moviemenace.ui.lists;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.ui.ListDetailActivity;
import nl.avans.moviemenace.R;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsViewHolder> implements Filterable {

    private Account account;
    private List<MovieList> movieLists;
    private List<MovieList> movieListsFull;

    public ListsAdapter(List<MovieList> movieLists, Account account) {
        this.movieLists = movieLists;
        this.account = account;
        this.movieListsFull = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private Filter myListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MovieList> filteredList = new ArrayList<>();

            String filterPattern = constraint.toString().toLowerCase().trim();

            if (constraint.length() == 0) {
                filteredList.addAll(movieListsFull);
            } else {
                for (MovieList movieList : movieListsFull) {
                    if (movieList.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(movieList);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movieLists.clear();
            movieLists.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ListsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;

        private TextView mTitleTv;

        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mTitleTv = itemView.findViewById(R.id.tv_list_viewholder_title);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ListDetailActivity.class);
            intent.putExtra(ListsFragment.LIST_KEY, movieLists.get(getAdapterPosition()));
            intent.putExtra(Account.ACCOUNT_KEY, account);
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_list, parent, false);
        return new ListsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsViewHolder holder, int position) {
        MovieList movieList = movieLists.get(position);
        holder.mTitleTv.setText(movieList.getName());
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }


    public void setMovieList(List<MovieList> movieLists) {
        this.movieLists = movieLists;
        notifyDataSetChanged();
    }

    public void setMovieListsFull(List<MovieList> movieListsFull) {
        this.movieListsFull = movieListsFull;
    }
}
