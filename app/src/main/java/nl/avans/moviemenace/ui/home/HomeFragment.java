package nl.avans.moviemenace.ui.home;

import android.content.Intent;
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
import java.util.Locale;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.logic.MovieEntityManager;
import nl.avans.moviemenace.logic.MovieManager;
import nl.avans.moviemenace.ui.FilmDetailActivity;
import nl.avans.moviemenace.ui.MainActivity;
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class HomeFragment extends Fragment {
    private AccountViewModel accountViewModel;
    private Account userAccount;
    private HomeViewModel homeViewModel;
    private RecyclerView mPopularRv;
    private PopularFilmAdapter popularFilmAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private Movie movieInBanner;

    private TextView mDescription;
    private ImageView mHeaderImage;
    private TextView mTitle;

    private ProgressBar mLoadingPopularPb;
    private ProgressBar mLoadingHeaderPb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mLoadingPopularPb = root.findViewById(R.id.pb_home_popular);
        mLoadingHeaderPb = root.findViewById(R.id.pb_home_header);

        homeViewModel.getMovies(mLoadingPopularPb).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieList =  movies;
                popularFilmAdapter.setMovieList(movies);
                mLoadingPopularPb.setVisibility(View.INVISIBLE);
            }
        });

        new MajorFilmDatabaseTask().execute();

        System.out.println(Locale.getDefault().toString());

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
        mHeaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FilmDetailActivity.class).putExtra(FilmDetailActivity.MOVIE_KEY, movieInBanner));
            }
        });
        mTitle = view.findViewById(R.id.tv_title_trending);
    }


    public class MajorFilmDatabaseTask extends AsyncTask<Void, Void, Movie>{

        @Override
        protected Movie doInBackground(Void... voids) {
            mLoadingHeaderPb.setVisibility(View.VISIBLE);
            MovieManager mm = new MovieManager(MainActivity.factory);
            MovieEntityManager mem = MainActivity.mem;
            return mm.getRandomMovie(mem);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            mLoadingHeaderPb.setVisibility(View.INVISIBLE);
            if(!(movie == null)){
                movieInBanner = movie;
                if(Locale.getDefault().equals(Locale.US)){
                    mDescription.setText(movie.getOverview());
                    Picasso.get().load(MainActivity.BASE_URL + movie.getBanner()).into(mHeaderImage);
                    mTitle.setText(movie.getTitle());
                } else {
                    mDescription.setText(movie.getTranslations().get("Dutch").getDescription());
                    Picasso.get().load(MainActivity.BASE_URL + movie.getTranslations().get("Dutch").getUrl()).into(mHeaderImage);
                    mTitle.setText(movie.getTranslations().get("Dutch").getTitle());
                }

            }

        }
    }

}