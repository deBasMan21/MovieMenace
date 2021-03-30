package nl.avans.moviemenace.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.account.AccountFragment;
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private AccountViewModel accountViewModel;

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
            accountViewModel.setAccount("logged in");
            Navigation.findNavController(view).navigate(R.id.nav_account);
        });
    }
}