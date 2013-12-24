package com.example.textifier;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        if(surfaceView == null){
            Toast.makeText(getApplicationContext(), "surfaceView is null", Toast.LENGTH_SHORT).show();
        }
        if(surfaceView != null){
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setFormat(PixelFormat.RGB_565);

        }
    }


    /**
     * The toggle button controls the camera preview, either on or off. If it is turned off it will
     * freeze the latest frame from the camera
     * @param view is the ToggleButton as view
     */
    public void onToggleClicked(View view){

        //The toggle button has been set to ON, start the camera
        if(((ToggleButton) view).isChecked()){

            //Get camera
            camera = Camera.open();

            if(camera != null){
                try{
                    camera.setDisplayOrientation(90);
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        else{ //The toggle button has been set to OFF, turn off the camera
            if(camera != null){
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        }
    }

    public void onCaptureClicked(View view){
        if(camera != null){
            //Toast.makeText(getApplicationContext(),  "You took a picture!", Toast.LENGTH_LONG).show();
            camera.takePicture(null, null, new PhotoHandler(getApplicationContext()));


            ToggleButton tB = ((ToggleButton) findViewById(R.id.togglePreview));


            if(tB.isChecked()){

                //The camera will stop preview when a picture is taken, so let's release it
                camera.stopPreview();
                camera.release();
                camera = null;

                //The preview toggle button is reset to off since the takePicture will stop the preview
                tB.setChecked(false);
            }
        }
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
