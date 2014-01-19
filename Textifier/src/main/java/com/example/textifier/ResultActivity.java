package com.example.textifier;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by simon on 12/30/13.
 * A result activity to display the result of whatever was done to the image that was captured
 */
public class ResultActivity extends Activity {

    ImageView imageView;

    boolean red, green, blue;

    int filterLevel = 10;

    SeekBar levelSeeker;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        red = false;
        green = false;
        blue = false;

        if(bundle != null && bundle.containsKey("IMAGE_BYTE_ARRAY")){
            byte[] image = bundle.getByteArray("IMAGE_BYTE_ARRAY");

            setContentView(R.layout.result_layout);

            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

            levelSeeker = (SeekBar)findViewById(R.id.levelSeeker);

            imageView = (ImageView)findViewById(R.id.resultImageView);

            imageView.setImageBitmap(bmp);
            imageView.setRotation((float)90.0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Did not contain image byte array", Toast.LENGTH_SHORT).show();
        }

        if(levelSeeker != null){

            levelSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    filterLevel = i;
                    TextView tv = (TextView)findViewById(R.id.seekValue);
                    tv.setText("" + i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Toast.makeText(getApplicationContext(), "Ended seek: " + seekBar.getProgress(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Seekbar null ", Toast.LENGTH_LONG).show();
        }
    }

    private void applyColorFilter(ImageView iv, int color){

        iv.setColorFilter(color, PorterDuff.Mode.ADD);

    }

    public void onRedClicked(View view){
        ToggleButton tb = (ToggleButton) view;
        red = tb.isChecked();
        updateColorFilter();
    }

    public void onGreenClicked(View view){
        ToggleButton tb = (ToggleButton) view;
        green = tb.isChecked();
        updateColorFilter();
    }

    public void onBlueClicked(View view){
        ToggleButton tb = (ToggleButton) view;
        blue = tb.isChecked();
        updateColorFilter();
    }

    private void updateColorFilter(){
        imageView.clearColorFilter();
        int color = 0;
        if(red){
            color += Color.RED;
        }
        if(green){
            color += Color.GREEN;
        }
        if(blue){
            color += Color.BLUE;
        }
        applyColorFilter(imageView, color);
    }

    private void grayScaleFilter(Bitmap bm){
        Canvas c = new Canvas(bm);
        Paint p = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(cm);

        p.setColorFilter(cmcf);

        imageView.draw(c);
    }

    public void customFilter(View v){
        int color, r,g,b;

        if(!imageView.isDrawingCacheEnabled()){
            Toast.makeText(getApplicationContext(), "Drawing cache is disabled", Toast.LENGTH_SHORT).show();
            imageView.setDrawingCacheEnabled(true);
        }

        imageView.setImageBitmap(levelFilter(imageView.getDrawingCache(), filterLevel));
    }

    private Bitmap levelFilter(Bitmap bmp, int level){
        Bitmap nBmp = bmp;
        int color, r, g, b;

        for(int y = 0; y < bmp.getHeight(); y++){
            for(int x = 0; x < bmp.getWidth(); x++){

                color = nBmp.getPixel(x,y);
                r = (byte)(color >> 16);
                g = (byte)(color >> 8);
                b = (byte)(color);

                float avg = (float)((r+g+b)/3.0);

                if(avg < level){
                    color = 0xff00ff00;
                    nBmp.setPixel(x, y, color);
                }
            }
        }

        return nBmp;
    }


}
