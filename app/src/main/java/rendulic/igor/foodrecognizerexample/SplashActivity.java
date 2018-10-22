package rendulic.igor.foodrecognizerexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Calendar;

import rendulic.igor.DatabaseHelper.DatabaseHelper;
import rendulic.igor.DatabaseHelper.OfflineConstants;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgSplash;
    private TextView txtSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        imgSplash=findViewById(R.id.imgSplash);
        txtSplash=findViewById(R.id.txtSplash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                imgSplash.setVisibility(View.VISIBLE);
                txtSplash.setVisibility(View.VISIBLE);
                long setTimer = 800;
                YoYo.with(Techniques.SlideInUp)
                        .duration(setTimer)
                        .playOn(imgSplash);
                YoYo.with(Techniques.BounceInDown)
                        .duration(setTimer)
                        .playOn(txtSplash);
            }
        }, 300);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor editor = getSharedPreferences(OfflineConstants.SP_MAIN, MODE_PRIVATE).edit();
                editor.putBoolean(OfflineConstants.SP_IS_ELEMENTS_INSERTED, true);
                editor.apply();
                startActivity(new Intent(SplashActivity.this, NavigationActivity.class));
            }
        }, 3000);


        SharedPreferences preferences = getSharedPreferences(OfflineConstants.SP_MAIN, MODE_PRIVATE);

        if (preferences.getBoolean(OfflineConstants.SP_IS_ELEMENTS_INSERTED, false)) {


            Toast.makeText(this, "Data Already Inserted", Toast.LENGTH_SHORT).show();
            DatabaseHelper databaseHelper = new DatabaseHelper(SplashActivity.this);
            databaseHelper.getReadableDatabase();


        } else {
            onInsertDataBaseElements();
            DatabaseHelper databaseHelper = new DatabaseHelper(SplashActivity.this);
            databaseHelper.getWritableDatabase();
            for (int i=1;i<11;i++)
            {
                Log.d("INSERTING", "onCreate: ");
                databaseHelper.onInsertDailyData(String.valueOf(i), "0", "0", "0", "0", "0", "0", "0");

            }
            databaseHelper.close();
//            Calendar calendar = Calendar.getInstance();
//            if (android.os.Build.VERSION.SDK_INT >= 23) {
//                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                        8, 0, 0);
//            } else {
//                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                        8, 0, 0);
//            }


           // setAlarm(calendar.getTimeInMillis());

        }

    }


//    private void setAlarm(long time) {
//        //getting the alarm manager
//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        //creating a new intent specifying the broadcast receiver
//        Intent i = new Intent(this, MyAlarm.class);
//
//        //creating a pending intent using the intent
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//
//        //setting the repeating alarm that will be fired every day
//        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
//        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
//    }


    private void onInsertDataBaseElements() {
        DatabaseHelper databaseHelper = new DatabaseHelper(SplashActivity.this);
        databaseHelper.getWritableDatabase();
        databaseHelper.onInsertElements("apple", "114", "0.3", "0", "250", "10", "28", "0.3");
        databaseHelper.onInsertElements("idli", "0", "0", "0", "49", "2", "0.9", "0.1");
        databaseHelper.onInsertElements("poha", "360", "6", "0", "7", "446", "69", "7");
        databaseHelper.onInsertElements("puff", "308", "18", "9", "0", "816", "32", "5");
        databaseHelper.onInsertElements("vada pav", "300", "9", "70", "100", "250", "55", "3");
        databaseHelper.onInsertElements("tea", "1", "6", "0", "10", "4", "0", "0");
        databaseHelper.close();
    }

}
