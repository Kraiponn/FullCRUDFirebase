package com.ksntechnology.fullcrudfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MultiDirectionActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private VertAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_direction);

        initInstance();
    }

    private void initInstance() {
        rcv = findViewById(R.id.recyclerView_Vert);

        ArrayList<VertItem> items = new ArrayList<>();
        ArrayList<HozItem> hozItem = new ArrayList<>();

        hozItem.add(new HozItem("Samsung"));
        hozItem.add(new HozItem("Apple"));
        hozItem.add(new HozItem("Huawei"));
        hozItem.add(new HozItem("Sony"));
        items.add(new VertItem("Mobile", hozItem));

        hozItem = new ArrayList<>();
        hozItem.add(new HozItem("ASP.Net MVC"));
        hozItem.add(new HozItem("Laravel"));
        hozItem.add(new HozItem("Node.JS"));
        hozItem.add(new HozItem("Spring Boot"));
        items.add(new VertItem("Web BackEnd", hozItem));

        hozItem = new ArrayList<>();
        hozItem.add(new HozItem("Angular"));
        hozItem.add(new HozItem("Vue.JS"));
        hozItem.add(new HozItem("ReactJS"));
        hozItem.add(new HozItem("JQuery"));
        items.add(new VertItem("Web FontEnd", hozItem));

        hozItem = new ArrayList<>();
        hozItem.add(new HozItem("Arduino"));
        hozItem.add(new HozItem("Rasberrry PI"));
        hozItem.add(new HozItem("Node MCU"));
        hozItem.add(new HozItem("Arm & PIC"));
        items.add(new VertItem("Embedded", hozItem));

        mAdapter = new VertAdapter(this, items);
        rcv.setAdapter(mAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(this));

    }
}
