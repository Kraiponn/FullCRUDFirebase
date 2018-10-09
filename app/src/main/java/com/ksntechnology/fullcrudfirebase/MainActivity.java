package com.ksntechnology.fullcrudfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtProductName;
    private EditText edtProductPrice;
    private Button btnAdd;
    private String mProductName;
    private String mProductPrice;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Product> mArr;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                return true;
            case R.id.action_read_write:
                startActivity(new Intent(
                        MainActivity.this,
                        RWFileActivity.class
                ));
                return true;
                default: return super.onOptionsItemSelected(item);
        }

    }

    private void initInstance() {
        edtProductName = findViewById(R.id.editProductName);
        edtProductPrice = findViewById(R.id.editProductPrice);
        btnAdd = findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItem();
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Products");
        id = mReference.push().getKey();

        initData();

    }

    private void initData() {
        mArr = new ArrayList<>();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Product.class) != null) {
                    mArr.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        mArr.add(product);
                    }

                    edtProductName.setText(mArr.get(0).getProductName());
                    edtProductPrice.setText(mArr.get(0).getPrice());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void AddItem() {
        mProductName = edtProductName.getText().toString().trim();
        mProductPrice = edtProductPrice.getText().toString().trim();

        Product p = new Product(mProductName, mProductPrice);

        mReference.child(id).setValue(p);
    }




}
