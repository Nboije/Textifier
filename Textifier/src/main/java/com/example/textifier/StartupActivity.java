package com.example.textifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * Created by simon on 12/29/13.
 * The startup activity gives a space where options and such can be prepared before starting the image capturing.
 */
public class StartupActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.startup_main);
    }

    public void onStartTextifierClicked(View view){
        Intent intent = new Intent(this, CameraHandler.class);
        startActivity(intent);
    }

}
