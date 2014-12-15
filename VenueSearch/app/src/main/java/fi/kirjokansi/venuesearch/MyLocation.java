package fi.kirjokansi.venuesearch;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by sampo on 15.12.2014.
 */
public class MyLocation {

    protected static MyLocation myLocation;

    protected MyLocation() {}

    public static MyLocation getInstance() {
        if (myLocation == null) {
            myLocation = new MyLocation();
        }
        return myLocation;
    }

    public Location getLastLocation(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = lm.getBestProvider(new Criteria(), false);
        return lm.getLastKnownLocation(provider);
    }
}
