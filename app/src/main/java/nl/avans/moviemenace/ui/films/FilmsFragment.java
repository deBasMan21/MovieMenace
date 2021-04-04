package nl.avans.moviemenace.ui.films;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.OnConflictStrategy;

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
    private ProgressBar mLoadingPb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        filmsViewModel =
                new ViewModelProvider(this).get(FilmsViewModel.class);
        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        View root = inflater.inflate(R.layout.fragment_films, container, false);

        mLoadingPb = root.findViewById(R.id.pb_films);

        filmsViewModel.getMovies(mLoadingPb).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {

            @Override
            public void onChanged(List<Movie> movies) {
                movieList = movies;
                filmsAdapter.setMovieList(movies);
                if (filmsAdapter.getMovieListFull().size() == 0) {
                    filmsAdapter.setMovieListFull(movies);
                }
                mLoadingPb.setVisibility(View.INVISIBLE);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFilmsRv = view.findViewById(R.id.rv_films);
        mFilmsRv.setAdapter(filmsAdapter = new FilmsAdapter(movieList, accountViewModel.getAccount()));
        mFilmsRv.setLayoutManager(new GridLayoutManager(this.getContext(), calculateColumns(118), GridLayoutManager.VERTICAL, false));
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem filter = menu.findItem(R.id.filter);

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
        filter.setVisible(true);
        filter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DatePickerDialog dp = new DatePickerDialog(getContext());
                dp.show();

                dp.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    }
                });
                return true;
            }
        });

    }

    private int calculateColumns(float columnWidthDp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }
}