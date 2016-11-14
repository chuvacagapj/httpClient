package com.example.jesus.httpclienttarea.model;

import com.example.jesus.httpclienttarea.Events.ModelUpdate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jesus on 13/11/16.
 */

public class Contacto {

    public static List<Contacto> lista = new LinkedList<>();

    public static void setContactos(List<Contacto> l){ lista = l; }

    public static void getContactos(){
        AsyncHttpClient http = new AsyncHttpClient();
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

    private int id;
    private String nombre;
    private int edad;

    public Contacto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
