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

import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.ui.EditAccountActivity;
import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.MainActivity;

public class AccountFragment extends Fragment {
    private AccountViewModel accountViewModel;

    private TextView mNameTv;
    private TextView mEmailTv;
    private TextView mBirthTv;
    private TextView mAddressTv;
    private Account account;

    private Button mEditBn;
    private Button mLogoutBn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        account = MainActivity.account;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // navigate to login fragment if no user is logged in
        NavController navController = Navigation.findNavController(view);
        if (account == null) {
            navController.navigate(R.id.nav_login);
            return;
        }

        mNameTv = view.findViewById(R.id.tv_account_name);
        mEmailTv = view.findViewById(R.id.tv_account_email);
        mBirthTv = view.findViewById(R.id.tv_account_birth);
        mAddressTv = view.findViewById(R.id.tv_account_address);
        mNameTv.append(": " + account.getName());
        mEmailTv.append(": " + account.getEmail());
        mBirthTv.append(": " + account.getDateOfBirth());
        mAddressTv.append(": " + account.getAddress());

        mLogoutBn = view.findViewById(R.id.bn_logout);
        mLogoutBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.account = null;
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Account.ACCOUNT_KEY, MainActivity.account);
                startActivity(intent);
            }
        });

        mEditBn = view.findViewById(R.id.bn_account_edit);
        mEditBn.setOnClickListener((View v) -> {
            startActivity(new Intent(this.getContext(), EditAccountActivity.class));
        });

    }
}