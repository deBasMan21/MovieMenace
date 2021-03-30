package nl.avans.moviemenace.ui.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.CreateListActivity;

public class ListsFragment extends Fragment {
    private ListsViewModel listsViewModel;
    private RecyclerView mListsRv;

    private FloatingActionButton mAddFb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listsViewModel =
                new ViewModelProvider(this).get(ListsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lists, container, false);

        mAddFb = root.findViewById(R.id.fb_lists_add);
        mAddFb.setOnClickListener((View v) -> {
            startActivity(new Intent(getContext(), CreateListActivity.class));
        });

        mListsRv = root.findViewById(R.id.rv_lists);
        mListsRv.setAdapter(new ListsAdapter());
        mListsRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        return root;
    }
}
