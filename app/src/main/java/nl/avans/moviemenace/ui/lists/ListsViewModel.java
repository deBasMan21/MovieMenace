package nl.avans.moviemenace.ui.lists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Mijn lijsten fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
