package fi.kirjokansi.venuesearch;

import android.content.Context;
import android.location.Location;
import android.util.Log;

/**
 * Created by sampo on 15.12.2014.
 */
public class MockMyLocation extends MyLocation{

    private static final String TAG = MockMyLocation.class.getSimpleName();
    private Location location;

    public MockMyLocation(double latitude, double longitude) {
        MyLocation.myLocation = this;
        location = new Location("mock");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
    }

    @Override
    public Location getLastLocation(Context context) {
        Log.d(TAG, "getLastLocation() location: "+location);
        return location;
    }
}
