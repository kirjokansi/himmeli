package fi.kirjokansi.venuesearch;

import android.location.Location;

/**
 * Created by sampo on 13.12.2014.
 */
public interface MainPresenter {

    public void onSearchStringUpdated(String searchString, Location location);
}
