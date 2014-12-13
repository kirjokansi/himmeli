package fi.kirjokansi.venuesearch;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by sampo on 13.12.2014.
 */
public class FoursquareApiImpl implements FoursquareApi {

    private final String CLIENT_ID = "LP3WEJ03DJIKCHCG5XVQUC4U2RJOWZIANDR2EUBAT3LWQGLE";
    private final String CLIENT_SECRET = "0M2U3RKWALTMMUU3BV0O3XTAHXVVZH4BIZYI4XURPL30OFPI";

    private final String OP_VENUE_LIST = "OP_VL";

    private final String venueListURL = "https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll=40.7463956,-73.9852992";

    private class CallAPI extends AsyncTask {

        private String op;
        private OnFinishedListener listener;

        @Override
        protected Object doInBackground(Object[] params) {

            op = (String) params[0];
            String search = (String) params[1];
            listener = (OnFinishedListener) params[2];

            Log.d("VenueSearch", op + "->" + search);

            List<String> result = new ArrayList();

            if (op.equals(OP_VENUE_LIST)) {
                String json = call(OP_VENUE_LIST, venueListURL);
                result = parse(json);
                Log.d("VenueSearch", json);
                Log.d("VenueSearch", result.toString());
            }

            return (Object) result;
        }

        @Override
        protected void onPostExecute(Object o) {
            listener.OnVenuesSearchFinished(
                    Arrays.asList(
                            "Item 1",
                            "Item 2",
                            "Item 3",
                            "Item 4",
                            "Item 5",
                            "Item 6",
                            "Item 7",
                            "Item 8",
                            "Item 9",
                            "Item 10"
                    )
            );

        }

        private String call(String op, String apiUrl) {
            String result = null;

            try {
                URL url = new URL(apiUrl); // TODO add to url search string and location
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                ByteArrayBuffer buffer = new ByteArrayBuffer(20);
                int current = 0;

                BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

                while ((current = in.read()) != -1) {
                    buffer.append((byte) current);
                }

                result = new String(buffer.toByteArray());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        private ArrayList parse(String response) {
            ArrayList temp = new ArrayList();
            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.has("response")) {
                    if (jsonObject.getJSONObject("response").has("venues")) {
                        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");
                        Log.d("VenueSearch", "parse:"+jsonArray.length());

                        /*
                        for (int i = 0; i<jsonArray.length(); i++){
                            if (jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).has("icon")) {
                                poi.setCategory(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("name"));
                            }
                        }
                        */
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return temp;
        }
    }

    @Override
    public void doVenueSearch(OnFinishedListener listener, String searchString) {
        new CallAPI().execute(new Object[] {OP_VENUE_LIST, searchString, listener});

    }


}
