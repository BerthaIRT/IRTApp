package com.ua.cs495_f18.berthaIRT;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.JsonObject;

public class OnLaunchService extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Client.net = new BerthaNet(this);
        //berthaNet contains code to log in user
        startActivity(new Intent(this, AdminPortalActivity.class));

    }
}
