package nl.avans.moviemenace.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mPopularRv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mPopularRv = root.findViewById(R.id.rv_home_popular);
        mPopularRv.setAdapter(new PopularFilmAdapter());
        mPopularRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));

        return root;
    }
}