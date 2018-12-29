package com.atechexcel.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.atechexcel.R;
import com.atechexcel.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    Intent openstartingpoint=new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(openstartingpoint);
                    finish();
                }
            }
        };
        timer.start();

    }
}
