package rendulic.igor.foodrecognizerexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rendulic.igor.DatabaseHelper.DataModel;
import rendulic.igor.DatabaseHelper.DatabaseHelper;
import rendulic.igor.DatabaseHelper.OfflineConstants;
import rendulic.igor.Tensor.Camera2BasicFragment;

public class MainActivity extends AppCompatActivity {

    private Button takePicture;
    private ImageView imageView;
    private Uri imageFile;
    String mCurrentPhotoPath;
    List<Map<String,String>> mFoodData;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    SimpleAdapter simpleAdapter;

    private static String MY_TOKEN = null;
    private DatabaseHelper databaseHelper;

    private String searchItemName = "puff";
    private ProgressDialog progressDialog;
    private AppCompatSpinner spinnerDay;
    private String selectedDay;

    String[] days = { "Select Day", "Day 1", "Day 2", "Day 3", "Day 4","Day 5","Day 6","Day 7","Day 8" ,"Day 9","Day 10" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, Camera2BasicFragment.newInstance())
                .commit();

       FloatingActionButton fabBtnDone = findViewById(R.id.fabBtnDone);
        spinnerDay = findViewById(R.id.spinnerDay);

        databaseHelper=new DatabaseHelper(MainActivity.this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,days);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerDay.setAdapter(aa);

        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0)
                {

                    selectedDay = String.valueOf(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please Select Day", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        progressDialog=new ProgressDialog(MainActivity.this);

        fabBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here we have to fetch and insert the data into 2nd database



                long currentDate = System.currentTimeMillis();

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(currentDate);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

//                String selectedDate = String.valueOf(dayOfMonth) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year);

                if (selectedDay!=null){
                    if (contains(searchItemName)) {
                        progressDialog.setMessage("Please Wait..");
                        progressDialog.show();
                        String selectedDate = String.valueOf(dayOfMonth);

                        SharedPreferences preference=getSharedPreferences(OfflineConstants.SP_ITEM_MAIN,MODE_PRIVATE);
                    searchItemName=    preference.getString(OfflineConstants.SP_ITEM_NAME,"puff");

                        ArrayList<DataModel> modelArrayList = databaseHelper.onReadElements(searchItemName);
                        DataModel dataModel = modelArrayList.get(0);


                        Log.d("DATAMODEL", "onClick: " + dataModel.getName());

                        databaseHelper.onInsertDailyCalories(selectedDay,
                                dataModel.getName(),
                                dataModel.getCalories(),
                                dataModel.getFat(),
                                dataModel.getCholestrol(),
                                dataModel.getSodium(),
                                dataModel.getPotassium(),
                                dataModel.getCarbohydrates(),
                                dataModel.getProteins()
                        );

                        // update daily calories

                        //  read today calories from database
                        String todayCalories = databaseHelper.onReadCalories(selectedDay);

                        int totalCalories = Integer.parseInt(dataModel.getCalories()) + Integer.parseInt(todayCalories);
                        Log.d("TODAY_CALORIES", "onClick: ");

                        databaseHelper.onUpdateDailyCalories(String.valueOf(totalCalories), selectedDay);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, NavigationActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            }
                        }, 3000);

                    } else {
                        Toast.makeText(MainActivity.this, "Sorry Item is not in the list", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "Please select day", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    // using intent to take a picture with existing camera app on the phone





    public boolean contains(String userPinCode) {
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("idli");
        list.add("poha");
        list.add("puff");
        list.add("tea");
        list.add("vada pav");
        if (list.contains(userPinCode)) {
            return true;
        } else {
            return false;
        }
    }



//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}
