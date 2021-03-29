package nl.avans.moviemenace.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import nl.avans.moviemenace.ui.EditAccountActivity;
import nl.avans.moviemenace.R;

public class AccountFragment extends Fragment {
    private AccountViewModel slideshowViewModel;

    private TextView mNameTv;
    private TextView mEmailTv;
    private TextView mBirthTv;
    private TextView mAddressTv;

    private Button mEditBn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        mEditBn = root.findViewById(R.id.bn_account_edit);

        mEditBn.setOnClickListener((View v) -> {
            startActivity(new Intent(this.getContext(), EditAccountActivity.class));
        });

        return root;
    }
}