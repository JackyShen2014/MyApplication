package com.jacky.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.io.InputStream;


public class FileOPActivity extends Activity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_op);

        findViews();
        registerListener();

    }

    private void registerListener() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromResRaw();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromResAsset();
            }
        });
    }

    private void readFromResAsset() {

    }

    private void readFromResRaw() {
        String res = "";
        InputStream inputStream = getResources().openRawResource(R.raw.testfile);

        //get the size of data
        try {
            int legth = inputStream.available();
            byte[] buffer = new byte[legth];
            inputStream.read(buffer);
            res = EncodingUtils.getString(buffer,"BIG5");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Toast.makeText(FileOPActivity.this,res,Toast.LENGTH_LONG).show();
        textView.setText(res);

    }

    private void findViews() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_o, menu);
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
