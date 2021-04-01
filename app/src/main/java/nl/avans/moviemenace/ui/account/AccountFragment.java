package nl.avans.moviemenace.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import nl.avans.moviemenace.ui.EditAccountActivity;
import nl.avans.moviemenace.R;

public class AccountFragment extends Fragment {
    private AccountViewModel accountViewModel;

    private TextView mNameTv;
    private TextView mEmailTv;
    private TextView mBirthTv;
    private TextView mAddressTv;

    private Button mEditBn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // navigate to login fragment if no user is logged in
        NavController navController = Navigation.findNavController(view);
        if (accountViewModel.getAccount() == null) {
            navController.navigate(R.id.nav_login);
        }

        mEditBn = view.findViewById(R.id.bn_account_edit);
        mEditBn.setOnClickListener((View v) -> {
            startActivity(new Intent(this.getContext(), EditAccountActivity.class));
        });

    }
}