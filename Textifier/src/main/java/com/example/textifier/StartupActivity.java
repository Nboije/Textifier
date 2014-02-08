package com.example.textifier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

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

    public void onTestPictureClicked(View view){
        Intent intent = new Intent(this, ResultActivity.class);

        String picturePath = "testPicture.png";
        FileManager fm = new FileManager(getApplicationContext());
        File pictureFile = fm.getFile(picturePath);
        if(pictureFile != null){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bmp = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(), options);
            if(bmp != null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes = baos.toByteArray();

                intent.putExtra("IMAGE_BYTE_ARRAY", bytes);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "bmp was null", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "No test image available", Toast.LENGTH_LONG).show();
        }
    }

}
