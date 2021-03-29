package nl.avans.moviemenace.ui.films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;

public class FilmsFragment extends Fragment {

    private FilmsViewModel filmsViewModel;
    private RecyclerView mFilmsRv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        filmsViewModel =
                new ViewModelProvider(this).get(FilmsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_films, container, false);

        mFilmsRv = root.findViewById(R.id.rv_films);
        mFilmsRv.setAdapter(new FilmsAdapter());
        mFilmsRv.setLayoutManager(new GridLayoutManager(this.getContext(), 3, GridLayoutManager.VERTICAL, false));

        return root;
    }
}