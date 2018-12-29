package com.atechexcel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.atechexcel.R;
import com.atechexcel.common.ClsGeneral;
import com.atechexcel.common.PreferenceName;
import com.atechexcel.login.LoginActivity;
import com.atechexcel.studentforum.AskQuestion;
import com.atechexcel.utilz.Download_web;
import com.atechexcel.utilz.OnTaskCompleted;
import com.atechexcel.utilz.Utilz;
import com.atechexcel.webservices.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedBackActivity extends AppCompatActivity {
    private EditText input_name, input_mobile, input_messae;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        input_name = (EditText) findViewById(R.id.input_name);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        input_messae = (EditText) findViewById(R.id.input_messae);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_name.getText().toString().trim().equals("")) {
                    Toast.makeText(FeedBackActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_mobile.getText().toString().trim().equals("")) {
                    Toast.makeText(FeedBackActivity.this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (input_messae.getText().toString().trim().equals("")) {
                    Toast.makeText(FeedBackActivity.this, "Enter Feedback", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    saveFeedback();
                }
            }
        });
    }

    private void saveFeedback() {
        Utilz.showDailog(FeedBackActivity.this, getResources().getString(R.string.please_wait));
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        Download_web web = new Download_web(FeedBackActivity.this, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String response) {
                Utilz.closeDialog();
                if (response.length() > 0) {
                    finish();
                }
            }
        });
        nameValuePairs.add(new BasicNameValuePair("name", input_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("mobile", input_mobile.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("message", input_messae.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("date ", Utilz.getCurrentDate(FeedBackActivity.this)));
        web.setData(nameValuePairs);
        web.setReqType(false);
        web.execute(WebServices.INSERTFEEDBACK);
    }
}
