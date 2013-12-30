package com.example.textifier;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by simon on 12/30/13.
 * A result activity to display the result of whatever was done to the image that was captured
 */
public class ResultActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();

        if(b != null && b.containsKey("IMAGE_BYTE_ARRAY")){
            byte[] image = b.getByteArray("IMAGE_BYTE_ARRAY");

            setContentView(R.layout.result_layout);

            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);


            ImageView iV = (ImageView)findViewById(R.id.resultImageView);

            iV.setImageBitmap(bmp);
            iV.setRotation((float)90.0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Did not contain image byte array", Toast.LENGTH_SHORT).show();
        }
    }

}
