package com.example.dell.rateapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RateActivity extends AppCompatActivity implements Runnable{
    private final String TAG = "Rate";
    private float dollarRate = 0.1f;
    private float euroRate = 0.2f;
    private float wonRate = 0.3f;
    private String updateDate = "0000-00-00";
    private String currentDate = "0000-00-00";
    EditText rmb;
    TextView show;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rmb = (EditText) findViewById(R.id.rmb);
        show = (TextView) findViewById(R.id.show);

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        currentDate = sf.format(curDate);

        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate = sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate = sharedPreferences.getFloat("won_rate",0.0f);
        updateDate = sharedPreferences.getString("update_date", "0000-00-00");

        Log.i(TAG, "onCreate: sp dollarRate=" + dollarRate);
        Log.i(TAG, "onCreate: sp euroRate=" + euroRate);
        Log.i(TAG, "onCreate: sp wonRate=" + wonRate);

        if(!updateDate.equals(currentDate)) {
            Thread t = new Thread(this);
            t.start();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 5) {
                        Bundle bdl = (Bundle) msg.obj;
                        dollarRate = bdl.getFloat("dollar-rate");
                        euroRate = bdl.getFloat("euro-rate");
                        wonRate = bdl.getFloat("won-rate");
                        Log.i(TAG, "handleMessage: dollarRate:" + dollarRate);
                        Log.i(TAG, "handleMessage: euroRate:" + euroRate);
                        Log.i(TAG, "handleMessage: wonRate:" + wonRate);
                        Toast.makeText(RateActivity.this, "汇率已更新", Toast.LENGTH_SHORT).show();


                        Log.i(TAG, "handleMessage: currentDate:" + currentDate);
                        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putFloat("dollar_rate", dollarRate);
                        editor.putFloat("euro_rate", euroRate);
                        editor.putFloat("won_rate", wonRate);
                        editor.putString("update_date", currentDate);

                        editor.apply();
                    }
                    super.handleMessage(msg);
                }
            };
        }
    }

    public void onClick(View btn) {
        Log.i(TAG, "onClick: ");
        String str = rmb.getText().toString();
        Log.i(TAG, "onClick: get str=" + str);
        float r = 0;
        if(str.length()>0){
            r = Float.parseFloat(str);
        }else{
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(TAG, "onClick: r=" + r);
        if(btn.getId()==R.id.dollar){
            show.setText(String.format("%.2f",r*dollarRate));
        }else if(btn.getId()==R.id.euro){
            show.setText(String.format("%.2f",r*euroRate));
        }else{
            show.setText(String.format("%.2f",r*wonRate));
        }
    }
    public void openOne(View btn){
        Intent config = new Intent(this,ConfigActivity.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);
        Log.i(TAG, "openOne: dollarRate=" + dollarRate);
        Log.i(TAG, "openOne: euroRate=" + euroRate);
        Log.i(TAG, "openOne: wonRate=" + wonRate);
        startActivityForResult(config,1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.1f);
            euroRate = bundle.getFloat("key_euro",0.1f);
            wonRate = bundle.getFloat("key_won",0.1f);
            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("dollar_rate",dollarRate);
        editor.putFloat("euro_rate",euroRate);
        editor.putFloat("won_rate",wonRate);
        editor.commit();
        Log.i(TAG, "onActivityResult: 数据已保存到sharedPreferences");

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void run() {
        Log.i(TAG, "run: run()......");
        for(int i=1;i<3;i++){
            Log.i(TAG, "run: i=" + i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //获取网络数据
        Bundle bundle = new Bundle();
        URL url = null;
        try {
            url = new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = inputStream2String(in);
            Document doc = Jsoup.parse(html);
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Log.i(TAG, "run: tables=" + tables);
            Element table6 = tables.get(0);
            Log.i(TAG, "run: table6=" + table6);
            //获取TD中的数据
            Elements tds = table6.getElementsByTag("td");
            Log.i(TAG, "run: tds=" + tds);
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, "run: " + str1 + "==>" + val);
                float v = 100f / Float.parseFloat(val);
                if("美元".equals(str1)){
                    bundle.putFloat("dollar-rate", v);
                }else if("欧元".equals(str1)){
                    bundle.putFloat("euro-rate", v);
                }else if("韩国元".equals(str1)){
                    bundle.putFloat("won-rate", v);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取Msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
//msg.what = 5;
        msg.obj = bundle;
        handler.sendMessage(msg);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            Intent config = new Intent(this,ConfigActivity.class);
            config.putExtra("dollar_rate_key",dollarRate);
            config.putExtra("euro_rate_key",euroRate);
            config.putExtra("won_rate_key",wonRate);
            Log.i(TAG, "openOne: dollarRate=" + dollarRate);
            Log.i(TAG, "openOne: euroRate=" + euroRate);
            Log.i(TAG, "openOne: wonRate=" + wonRate);
            startActivityForResult(config,1);
        }
        return super.onOptionsItemSelected(item);
    }
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}

