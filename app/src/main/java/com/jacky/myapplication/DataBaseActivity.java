package com.jacky.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jacky.myapplication.DataBase.ContentDescriptor;
import com.jacky.myapplication.DataBase.DataBaseHelper;

import java.util.Random;

public class DataBaseActivity extends Activity {
    private final static String LOG_TAG = "DataBaseActivity";

    private Button mBtCreate;
    private Button mBtRead;
    private Button mBtInsert;
    private Button mBtDelete;
    private Button mBtUpdate;
    private Button mBtQuery;
    private TextView mTextViewShow;

    SQLiteDatabase database;

    private final static String[] names = {"Jack", "Jone", "Kate", "Hurley", "Sayid", "Desmond", "Jin", "Ben", "Sun"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        findViews();
        registerListeners();
    }

    private void registerListeners() {
        mBtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatabase();
            }
        });
        mBtRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDatabase();
            }
        });
        mBtInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });
        mBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteItem();
            }
        });

        mBtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateItem();
            }
        });
        mBtQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //queryItem();
            }
        });
    }

    private void readDatabase() {
        if (database == null) {
            showToast("No data base could be found. Pls try create a new database!");
            Log.e(LOG_TAG, "readDatabase():No data base could be found. Pls try create a new database");
            return;
        }

        Cursor cursor = database.query(DataBaseHelper.DB_NAME, null, null, null, null, null, null, null);

        String show = "";
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContentDescriptor.HistoriesMessage.Cols.ID));
            String name = cursor.getString(cursor.getColumnIndex(ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME));
            int groupType = cursor.getInt(cursor.getColumnIndex(ContentDescriptor.HistoriesMessage.Cols.HISTORY_MESSAGE_GROUP_TYPE));
            show += "id:\t" + String.valueOf(id)
                    + "\t" + "name:\t" + name
                    + "\t" + "groupType:\t" + groupType
                    + "\n";
        }

        showToTextView(show);


    }

    private void showToTextView(String show) {
        if (show == null) return;
        mTextViewShow.setText(show);
    }

    private void insertItem() {
        if (database == null) {
            showToast("insert item failed due to no database created. Pls try create a new database");
            Log.e(LOG_TAG, "insertItem():insert item failed due to no database created. Pls try create a new database");
            return;
        }

        if (database.isReadOnly()) {
            showToast("insert item failed due to database is read only!");
            Log.e(LOG_TAG, "insertItem(): insert item failed due to database is read only!");
            return;
        }

        ContentValues cv = new ContentValues();

        //为user table添加第一列数据
        int random = new Random().nextInt(names.length);
        cv.put(ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME, names[random]);
        cv.put(ContentDescriptor.HistoriesMessage.Cols.HISTORY_MESSAGE_GROUP_TYPE, new Integer(random));
        database.insert(DataBaseHelper.DB_NAME, null, cv);
    }

    private void createDatabase() {
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(this);
        database = dataBaseHelper.getWritableDatabase();
    }

    private void findViews() {
        mBtCreate = (Button) findViewById(R.id.button_createDB);
        mBtRead = (Button) findViewById(R.id.button_readDB);
        mBtInsert = (Button) findViewById(R.id.button_insert);
        mBtDelete = (Button) findViewById(R.id.button_delete);
        mBtUpdate = (Button) findViewById(R.id.button_update);
        mBtQuery = (Button) findViewById(R.id.button_query);
        mTextViewShow = (TextView) findViewById(R.id.textView_showDB);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_base, menu);
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

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
