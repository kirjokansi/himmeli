package fi.kirjokansi.venuesearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends Activity
        implements MainView, AdapterView.OnClickListener {

    private ListView listView;
    private EditText searchText;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        searchText = (EditText) findViewById(R.id.editText);
        searchText.setOnClickListener(this);
        presenter = new MainPresenterImpl(this);

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
    public void updateVenueList(List<String> venues) {
        Log.d("VenueSearch", venues.toString());
        listView.setAdapter(new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, venues));

    }

    @Override
    public void onClick(View v) {
        presenter.onSearchStringUpdated(searchText.getText().toString());
    }

}