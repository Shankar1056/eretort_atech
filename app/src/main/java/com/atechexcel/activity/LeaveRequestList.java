package com.atechexcel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.atechexcel.R;
import com.atechexcel.adapter.LeaveRequestListAdapter;
import com.atechexcel.allinterface.OnClickListener;
import com.atechexcel.common.ClsGeneral;
import com.atechexcel.common.PreferenceName;
import com.atechexcel.model.LeaveAllRequestModel;
import com.atechexcel.utilz.Download_web;
import com.atechexcel.utilz.OnTaskCompleted;
import com.atechexcel.utilz.Utilz;
import com.atechexcel.webservices.WebServices;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Shankar on 3/15/2018.
 */

public class LeaveRequestList extends AppCompatActivity  {
    private RecyclerView rv_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaverequestlist);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        initWidgit();
        if (Utilz.isInternetConnected(LeaveRequestList.this)) {
            callCatApi();
        } else {
            Toast.makeText(LeaveRequestList.this, "" + getResources().getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    private void callCatApi() {
        Utilz.showDailog(LeaveRequestList.this, getResources().getString(R.string.pleasewait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(LeaveRequestList.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {

                Utilz.closeDialog();
                if (response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        Gson gson = new Gson();
                        final LeaveAllRequestModel subCategoryModel = gson.fromJson(response, LeaveAllRequestModel.class);

                        if (subCategoryModel.getStatus().equals("true")) {

                            rv_list.setAdapter(new LeaveRequestListAdapter(LeaveRequestList.this, subCategoryModel.getData(), new OnClickListener() {
                                @Override
                                public void onclick(int position) {
                                    startActivity(new Intent(LeaveRequestList.this, LeaveRequestDetails.class)
                                            .putParcelableArrayListExtra("list", subCategoryModel.getData())
                                            .putExtra("pos", position));
                                }
                            }));

                        } else {
                            Toast.makeText(LeaveRequestList.this, "" + object.opt("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("id", ClsGeneral.getPreferences(LeaveRequestList.this, PreferenceName.NAME)));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.LEAVEREQUESTLIST);

    }

    private void initWidgit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("LeaveRequest");

        rv_list = (RecyclerView) findViewById(R.id.recyclerView);
        rv_list.setLayoutManager(new LinearLayoutManager(LeaveRequestList.this));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
