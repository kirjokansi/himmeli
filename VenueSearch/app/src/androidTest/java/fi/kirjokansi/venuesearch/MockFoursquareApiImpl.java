package fi.kirjokansi.venuesearch;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sampo on 15.12.2014.
 */
public class MockFoursquareApiImpl extends FoursquareApiImpl {

    private static final String TAG = MockMainPresenter.class.getSimpleName();
    private String response;
    private String date;
    private String apiUrl;

    public MockFoursquareApiImpl(String response, String date) {
        this.response = response;
        this.date = date;
    }

    public String GetApiUrl() {
        return apiUrl;
    }

    @Override
    protected BufferedInputStream getStream(String apiUrl) throws MalformedURLException,IOException {
        InputStream is = new ByteArrayInputStream(response.getBytes());
        this.apiUrl = apiUrl;
        return new BufferedInputStream(is);
    }

    @Override
    protected String getDate() {
        return date;
    }

}
