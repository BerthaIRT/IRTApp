package com.universityofalabama.cs495f2018.berthaIRT;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProviderClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Client extends AppCompatActivity {

    static String currentUser;
    static BerthaNet net;
    static HashMap<String, Report> reportMap;
    static Report activeReport;
    static CognitoUserPool pool;
    static CognitoUserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        net = new BerthaNet(this);

        AWSMobileClient.getInstance().initialize(this).execute();
        AWSConfiguration awsConfiguration = new AWSConfiguration(this);
        if (IdentityManager.getDefaultIdentityManager() == null) {
            final IdentityManager identityManager = new IdentityManager(getApplicationContext(), awsConfiguration);
            IdentityManager.setDefaultIdentityManager(identityManager);
        }
        pool = new CognitoUserPool(this, awsConfiguration);
        reportMap = new HashMap<>();

/*        List<String> fakeCats = new ArrayList<String>();
        fakeCats.add("Drugs");
        fakeCats.add("WEED");
        Report r1 = new Report("1000", "Description A", "3", fakeCats);
        r1.date="4/20/18";
        r1.location="Location A";
        fakeCats = new ArrayList<String>();
        fakeCats.add("Bullying");
        fakeCats.add("Sexual shit");
        Report r2 = new Report("1001", "Description B", "4", fakeCats);
        r2.date="4/21/18";
        r2.location="Location B";
        fakeCats = new ArrayList<String>();
        fakeCats.add("fuck");
        fakeCats.add("jim");
        Report r3 = new Report("1002", "Description C", "9", fakeCats);
        r3.date="4/28/18";
        r3.location="Location C";

        for(Report r : new Report[]{r1, r2, r3})
            reportMap.put(r.reportId, r);*/

        //startActivity(new Intent(this, AdminLoginActivity.class));

        //Todo: check login
    }

    public static void updateReportMap(){
        reportMap = new HashMap<>();
        net.secureSend("report/getall", null, (r)->{
            JsonObject jay = net.jp.parse(r).getAsJsonObject();
            for(Map.Entry<String, JsonElement> e : jay.entrySet())
                reportMap.put(e.getKey(), net.gson.fromJson(e.getValue().getAsString(), Report.class));
        });
    }
}