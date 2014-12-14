package fi.kirjokansi.venuesearch;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity implements MainView {

    private ListView listView;
    private EditText searchText;
    private MainPresenter presenter;
    private ArrayList venueList;
    private Location location;
    private ArrayAdapter listAdapter;

    private TextWatcher onClick = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("VenueSearch", "beforeTextChanged: "+count);
            if (count > 0) {
                presenter.onSearchStringUpdated(searchText.getText().toString(), location);
            }
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenterImpl(this);
        venueList = new ArrayList();

        listView = (ListView) findViewById(R.id.listView);
        searchText = (EditText) findViewById(R.id.editText);
        searchText.addTextChangedListener(onClick);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = lm.getBestProvider(new Criteria(), false);
        location = lm.getLastKnownLocation(provider);

        if (savedInstanceState != null) {
            venueList = savedInstanceState.getStringArrayList("venueList");
        }
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, venueList);
        listView.setAdapter(listAdapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("venueList", venueList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdateVenueList(ArrayList venues) {
        Log.d("VenueSearch", "onUpdateVenueList: "+venues.toString());
        venueList = venues;
        listAdapter.clear();
        listAdapter.addAll(venueList);
    }

}