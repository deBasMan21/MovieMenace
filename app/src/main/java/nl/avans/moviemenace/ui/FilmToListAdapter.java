package nl.avans.moviemenace.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.MovieList;
import nl.avans.moviemenace.logic.MovieListManager;
import nl.avans.moviemenace.ui.lists.ListsFragment;

public class FilmToListAdapter extends RecyclerView.Adapter<FilmToListAdapter.FilmToListViewHolder> {

    private List<MovieList> movieLists;
    private Movie movie;

    public FilmToListAdapter(List<MovieList> movieLists, Movie movie) {
        this.movieLists = movieLists;
        this.movie = movie;
    }

    public class FilmToListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;

        private TextView mTitleTv;
        private Button mDeleteButton;
        private LinearLayout mLinearLayout;

        public FilmToListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mTitleTv = itemView.findViewById(R.id.tv_list_viewholder_title);
            mDeleteButton = itemView.findViewById(R.id.bn_list_viewholder_delete);
            mLinearLayout = itemView.findViewById(R.id.ll_custom_list);
            LinearLayout mChildLinearLayout = mLinearLayout.findViewById(R.id.ll_list_button_bar);
            mLinearLayout.removeView(mChildLinearLayout);
        }

        @Override
        public void onClick(View v) {
            MovieList movieList = movieLists.get(getAdapterPosition());
            movieLists.remove(movieList);
            Integer[] IDS = {movieList.getId(), movie.getId()};
            new DatabaseTask().execute(IDS);
            Intent intent = new Intent(context, ListDetailActivity.class);
            intent.putExtra(ListsFragment.LIST_KEY, movieList);
            context.startActivity(intent);
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
        MovieList movieList = movieLists.get(position);
        holder.mTitleTv.setText(movieList.getName());
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
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
