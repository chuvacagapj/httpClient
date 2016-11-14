package com.example.jesus.httpclienttarea;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.jesus.httpclienttarea.Events.ModelUpdate;
import com.example.jesus.httpclienttarea.Events.ReqEvent;
import com.example.jesus.httpclienttarea.model.Contacto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MyService extends Service {

    AsyncHttpClient http = new AsyncHttpClient();

    public MyService() {
        System.out.println("Created Service");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    public void onDestroy(){
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(ReqEvent reqEvent){
        System.out.println("Event Recived");
        String url = "http://oscarbego.comule.com/android/index.php/alumnos";
        http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String responseBodyStr = new String(responseBody);
                Type listType = new TypeToken<LinkedList<Contacto>>(){}.getType();
                List lista = new Gson().fromJson(responseBodyStr, listType);
                Contacto.setContactos(lista);
                EventBus.getDefault().post(new ModelUpdate());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
