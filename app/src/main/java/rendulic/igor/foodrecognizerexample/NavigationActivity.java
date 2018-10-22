package rendulic.igor.foodrecognizerexample;

import android.app.NativeActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import rendulic.igor.DatabaseHelper.DataModel;
import rendulic.igor.DatabaseHelper.DatabaseHelper;
import rendulic.igor.DatabaseHelper.OfflineConstants;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BarChart myChart;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myChart = findViewById(R.id.myChart);
        myChart.getDescription().setEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavigationActivity.this,MainActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        databaseHelper = new DatabaseHelper(NavigationActivity.this);
        databaseHelper.getReadableDatabase();
        ArrayList<DataModel> modelArrayList = databaseHelper.onReadDailyElements();

        //  Log.d("onReadDailyElements", "onCreate: "+modelArrayList.get(0).getName());

        setData(modelArrayList);

        myChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {


                int i = (int) e.getX();
                Log.d("MY_CHART", "onValueSelected: " + e.toString());
                Log.d("MY_CHART", "onValueSelected: " + e.getX());
                Log.d("MY_CHART", "onValueSelected: " + e.getY());
                Log.d("MY_CHART", "onValueSelected: H Value " + h.getDataSetIndex());
                Log.d("MY_CHART", "onValueSelected: "+i);

                startActivity(new Intent(NavigationActivity.this, ElementsActivity.class)
                        .putExtra(OfflineConstants.KEY_SELECETED_POSTION, i));
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }




    private void setData(ArrayList<DataModel> count) {
        ArrayList<BarEntry> yEntry = new ArrayList<>();

        for (int i = 0; i < count.size(); i++) {
            //  float value = (float) (Math.random() * 100);

            String totalCalories = count.get(i).getCalories();
            String date = count.get(i).getDate();
            int valueY = Integer.parseInt(totalCalories);
            int valueX = Integer.parseInt(date);

            yEntry.add(new BarEntry(valueX, valueY));
        }


        MyBarDataSet set = new MyBarDataSet(yEntry, "My Calories");
        set.setColors(new int[]{ContextCompat.getColor(getApplicationContext(), R.color.green),
                ContextCompat.getColor(getApplicationContext(), R.color.orange),
                ContextCompat.getColor(getApplicationContext(), R.color.red)});


//        BarDataSet barDataSet = new BarDataSet(yEntry, "My Calories");
//        barDataSet.setColors(ColorTemplate.getHoloBlue());
//        barDataSet.setDrawValues(true);

        BarData barData = new BarData(set);
        myChart.setData(barData);
        myChart.invalidate();
        myChart.animateX(50);
    }

    public class MyBarDataSet extends BarDataSet {

        public MyBarDataSet(List<BarEntry> yVals, String label) {
            super(yVals, label);
        }

        @Override
        public int getColor(int index) {
            if (getEntryForIndex(index).getY() < 500) // less than 95 green
                return mColors.get(0);
            else if (getEntryForIndex(index).getY() < 1000) // less than 100 orange
                return mColors.get(1);
            else // greater or equal than 100 red
                return mColors.get(2);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(NavigationActivity.this,MainActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(NavigationActivity.this, ElementsActivity.class)
                    .putExtra(OfflineConstants.KEY_SELECETED_POSTION, 1));
        } else if (id == R.id.nav_slideshow) {
           // startActivity(new Intent(NavigationActivity.this,MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
