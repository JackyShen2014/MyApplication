package com.jacky.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileOPActivity extends Activity {
    private final static String LOG_TAG = "FileOPActivity";

    private final static String INTERNAL_SD_PATH = "storage/emulated/0";
    private final static String EXTERNAL_SD_PATH = "storage/sdcard1";

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;

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

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "asset_testfile";
                String content = readFromAssets(fileName);
                if (content != null) {
                    textView.setText(content);
                }

            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取当前app路径
                String appFilePath = getApplicationContext().getFilesDir().getAbsolutePath();

                //获取该程序的安装包路径
                String packagePath = getApplicationContext().getPackageResourcePath();

                //获取程序默认数据库路径
                //String dataPath = getApplicationContext().getDatabasePath(new String("DataBaseName")).getAbsolutePath();

                Log.i(LOG_TAG, "Current app path= " + appFilePath);
                Log.i(LOG_TAG, "Current package path= " + packagePath);

                String writeContent = "Current app file path: " + appFilePath;

                writeFileToDataFolder(appFilePath, writeContent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read data from data/data
                String fileDir = getApplicationContext().getFilesDir().getAbsolutePath();

                String content = readFromAppFiles(fileDir);
                textView.setText(content);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToExternalSd();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromExternalSd();
            }
        });
    }

    private void readFromExternalSd() {

    }

    private void writeToExternalSd() {
        String extSdDir = getExternalSdDir();
        if (extSdDir == null){
            Log.e(LOG_TAG,"writeToExternalSd(): failed to get external SD card path!");
            return;
        }

        File file = new File(extSdDir);

        //Check if external SD card file is exists.
        if (!file.exists()){
            Log.e(LOG_TAG,"writeToExternalSd(): no file exists!");
            return;
        }

        //OK, now create a file in external SD card
        File testDir = new File(file,"/Myapplication/test");

        if (!testDir.exists()) {
            testDir.setWritable(true);
            boolean ret = testDir.mkdir();
            Log.i(LOG_TAG, "writeToExternalSd(): create log dir: " + testDir.getAbsolutePath() + " " + ret);
        }

        if (!testDir.canWrite()) {
            testDir.setWritable(true);
        }
        int random = new Random().nextInt(100);
        File logFile = new File(testDir, "log_" + random + ".txt");
        String fileName = logFile.getAbsolutePath();
        Log.d(LOG_TAG, "writeToSDCard(): fileName = " + fileName);
        writeFileSdCardFile(fileName, "Hello! This is the " + random + " example to write sd card file!\n");

    }


    private String readFromAppFiles(String filePath) {
        File fileDir = new File(filePath);
        Log.i(LOG_TAG, "Current file path is " + fileDir.getAbsolutePath());
        if (!fileDir.exists()) {
            Log.e(LOG_TAG, "No file folder exists!");
            return null;
        }

        if (!fileDir.canRead()) {
            fileDir.setReadable(true);
        }

        File[] files = fileDir.listFiles();
        String ret = "Default value!";
        if (files != null && files.length != 0) {
            for (File file : files) {
                ret += readFileSdcardFile(file.getAbsolutePath()) + "\n";
            }
        }
        return ret;
    }


    public void writeFileToDataFolder(String appFilePath, String writeContent) {
        File appFileDir = new File(appFilePath);

        if (!appFileDir.exists()) {
            //Create app file dir if not exists.
            appFileDir.mkdir();
            appFileDir.setWritable(true);
        }

        if (!appFileDir.canWrite()) {
            appFileDir.setWritable(true);
        }

        //Create a new file in this folder.
        int rand = new Random().nextInt(100);
        File newFile = new File(appFileDir + "/file_" + rand);

        writeFileSdCardFile(newFile.getAbsolutePath(), writeContent);

    }


    public void writeToSDCard() {
        File logDir = new File(StorageUtil.getAbsoluteSdcardPath() + "/carlocation/log/");
        if (!logDir.exists()) {
            logDir.setWritable(true);
            boolean ret = logDir.mkdir();
            Log.i(LOG_TAG, "writeToSDCard(): create log dir: " + logDir.getAbsolutePath() + " " + ret);
        }

        if (!logDir.canWrite()) {
            logDir.setWritable(true);
        }
        int random = new Random().nextInt(100);
        File logFile = new File(logDir, "log_" + random + ".txt");
        String fileName = logFile.getAbsolutePath();
        Log.d(LOG_TAG, "writeToSDCard(): fileName = " + fileName);
        writeFileSdCardFile(fileName, "Hello! This is the " + random + " example to write sd card file!\n");

    }


    /**
     * Write to a file <code>fileName</code> with content <code>writeStr</code>.
     *
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
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void readFromSDCard() {
        File logDir = new File(StorageUtil.getAbsoluteSdcardPath() + "/carlocation/log/");
        if (!logDir.exists()) {
            Toast.makeText(FileOPActivity.this, "File Dir " + logDir.getAbsolutePath() + "doesn't exists!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!logDir.canRead()) {
            logDir.setReadable(true);
        }

        File[] files = logDir.listFiles();

        String retStr = "";

        if (files != null && files.length != 0) {
            for (File file : files) {
                String content = readFileSdcardFile(file.getAbsolutePath());
                retStr += "\n" + file.getName() + ":" + content;
            }
        }

        //Show file contents in TextView
        textView.setText(retStr);
    }


    /**
     * Read file content as String format from file.
     *
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
            } else {
                res = EncodingUtils.getString(buffer, "UTF-8");
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


    /**
     * Read file under assets
     *
     * @return string content of file <code>fileName</code> under assets
     * null if read failed.
     */
    public String readFromAssets(String fileName) {
        String ret = null;

        try {
            InputStream in = getResources().getAssets().open(fileName);

            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            in.close();

            ret = EncodingUtils.getString(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return ret;
        }

    }


    public void readFromResRaw() {
        String res = "";
        InputStream inputStream = getResources().openRawResource(R.raw.testfile);

        //get the size of data
        try {
            int legth = inputStream.available();
            byte[] buffer = new byte[legth];
            inputStream.read(buffer);
            res = EncodingUtils.getString(buffer, "BIG5");
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
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button_write_externalSd);
        button8 = (Button) findViewById(R.id.button_read_externalSd);


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


    public String getExternalSdDir() {
        File externalStorageDir = Environment.getExternalStorageDirectory();
        Log.i(LOG_TAG, "writeToExternalSd(): external storage dir is " + externalStorageDir.getAbsolutePath());
        String internalSdPath = externalStorageDir.getAbsolutePath();

        Pattern pattern = Pattern.compile("/?storage/emulated/\\d");
        Matcher matcher = pattern.matcher(internalSdPath);

        if (matcher.find()){
            internalSdPath.replaceAll(INTERNAL_SD_PATH,EXTERNAL_SD_PATH);
            return internalSdPath;
        }else {
            Log.e(LOG_TAG,"getExternalSdDir(): can't resolve external path from internal SD card path!"+internalSdPath);
            return null;
        }

    }
}
