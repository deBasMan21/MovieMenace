package nl.avans.moviemenace.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nl.avans.moviemenace.domain.Account;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private Account account;

    public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Account fragment");

        account = null;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public Account getAccount() {
        return account;
    }
}