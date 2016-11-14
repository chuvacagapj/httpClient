package com.example.jesus.httpclienttarea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jesus.httpclienttarea.Events.ModelUpdate;
import com.example.jesus.httpclienttarea.Events.ReqEvent;
import com.example.jesus.httpclienttarea.model.Contacto;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    ListView list;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MyAdapter();
        EventBus.getDefault().register(this);
        Contacto.getContactos();
        list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);
        //Intent i = new Intent(this, MyService.class);
        //startService(i);
        //EventBus.getDefault().postSticky(new ReqEvent());

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(ModelUpdate md){
        adapter.notifyDataSetChanged();
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount(){
            return Contacto.lista.size();
        }

        @Override
        public Object getItem(int i) {
            return Contacto.lista.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.item, null);
            TextView nombre = (TextView) view.findViewById(R.id.nombreTxt);
            TextView numero = (TextView) view.findViewById(R.id.numeroTxt);
            TextView edad = (TextView) view.findViewById(R.id.EdadTxt);
            Contacto c = Contacto.lista.get(i);
            nombre.setText(c.getNombre());
            numero.setText(c.getId() + "");
            edad.setText(c.getEdad() + "");
            return view;
        }
    }



}
