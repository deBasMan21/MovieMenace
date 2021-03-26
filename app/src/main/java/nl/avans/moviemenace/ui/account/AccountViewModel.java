package nl.avans.moviemenace.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AccountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}