package nl.avans.moviemenace.ui;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Viewing;
import nl.avans.moviemenace.logic.ViewingManager;

public class ViewingViewModel extends ViewModel {
    private MutableLiveData<List<Viewing>> viewingList;
    private DAOFactory factory= new SQLDAOFactory();
    private ViewingManager viewingManager = new ViewingManager(factory);
    private int movieId;

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public MutableLiveData<List<Viewing>> getViewingList() {
        if (viewingList == null) {
            viewingList = new MutableLiveData<List<Viewing>>();
            new ViewingTask().execute();
        }
        return viewingList;
    }

    public class ViewingTask extends AsyncTask<Void, Void, List<Viewing>> {

        @Override
        protected List<Viewing> doInBackground(Void... voids) {
            List<Viewing> viewingList = viewingManager.getUpcomingViewingsForFilm(movieId);
            return viewingList;
        }

        @Override
        protected void onPostExecute(List<Viewing> viewings) {
            super.onPostExecute(viewings);
            viewingList.setValue(viewings);
        }

    }
}
