package com.example.word28_gsontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            button=(Button)findViewById(R.id.button);
            textView=(TextView)findViewById(R.id.textview);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        okhttpclient();
                    }
                });
            }
    private void okhttpclient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path="http://192.168.3.102:9090/get_data.json";
                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                        .url(path)
                        .build();
                    Response response=client.newCall(request).execute();
                    String reader=response.body().string();
                    gsondata(reader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
             }
         }).start();
    }
    private void gsondata(String data){
        Gson gson=new Gson();
        List<App> appList=gson.fromJson(data,new TypeToken<List<App>>(){}.getType());
        for(App app:appList){
            Log.d("MainActivity","gsondata:id is "+app.getId());
        }

    }
}
