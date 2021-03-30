package nl.avans.moviemenace.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import nl.avans.moviemenace.domain.Account;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private MutableLiveData<String> account;

    public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Account fragment");

        account = new MutableLiveData<>();
        account.setValue("test");
    }

    public void setAccount(String account) {
        this.account.setValue(account);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getAccount() {
        return account;
    }
}