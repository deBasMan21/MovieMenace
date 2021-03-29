package nl.avans.moviemenace.ui.films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;

public class FilmsFragment extends Fragment {

    private FilmsViewModel filmsViewModel;
    private RecyclerView mFilmsRecyclerview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        filmsViewModel =
                new ViewModelProvider(this).get(FilmsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_films, container, false);

        mFilmsRecyclerview = root.findViewById(R.id.rv_films_recyclerview);
        mFilmsRecyclerview.setAdapter(new FilmsAdapter());
        mFilmsRecyclerview.setLayoutManager(new GridLayoutManager(this.getContext(), 3, GridLayoutManager.VERTICAL, false));

        return root;
    }
}