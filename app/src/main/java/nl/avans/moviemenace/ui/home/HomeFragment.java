package nl.avans.moviemenace.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.logic.MovieEntityManager;
import nl.avans.moviemenace.logic.MovieManager;
import nl.avans.moviemenace.ui.MainActivity;
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class HomeFragment extends Fragment {
    private AccountViewModel accountViewModel;
    private Account userAccount;
    private HomeViewModel homeViewModel;
    private RecyclerView mPopularRv;
    private PopularFilmAdapter popularFilmAdapter;
    private List<Movie> movieList = new ArrayList<>();

    private TextView mDescription;
    private ImageView mHeaderImage;

    private ProgressBar mLoadingPb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mLoadingPb = root.findViewById(R.id.pb_home_popular);

        homeViewModel.getMovies(mLoadingPb).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieList =  movies;
                popularFilmAdapter.setMovieList(movies);
                mLoadingPb.setVisibility(View.INVISIBLE);
            }
        });

        new MajorFilmDatabaseTask().execute();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPopularRv = view.findViewById(R.id.rv_home_popular);
        mPopularRv.setAdapter(popularFilmAdapter = new PopularFilmAdapter(movieList, accountViewModel.getAccount()));
        mPopularRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));

        mDescription = view.findViewById(R.id.tv_home_desc);
        mHeaderImage = view.findViewById(R.id.iv_home_banner);
    }


    public class MajorFilmDatabaseTask extends AsyncTask<Void, Void, Movie>{

        @Override
        protected Movie doInBackground(Void... voids) {
            MovieManager mm = new MovieManager(MainActivity.factory);
            MovieEntityManager mem = MainActivity.mem;
            return mm.getRandomMovie(mem);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            mDescription.setText(movie.getOverview());
            Picasso.get().load(MainActivity.BASE_URL + movie.getUrl()).into(mHeaderImage);
        }
    }

}