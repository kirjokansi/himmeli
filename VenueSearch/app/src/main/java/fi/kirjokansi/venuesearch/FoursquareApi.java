package fi.kirjokansi.venuesearch;

import android.location.Location;

/**
 * Created by sampo on 13.12.2014.
 */
public interface FoursquareApi {

    public void doVenueSearch(OnFinishedListener listener, String searchString, Location location);
}
