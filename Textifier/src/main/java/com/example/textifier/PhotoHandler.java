package com.example.textifier;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by simon on 12/24/13.
 * PhotoHandler comes to use when a picture is being taken
 */
public class PhotoHandler implements Camera.PictureCallback {

    private Context context;

    public PhotoHandler(Context con){
        context = con;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        MediaActionSound mas = new MediaActionSound();
        mas.play(MediaActionSound.SHUTTER_CLICK);

        //Continue previewing to enable taking more pictures
        camera.startPreview();

        //TODO: The picture needs to be saved to memory
    }



}
