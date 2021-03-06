package com.jacky.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity {

    private final static String[] ITEMS = {"File Operation Test","TIMER TEST","SQlite Test"};
    ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = getListView();
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ITEMS));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(MainActivity.this,FileOPActivity.class);
                        break;
                    case 1:
                        //start Timer activity
                        intent = new Intent(MainActivity.this,TimerActivity.class);
                        break;
                    case 2:
                        //Start DataBase activity
                        intent = new Intent(MainActivity.this,DataBaseActivity.class);
                    default:break;
                }
                if (intent != null) {
                    startActivity(intent);
                }

            }
        });

    }
}
