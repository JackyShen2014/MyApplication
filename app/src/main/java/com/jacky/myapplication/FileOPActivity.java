package com.jacky.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jacky.myapplication.Util.StorageUtil;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class FileOPActivity extends Activity {
    private final static String LOG_TAG = "FileOPActivity";

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
                writeToSDCard();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromSDCard();
            }
        });
    }



    private void writeToSDCard(){
        File logDir = new File(StorageUtil.getAbsoluteSdcardPath()+"/carlocation/log/");
        if(!logDir.exists()){
            logDir.setWritable(true);
            boolean ret = logDir.mkdir();
            Log.i(LOG_TAG,"writeToSDCard(): create log dir: "+logDir.getAbsolutePath()+" "+ret);
        }

        if (!logDir.canWrite()){
            logDir.setWritable(true);
        }
        int random = new Random().nextInt(100);
        File logFile = new File(logDir,"log_"+random+".txt");
        String fileName = logFile.getAbsolutePath();
        Log.d(LOG_TAG, "writeToSDCard(): fileName = " + fileName);
        writeFileSdCardFile(fileName, "Hello! This is the "+random+" example to write sd card file!\n");

    }


    /**
     * Write to a file <code>fileName</code> with content <code>writeStr</code>.
     * @param fileName file name must be an absolute path name.
     * @param writeStr content to be written.
     */
    public void writeFileSdCardFile(String fileName, String writeStr) {

        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(fileName);
            byte[] bytes = writeStr.getBytes();

            fout.write(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fout != null){
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void readFromSDCard() {
        File logDir = new File(StorageUtil.getAbsoluteSdcardPath()+"/carlocation/log/");
        if (!logDir.exists()){
            Toast.makeText(FileOPActivity.this,"File Dir "+logDir.getAbsolutePath()+"doesn't exists!",Toast.LENGTH_LONG).show();
            return;
        }

        if (!logDir.canRead()){
            logDir.setReadable(true);
        }

        File[] files = logDir.listFiles();

        String retStr = "";

        if (files != null && files.length != 0){
            for (File file : files){
                String content = readFileSdcardFile(file.getAbsolutePath());
                retStr += "\n"+file.getName()+":"+content;
            }
        }

        //Show file contents in TextView
        textView.setText(retStr);
    }


    /**
     * Read file content as String format from file.
     * @param fileName file name should be absolute file path.
     * @return file content as String format if success, null if read failed.
     */
    public String readFileSdcardFile(String fileName) {
        String res = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(fileName);

            int length = fin.available();
            byte[] buffer = new byte[length];
            int len = fin.read(buffer);

            if (len <= 0) {
                Log.i(LOG_TAG, "readFileSdcardFile(): error file " + fileName);
            }else {
                res = EncodingUtils.getString(buffer,"UTF-8");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

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
