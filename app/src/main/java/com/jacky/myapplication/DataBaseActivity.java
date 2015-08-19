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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBaseActivity extends Activity {
    private final static String LOG_TAG = "DataBaseActivity";

    private Button mBtCreate;
    private Button mBtRead;
    private Button mBtInsert;
    private Button mBtDelete;
    private Button mBtUpdate;
    private Button mBtDrop;
    private TextView mTextViewShow;

    private DataBaseHelper mDataBaseHelper;
    SQLiteDatabase mDataBase;

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
                String content  = readDatabase();
                if (content != null){
                    showToTextView(content);
                }
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
                deleteItem();
            }
        });

        mBtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });
        mBtDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropTable();
            }
        });
    }


    /**
     * Drop table
     */
    public void dropTable() {
        if (!checkDBExist()) return;

        Log.i(LOG_TAG, "DropTable():Drop the current table!");
        mDataBase.execSQL(DataBaseHelper.HISTORIES_MESSAGE_TABLE_DROP_SQL);
        mDataBaseHelper.onCreate(mDataBase);
        mDataBase.close();
    }

    /**
     * Update one row in random with random content.
     */
    public void updateItem() {
        if (!checkDBWritable()) return;

        ContentValues cv = new ContentValues();
        int groupType  = new Random().nextInt(names.length);
        String addName = names[groupType];
        cv.put(ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME,addName);
        cv.put(ContentDescriptor.HistoriesMessage.Cols.HISTORY_MESSAGE_GROUP_TYPE,groupType);

        //Get all UserName column content
        List<String> list = getUserNameCols();

        if (list != null){
            String whereClause = ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME+"=?";
            String[] whereArgs = {list.get(new Random().nextInt(list.size()-1))};

            mDataBase.update(ContentDescriptor.HistoriesMessage.NAME, cv, whereClause, whereArgs);
        }


    }

    private List<String> getUserNameCols() {
        if(!checkDBExist()){
            return null;
        }

        List<String> list = new ArrayList<>();

        Cursor cursor = mDataBase.query(ContentDescriptor.HistoriesMessage.NAME, null, null, null, null, null, null, null);


        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME));
            list.add(name);
        }
        return list;
    }


    /**
     * Delete on row in random according random name.
     */
    public void deleteItem() {
        if (!checkDBWritable()) return;

        //Get all UserName column content
        List<String> list = getUserNameCols();

        if (list != null){
            String whereClause = ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME+"=?";
            String[] whereArgs = {list.get(new Random().nextInt(list.size()))};

            mDataBase.delete(ContentDescriptor.HistoriesMessage.NAME, whereClause, whereArgs);
        }

    }

    /**
     *
     */
    /**
     * Use <code>query</code> to retrieve cursor pointed table and read it's content
     * by <code>cursor.moveToNext()</code>
     * @return null if database is not exist; otherwise content of database.
     */
    public String readDatabase() {
        if(!checkDBExist()){
            return null;
        }

        Cursor cursor = mDataBase.query(ContentDescriptor.HistoriesMessage.NAME, null, null, null, null, null, null, null);

        String show = "";
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ContentDescriptor.HistoriesMessage.Cols.ID));
            String name = cursor.getString(cursor.getColumnIndex(ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME));
            int groupType = cursor.getInt(cursor.getColumnIndex(ContentDescriptor.HistoriesMessage.Cols.HISTORY_MESSAGE_GROUP_TYPE));
            show += "id:\t" + String.valueOf(id)
                    + "\t\t\t" + "name:\t" + name
                    + "\t\t\t" + "groupType:\t" + groupType
                    + "\n";
        }

        return show;
    }


    private void showToTextView(String show) {
        if (show == null) return;
        mTextViewShow.setText(show);
    }

    /**
     * Insert a row to data base by <code>database.insert()</code>
     *
     */
    public void insertItem() {
        if (!checkDBWritable()) return;

        ContentValues cv = new ContentValues();

        //为user table添加一列数据
        int random = new Random().nextInt(names.length);
        cv.put(ContentDescriptor.HistoriesMessage.Cols.OWNER_USER_NAME, names[random]);
        cv.put(ContentDescriptor.HistoriesMessage.Cols.HISTORY_MESSAGE_GROUP_TYPE, new Integer(random));
        mDataBase.insert(ContentDescriptor.HistoriesMessage.NAME, null, cv);
    }

    private boolean checkDBExist() {
        if (mDataBase == null) {
            showToast("No data base could be found. Pls try create a new database!");
            Log.e(LOG_TAG, "readDatabase():No data base could be found. Pls try create a new database");
            return false;
        }
        return true;
    }

    private boolean checkDBWritable() {
        if (checkDBExist()){
            if (!mDataBase.isReadOnly()) {
                return true;
            }else {
                showToast("insert item failed due to database is read only!");
                Log.e(LOG_TAG, "insertItem(): insert item failed due to database is read only!");
            }
        }

        return false;

    }

    /**
     * Create a data base by <code>DataBaseHelper</code>
     * @see DataBaseHelper
     */
    public void createDatabase() {
        mDataBaseHelper = DataBaseHelper.getInstance(this);
        mDataBase = mDataBaseHelper.getWritableDatabase();
    }

    private void findViews() {
        mBtCreate = (Button) findViewById(R.id.button_createDB);
        mBtRead = (Button) findViewById(R.id.button_readDB);
        mBtInsert = (Button) findViewById(R.id.button_insert);
        mBtDelete = (Button) findViewById(R.id.button_delete);
        mBtUpdate = (Button) findViewById(R.id.button_update);
        mBtDrop = (Button) findViewById(R.id.button_drop);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
