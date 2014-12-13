package fi.kirjokansi.venuesearch;

import android.util.Log;

import java.util.List;

/**
 * Created by sampo on 13.12.2014.
 */
public class MainPresenterImpl implements MainPresenter, OnFinishedListener{

    private MainView mainView;
    private FoursquareApi foursquareApi;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        foursquareApi = new FoursquareApiImpl();
    }

    @Override
    public void onSearchStringUpdated(String searchString) {
        foursquareApi.doVenueSearch(this, searchString);
    }

    @Override
    public void OnVenuesSearchFinished(List<String> venues) {
        Log.d("VenueSearch", "MainPresenterImpl:OnVenuesSearchFinished");
        mainView.updateVenueList(venues);
    }
}
