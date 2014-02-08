package com.example.textifier;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

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

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

    public void onCaptureClicked(View view){
        boolean cameraActive = (camera != null);// && ((ToggleButton)findViewById(R.id.togglePreview)).isChecked();
        if(cameraActive){
            PhotoHandler ph = new PhotoHandler(getApplicationContext());


            camera.takePicture(null, null, ph);

        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void onClickedView(View view){

        if(camera != null){ // Making sure camera has started preview
            Camera.Parameters p = camera.getParameters();
            if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){

                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                Toast.makeText(getApplicationContext(), "Using flash " + p.getFlashMode(), Toast.LENGTH_SHORT).show();
                camera.setParameters(p);

            }

            //Make sure the device has auto focus capabilities
            if(camera.getParameters().getFocusMode().equals(Camera.Parameters.FOCUS_MODE_AUTO) ||
                    camera.getParameters().getFocusMode().equals(Camera.Parameters.FOCUS_MODE_MACRO)){

                p.setAutoExposureLock(false);
                p.setAutoWhiteBalanceLock(false);
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

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
