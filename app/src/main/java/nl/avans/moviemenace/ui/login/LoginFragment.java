package nl.avans.moviemenace.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.logic.AccountManager;
import nl.avans.moviemenace.ui.MainActivity;
import nl.avans.moviemenace.ui.RegisterActivity;
import nl.avans.moviemenace.ui.account.AccountViewModel;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private AccountViewModel accountViewModel;

    private EditText mEmailField;
    private EditText mPasswordField;

    private TextView mRegisterTv;

    private Button mLoginBn;

    private Account account = null;


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
        mEmailField = view.findViewById(R.id.et_login_email);
        mPasswordField = view.findViewById(R.id.et_login_pass);
        mLoginBn.setOnClickListener((View v) -> {
            // set dummy account to simulate logged in user
            new loginTask().execute();
        });

        mRegisterTv = view.findViewById(R.id.tv_login_register);
        mRegisterTv.setOnClickListener((View v) -> {
            startActivity(new Intent(getContext(), RegisterActivity.class));
        });

    }

    public class loginTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            AccountManager accountManager = new AccountManager(MainActivity.factory);
            account = accountManager.loginWithAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(account != null){
                accountViewModel.setAccount(account);
//                Navigation.findNavController(view).navigate(R.id.nav_account);
                startActivity(new Intent(getContext(), MainActivity.class));
            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Login failed")
                        .setMessage("Login info is invalid")
                        .setNegativeButton("Back", null)
                        .show();
            }
        }
    }
}