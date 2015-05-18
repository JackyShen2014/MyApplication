package com.jacky.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class TimerActivity extends Activity {

    private TextView field_time;
    private Button but_start;
    private Button but_stop;
    private boolean mtimerOn = false;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(TimerActivity.this,"计时时间到！",Toast.LENGTH_SHORT).show();
            mtimerOn = false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        findViews();
        setListeners();

    }

    private void findViews(){
        field_time = (TextView)findViewById(R.id.timeshow);
        but_start = (Button)findViewById(R.id.start);
        but_stop = (Button)findViewById(R.id.stop);
    }

    private void setListeners(){
        but_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Timer
                mtimerOn = true;
                handler.postDelayed(runnable,5000);
            }
        });

        but_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Stop Timer
                if (mtimerOn){
                    handler.removeCallbacks(runnable);
                    Toast.makeText(TimerActivity.this,"计时被取消！",Toast.LENGTH_SHORT).show();
                    mtimerOn = false;
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
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
}
