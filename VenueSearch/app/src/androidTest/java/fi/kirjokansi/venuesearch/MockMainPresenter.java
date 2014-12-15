package fi.kirjokansi.venuesearch;

import android.location.Location;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by sampo on 15.12.2014.
 */
public class MockMainPresenter extends MainPresenterImpl {

    private static final String TAG = MockMainPresenter.class.getSimpleName();
    private List<String> venues;
    private List<String> response;
    private CountDownLatch signal;

    public MockMainPresenter(List<String> venues) {
        MainPresenterImpl.mainPresenter = this;
        this.venues = venues;
    }

    public MockMainPresenter(CountDownLatch signal) {
        MainPresenterImpl.mainPresenter = this;
        this.signal = signal;
    }

    public List<String> getResponse() {
        return response;
    }

    @Override
    public void onSearchStringUpdated(String searchString, Location location) {
        Log.d(TAG, "onSearchStringUpdated() searchString: "+searchString+", location: "+location);
        super.OnVenuesSearchFinished(venues);
    }

    @Override
    public void OnVenuesSearchFinished(List<String> venues) {
        Log.d(TAG, "OnVenuesSearchFinished() venues: "+venues);
        response = venues;
        signal.countDown();
    }
}
