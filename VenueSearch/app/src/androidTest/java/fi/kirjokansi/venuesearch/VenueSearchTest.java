package fi.kirjokansi.venuesearch;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class VenueSearchTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private static final String TAG = VenueSearchTest.class.getSimpleName();
    private MainActivity activity;
    private EditText editText;
    private ListView listView;

    private MockMyLocation mockMyLocation;
    private MockMainPresenter mockMainPresenter;
    private MockFoursquareApiImpl mockApi;

    private void initialize() {
        activity = getActivity();
        assertNotNull("Activity is null", activity);
        editText = (EditText) activity.findViewById(R.id.editText);
        assertNotNull("editText is null", editText);
        listView = (ListView) activity.findViewById(R.id.listView);
        assertNotNull("listView is null", listView);
        getInstrumentation().waitForIdleSync();
    }

    public VenueSearchTest() {
        super(MainActivity.class);
        Log.d(TAG, "ActivityTest()");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Log.d(TAG, "setUp()");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Log.d(TAG, "tearDown()");
    }

    public void test01_searchVenue() {
        Log.d(TAG, "test01_searchVenue()");

        mockMyLocation = new MockMyLocation(123.4, 432.1);
        mockMainPresenter = new MockMainPresenter(TestData.venueList);

        initialize();

        String expectedEditText = "r";

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(expectedEditText);
        getInstrumentation().waitForIdleSync();

        Log.d(TAG, "test01_searchVenue() editText: "+editText.getText());
        Log.d(TAG, "test01_searchVenue() listView count: "+listView.getAdapter().getCount());

        assertEquals(expectedEditText, editText.getText().toString());
        assertEquals(TestData.venueCount, listView.getAdapter().getCount());

        for(int i = 0; i < TestData.venueCount; i++) {
            assertEquals(TestData.venueList.get(i), (String)listView.getAdapter().getItem(i));
        }
    }

    public void test02callApiUrl() throws InterruptedException,JSONException{
        Log.d(TAG, "test02_callApiUrl()");

        mockMyLocation = new MockMyLocation(123.4, 432.1);
        CountDownLatch signal = new CountDownLatch(1);
        mockMainPresenter = new MockMainPresenter(signal);
        // Just to prevent the actual http connection
        mockApi = new MockFoursquareApiImpl(TestData.jsonOk, TestData.date);

        final String expectedSearchTerm = "r";

        mockApi.doVenueSearch(mockMainPresenter, expectedSearchTerm, mockMyLocation.getLastLocation(getActivity()));
        signal.await(10, TimeUnit.SECONDS);

        List<String> response = mockMainPresenter.getResponse();
        Log.d(TAG, "test02_callApiUrl() response: "+response);

        assertEquals(TestData.apiUrl, mockApi.GetApiUrl());
        assertEquals(TestData.venueList, mockMainPresenter.getResponse());

    }

}