package nl.avans.moviemenace.ui.films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class FilmsFragment extends Fragment {

    private FilmsViewModel filmsViewModel;
    private AccountViewModel accountViewModel;
    private RecyclerView mFilmsRv;
    private FilmsAdapter filmsAdapter;
    private List<Movie> movieList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        filmsViewModel =
                new ViewModelProvider(this).get(FilmsViewModel.class);
        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        filmsViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {

            @Override
            public void onChanged(List<Movie> movies) {
                movieList = movies;
                filmsAdapter.setMovieList(movies);
                if (filmsAdapter.getMovieListFull().size() == 0) {
                    filmsAdapter.setMovieListFull(movies);
                }
            }
        });

        View root = inflater.inflate(R.layout.fragment_films, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFilmsRv = view.findViewById(R.id.rv_films);
        mFilmsRv.setAdapter(filmsAdapter = new FilmsAdapter(movieList, accountViewModel.getAccount()));
        mFilmsRv.setLayoutManager(new GridLayoutManager(this.getContext(), 3, GridLayoutManager.VERTICAL, false));
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filmsAdapter.getFilter().filter(newText);
                return false;
            }
        });

        searchItem.setVisible(true);
    }
}