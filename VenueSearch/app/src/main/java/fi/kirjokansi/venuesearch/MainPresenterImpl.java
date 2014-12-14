package fi.kirjokansi.venuesearch;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
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
    public void onSearchStringUpdated(String searchString, Location location) {
        Log.d("VenueSearch", "MainPresenterImpl:onSearchStringUpdated");
        foursquareApi.doVenueSearch(this, searchString, location);
    }

    @Override
    public void OnVenuesSearchFinished(ArrayList venues) {
        Log.d("VenueSearch", "MainPresenterImpl:OnVenuesSearchFinished");
        mainView.onUpdateVenueList(venues);
    }
}
