package nl.avans.moviemenace.ui.lists;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;

public class ListsFragment extends Fragment {
    private ListsViewModel listsViewModel;
    private RecyclerView mListsRv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listsViewModel =
                new ViewModelProvider(this).get(ListsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lists, container, false);

        mListsRv = root.findViewById(R.id.rv_lists);
        mListsRv.setAdapter(new ListsAdapter());
        mListsRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        return root;
    }
}
