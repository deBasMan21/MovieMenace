package nl.avans.moviemenace.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.ui.MainActivity;
import nl.avans.moviemenace.ui.RegisterActivity;
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private AccountViewModel accountViewModel;

    private TextView mRegisterTv;

    private Button mLoginBn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        accountViewModel =
                new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        View root = inflater.inflate(R.layout.fragment_login, container, false);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoginBn = view.findViewById(R.id.bn_login_login);
        mLoginBn.setOnClickListener((View v) -> {
            // set dummy account to simulate logged in user
            accountViewModel.setAccount(new Account("email", "name", "pass", "address", "0000AA", "iban", LocalDate.of(2000, 1, 1)));
            Navigation.findNavController(view).navigate(R.id.nav_account);
        });

        mRegisterTv = view.findViewById(R.id.tv_login_register);
        mRegisterTv.setOnClickListener((View v) -> {
            startActivity(new Intent(getContext(), RegisterActivity.class));
        });

    }
}