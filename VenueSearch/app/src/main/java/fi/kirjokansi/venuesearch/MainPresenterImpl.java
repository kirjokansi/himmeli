package fi.kirjokansi.venuesearch;

import android.location.Location;
import android.util.Log;

import java.util.List;

/**
 * Created by sampo on 13.12.2014.
 */
public class MainPresenterImpl implements MainPresenter, OnFinishedListener{

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private static MainView mainView;
    private static FoursquareApi foursquareApi;

    protected static MainPresenterImpl mainPresenter;

    protected MainPresenterImpl() {}

    public static MainPresenterImpl getInstance(MainView view) {
        if (mainPresenter == null) {
            mainPresenter = new MainPresenterImpl();
        }
        mainView = view;
        foursquareApi = new FoursquareApiImpl();
        return mainPresenter;
    }

    @Override
    public void onSearchStringUpdated(String searchString, Location location) {
        Log.d(TAG, "onSearchStringUpdated()");
        foursquareApi.doVenueSearch(this, searchString, location);
    }

    @Override
    public void OnVenuesSearchFinished(List<String> venues) {
        Log.d(TAG, "OnVenuesSearchFinished()");
        mainView.onUpdateVenueList(venues);
    }
}
