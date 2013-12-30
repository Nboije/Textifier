package com.example.textifier;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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

        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("IMAGE_BYTE_ARRAY", bytes);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //Continue previewing to enable taking more pictures
        camera.startPreview();

        //TODO: The picture needs to be saved to memory
    }



}
