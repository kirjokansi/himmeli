package fi.kirjokansi.venuesearch;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private EditText searchText;
    private MainPresenter presenter;
    private List<String> venueList;
    private Location location;
    private ArrayAdapter listAdapter;

    private TextWatcher onClick = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged() char count: "+count);
            if (count > 0) {
                presenter.onSearchStringUpdated(searchText.getText().toString(), location);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = MainPresenterImpl.getInstance(this);

        if (savedInstanceState != null) {
            venueList = savedInstanceState.getStringArrayList("venueList");
        } else {
            venueList = new ArrayList();
        }

        location = MyLocation.getInstance().getLastLocation(this);
        Log.d(TAG, "onCreate() loc: "+location);

        if (location == null) {
            Toast.makeText(this, "Location not available. Unable to search local venues.", Toast.LENGTH_SHORT)
                .show();
        }

        searchText = (EditText) findViewById(R.id.editText);
        searchText.addTextChangedListener(onClick);

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, venueList);
        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("venueList", (ArrayList)venueList);
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
    public void onUpdateVenueList(List<String> venues) {
        Log.d(TAG, "onUpdateVenueList() venues: "+venues.toString());
        venueList = venues;
        listAdapter.clear();
        listAdapter.addAll(venueList);
    }

}