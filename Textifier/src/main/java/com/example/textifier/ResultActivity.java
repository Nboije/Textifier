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
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

    Bitmap picture;

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

            picture = BitmapFactory.decodeByteArray(image, 0, image.length);

            levelSeeker = (SeekBar)findViewById(R.id.levelSeeker);

            imageView = (ImageView)findViewById(R.id.resultImageView);

            imageView.setImageBitmap(picture);
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
                    imageView.setImageBitmap(levelFilter(picture, filterLevel));
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void grayScaleFilter(Bitmap bm){
        Canvas c = new Canvas(bm);
        Paint p = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(cm);

        p.setColorFilter(cmcf);

        int x = (int)imageView.getX();
        int y = (int)imageView.getY();
        c.drawBitmap(bm, x, y, p);
        imageView.setImageDrawable(new BitmapDrawable(getResources(), bm));
        //imageView.draw(c);
    }

    public void customFilter(View v){
        int color, r,g,b;

        if(!imageView.isDrawingCacheEnabled()){
            Toast.makeText(getApplicationContext(), "Drawing cache was disabled", Toast.LENGTH_SHORT).show();
            imageView.setDrawingCacheEnabled(true);
        }

        //grayScaleFilter(imageView.getDrawingCache());
        imageView.setImageBitmap(threshFilter(imageView.getDrawingCache()));
        //imageView.setImageBitmap(levelFilter(imageView.getDrawingCache(), filterLevel));
    }

    private Bitmap levelFilter(Bitmap bmp, int level){
        Bitmap nBmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
        int color, r, g, b;
        //float sum = 0;

        for(int y = 0; y < bmp.getHeight(); y++){
            for(int x = 0; x < bmp.getWidth(); x++){

                color = nBmp.getPixel(x,y);
                r = (color >> 16) & 0xff;
                g = (color >> 8) & 0xff;
                b = (color) & 0xff;

                float sum = (float)((r+g+b)/3.0);

                if(sum < level){
                    color = 0xff000000;
                    nBmp.setPixel(x, y, color);
                }
                else {
                    color = 0xffffffff;
                    Log.i("Settings", "x: " + x + " y:" + y + " color:" + color);
                    nBmp.setPixel(x, y, color);
                }

            }
        }


        return nBmp;
    }

    private Bitmap threshFilter(Bitmap bmp)
    {
        Bitmap nBmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
        int color, r, g, b, avg;
        float sum = 0;

        for(int y = 0; y < bmp.getHeight(); y++){
            for(int x = 0; x < bmp.getWidth(); x++){

                color = nBmp.getPixel(x,y);
                r = (byte)(color >> 16);
                g = (byte)(color >> 8);
                b = (byte)(color);

                sum += (float)(r+g+b);

            }
        }

        avg = (int)(sum / (nBmp.getWidth() * nBmp.getHeight()));

        nBmp = levelFilter(nBmp, avg);


        return nBmp;
    }


}
