package fi.kirjokansi.venuesearch;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sampo on 13.12.2014.
 */
public class FoursquareApiImpl implements FoursquareApi {

    private static final String TAG = FoursquareApiImpl.class.getSimpleName();

    private final String CLIENT_ID = "LP3WEJ03DJIKCHCG5XVQUC4U2RJOWZIANDR2EUBAT3LWQGLE";
    private final String CLIENT_SECRET = "0M2U3RKWALTMMUU3BV0O3XTAHXVVZH4BIZYI4XURPL30OFPI";

    private final int OP_VENUE_LIST = 1;

    protected BufferedInputStream getStream(String apiUrl) throws MalformedURLException,IOException{
        URL url = new URL(apiUrl);
        return new BufferedInputStream(url.openStream());
    }

    protected String getDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    private String call(String apiUrl) {
        String result = "";

        try {
            BufferedInputStream in = getStream(apiUrl);
            ByteArrayBuffer buffer = new ByteArrayBuffer(20);
            int current = 0;

            while ((current = in.read()) != -1) {
                buffer.append((byte) current);
            }

            result = new String(buffer.toByteArray());

        } catch (MalformedURLException e) {
            Log.d(TAG, "call() error: url malformed");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "call() error: http connection failed");
            e.printStackTrace();
        }

        return result;
    }

    private List<String> parse(int operation, String response) {
        List<String> result = new ArrayList();
        try {
            JSONObject jsonObject = new JSONObject(response);

            switch (operation) {
            case OP_VENUE_LIST:
                result = parseVenueList(jsonObject);
                break;
            default:
                break;
            }
        } catch (JSONException e) {
            Log.d(TAG, "parse() error: json parser failure");
            e.printStackTrace();
        }
        return result;
    }

    private List<String> parseVenueList(JSONObject jsonObject) throws JSONException{
        List<String> result = new ArrayList();

        if (jsonObject.has("response")) {
            if (jsonObject.getJSONObject("response").has("venues")) {
                JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");
                Log.d("VenueSearch", "parse:"+jsonArray.length());

                for (int i = 0; i<jsonArray.length(); i++){
                    String name = null;
                    String address = null;
                    int distance = -1;

                    if (jsonArray.getJSONObject(i).has("name")) {
                        name = jsonArray.getJSONObject(i).getString("name");
                    }

                    if (jsonArray.getJSONObject(i).has("location")) {
                        if (jsonArray.getJSONObject(i).getJSONObject("location").has("address")) {
                            address = jsonArray.getJSONObject(i).getJSONObject("location").getString("address");
                        }
                        if (jsonArray.getJSONObject(i).getJSONObject("location").has("distance")) {
                            distance = jsonArray.getJSONObject(i).getJSONObject("location").getInt("distance");
                        }
                    }

                    if (name != null) {
                        String line = name;

                        line += ", ";

                        if (address == null) line += "N/A";
                        else line += address;

                        line += ", ";

                        if (distance == -1) line += "N/A";
                        else if (distance > 1000) line += (distance/1000) + "km";
                        else line += distance+"m";

                        result.add(line);
                    }
                }
            }
        }

        return result;
    }

    private class CallAPI extends AsyncTask {

        private int operation;
        private OnFinishedListener listener;

        @Override
        protected Object doInBackground(Object[] params) {

            operation = (int) params[0];
            String searchTerm = (String) params[1];
            Location location = (Location) params[2];
            listener = (OnFinishedListener) params[3];

            Log.d(TAG, "CallAPI.doInBackground() op: "+operation + ", term: " + searchTerm);

            List<String> result = new ArrayList();

            if (operation == OP_VENUE_LIST) {
                String date = getDate();
                double latitude = -1;
                double longitude = -1;

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

                Log.d(TAG, "CallAPI.doInBackground() date: "+date+", lat: "+latitude+", lon: "+longitude);

                String url = "https://api.foursquare.com/v2/venues/search?" +
                    "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET +
                    "&v=" + date +
                    "&ll=" + latitude + "," + longitude +
                    "&query=" + searchTerm;

                Log.d(TAG, "CallAPI.doInBackground() url: "+url);
                String json = call(url);
                Log.d(TAG, "CallAPI.doInBackground() response: "+json.toString());
                result = parse(operation, json);
            }

            return (Object) result;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d(TAG, "CallAPI.onPostExecute()");
            if (operation == OP_VENUE_LIST) {
                List<String> currentVenueList = (ArrayList) o;
                listener.OnVenuesSearchFinished(currentVenueList);
            }
        }
    }

    @Override
    public void doVenueSearch(OnFinishedListener listener, String searchString, Location location) {
        new CallAPI().execute(OP_VENUE_LIST, searchString, location, listener);

    }


}
