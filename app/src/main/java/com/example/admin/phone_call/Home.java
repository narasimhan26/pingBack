package com.example.admin.phone_call;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<Contact> contacts;
    RecyclerView rv;
    PermissionManager permissionManager;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);


        this.contacts = new ArrayList<Contact>();
        rv = (RecyclerView) findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new Adapter(this,contacts);
        retrieve();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode,permissions,grantResults);
    }
    //retriving all contacts from db and getting it in array list
    private void retrieve() {
        contacts.clear();

        db = new DatabaseHelper(this);

        Cursor c= (Cursor) db.getAllContacts();

        while (c.moveToNext())
        {
            String status=c.getString(4);
            String name=c.getString(1);
            String number=c.getString(2);
            String thumbnail = c.getString(3);

            Contact contact=new Contact(name,number,status,thumbnail);

            contacts.add(contact);
        }

        if(!(contacts.size()<1))
        {
            rv.setAdapter(adapter);
        }

        db.close();
    }
}
