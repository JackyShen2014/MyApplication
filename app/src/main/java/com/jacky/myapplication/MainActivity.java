package com.jacky.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ListActivity {

    private final static String[] ITEMS = {"BMI","TIMER TEST"};
    ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = getListView();
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ITEMS));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,"您选择了"+ITEMS[position],Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        //TODO start BMI app
                        break;
                    case 1:
                        //start Timer activity
                        Intent i = new Intent(MainActivity.this,TimerActivity.class);
                        startActivity(i);
                        break;
                    default:break;
                }
            }
        });

    }
}
