package com.example.textifier;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by simon on 12/30/13.
 * A result activity to display the result of whatever was done to the image that was captured
 */
public class ResultActivity extends Activity {

    ImageView imageView;

    boolean r,g,b;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        r = false;
        g = false;
        b = false;

        if(bundle != null && bundle.containsKey("IMAGE_BYTE_ARRAY")){
            byte[] image = bundle.getByteArray("IMAGE_BYTE_ARRAY");

            setContentView(R.layout.result_layout);

            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);


            imageView = (ImageView)findViewById(R.id.resultImageView);

            imageView.setImageBitmap(bmp);
            imageView.setRotation((float)90.0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Did not contain image byte array", Toast.LENGTH_SHORT).show();
        }
    }

    private void applyColorFilter(ImageView iv, int color){

        iv.setColorFilter(color, PorterDuff.Mode.ADD);

    }

    public void onRedClicked(View view){
        ToggleButton tb = (ToggleButton) view;
        r = tb.isChecked();
        updateColorFilter();
    }

    public void onGreenClicked(View view){
        ToggleButton tb = (ToggleButton) view;
        g = tb.isChecked();
        updateColorFilter();
    }

    public void onBlueClicked(View view){
        ToggleButton tb = (ToggleButton) view;
        b = tb.isChecked();
        updateColorFilter();
    }

    private void updateColorFilter(){
        imageView.clearColorFilter();
        int color = 0;
        if(r){
            color += Color.RED;
        }
        if(g){
            color += Color.GREEN;
        }
        if(b){
            color += Color.BLUE;
        }
        applyColorFilter(imageView, color);
    }
}
