package nl.avans.moviemenace.ui.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.CreateListActivity;
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class ListsFragment extends Fragment {
    private ListsViewModel listsViewModel;
    private AccountViewModel accountViewModel;

    private RecyclerView mListsRv;

    private FloatingActionButton mAddFb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listsViewModel =
                new ViewModelProvider(requireActivity()).get(ListsViewModel.class);
        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        View root = inflater.inflate(R.layout.fragment_lists, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // navigate to login fragment if no user i slogged in
        NavController navController = Navigation.findNavController(view);
        if (accountViewModel.getAccount() == null) {
            navController.navigate(R.id.nav_login);
        }

        mAddFb = view.findViewById(R.id.fb_lists_add);
        mAddFb.setOnClickListener((View v) -> {
            startActivity(new Intent(getContext(), CreateListActivity.class));
        });

        mListsRv = view.findViewById(R.id.rv_lists);
        mListsRv.setAdapter(new ListsAdapter());
        mListsRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
    }
}
