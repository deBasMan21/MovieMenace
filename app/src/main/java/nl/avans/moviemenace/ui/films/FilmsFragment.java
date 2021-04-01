package nl.avans.moviemenace.ui.films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Movie;

public class FilmsFragment extends Fragment {

    private FilmsViewModel filmsViewModel;
    private RecyclerView mFilmsRv;
    private FilmsAdapter filmsAdapter;
    private List<Movie> movieList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        filmsViewModel =
                new ViewModelProvider(this).get(FilmsViewModel.class);

        filmsViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieList =  movies;
                filmsAdapter.setMovieList(movies);
            }
        });


        View root = inflater.inflate(R.layout.fragment_films, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFilmsRv = view.findViewById(R.id.rv_films);
        mFilmsRv.setAdapter(filmsAdapter = new FilmsAdapter(movieList));
        mFilmsRv.setLayoutManager(new GridLayoutManager(this.getContext(), 3, GridLayoutManager.VERTICAL, false));
    }
}