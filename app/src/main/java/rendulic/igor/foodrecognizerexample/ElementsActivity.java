package rendulic.igor.foodrecognizerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import rendulic.igor.DatabaseHelper.DataModel;
import rendulic.igor.DatabaseHelper.DatabaseHelper;
import rendulic.igor.DatabaseHelper.OfflineConstants;

public class ElementsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        int gotPosition = getIntent().getIntExtra(OfflineConstants.KEY_SELECETED_POSTION, 1);

        onLoadMyData(recyclerView,gotPosition);
    }

    private void onLoadMyData(RecyclerView recyclerView,int gotPosition) {
        DatabaseHelper databaseHelper = new DatabaseHelper(ElementsActivity.this);
        databaseHelper.getReadableDatabase();
        ArrayList<DataModel> modelArrayList = databaseHelper.onReadOneDayHealthTable(String.valueOf(gotPosition));
        recyclerView.setAdapter(new MyAdapter(ElementsActivity.this, modelArrayList));

        //  Log.d("READ_DATAA", "onLoadMyData: " + modelArrayList.get(1).getName());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
