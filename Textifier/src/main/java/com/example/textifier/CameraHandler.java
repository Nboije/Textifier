package com.example.textifier;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.os.Build;
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
public class CameraHandler extends Activity implements SurfaceHolder.Callback, Camera.AutoFocusCallback{

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
                    camera.setDisplayOrientation(90); // Portrait, there should be another way to choose portrait besides setting 90
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
            PhotoHandler ph = new PhotoHandler(getApplicationContext());
            camera.takePicture(null, null, ph);

            ToggleButton tB = ((ToggleButton) findViewById(R.id.togglePreview));


            if(tB.isChecked()){
                //The preview toggle button is reset to off since the takePicture will stop the preview
                tB.setChecked(false);
            }
        }
    }

    public void onClickedView(View view){

        if(camera != null){ // Making sure camera has started preview

            //Make sure the device has auto focus capabilities
            if(camera.getParameters().getFocusMode().equals(Camera.Parameters.FOCUS_MODE_AUTO) ||
                    camera.getParameters().getFocusMode().equals(Camera.Parameters.FOCUS_MODE_MACRO)){

                camera.autoFocus(this);

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAutoFocus(boolean b, Camera camera) {
        MediaActionSound mas = new MediaActionSound();
        if(b){ // Auto focus success
            mas.play(MediaActionSound.FOCUS_COMPLETE);
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
