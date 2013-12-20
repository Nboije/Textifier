package com.example.textifier;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by Simon on 2013-12-20.
 * Comments:
 * Starting on something to use for handling the camera. This is ongoing and untested.
 */
public class CameraHandler extends Activity implements SurfaceHolder.Callback{

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;

    private Button startPreview, stopPreview;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.RGB_565);
        startPreview = (Button)findViewById(R.id.startPreview);
        stopPreview = (Button)findViewById(R.id.stopPreview);

        startPreview.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v){
                camera = Camera.open();
                if(camera != null){
                    try{
                        camera.setPreviewDisplay(surfaceHolder);
                        camera.startPreview();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }

        });

        stopPreview.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v){
                if(camera != null){
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        /*
        camera = Camera.open();

        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException exception) {
            camera.release();
            camera = null;
        }
        */
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        /*
        camera.stopPreview();
        camera.release();
        camera = null;
        */
    }
}
