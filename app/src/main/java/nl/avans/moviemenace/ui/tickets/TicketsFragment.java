package nl.avans.moviemenace.ui.tickets;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class TicketsFragment extends Fragment {
    private TicketsViewModel ticketsViewModel;
    private AccountViewModel accountViewModel;

    private RecyclerView mTicketsRv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ticketsViewModel =
                new ViewModelProvider(this).get(TicketsViewModel.class);
        accountViewModel =
                new ViewModelProvider((requireActivity())).get(AccountViewModel.class);

        View root = inflater.inflate(R.layout.fragment_tickets, container, false);

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

        mTicketsRv = view.findViewById(R.id.rv_tickets);
        mTicketsRv.setAdapter(new TicketsAdapter());
        mTicketsRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
    }
}
