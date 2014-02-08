package com.example.textifier;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Toast.makeText(context, "Picture taken", Toast.LENGTH_SHORT).show();
        MediaActionSound mas = new MediaActionSound();
        mas.play(MediaActionSound.SHUTTER_CLICK);

        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        Intent intent = new Intent(context, ResultActivity.class);

        intent.putExtra("IMAGE_BYTE_ARRAY", bytes);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(p);
        //Continue previewing to enable taking more pictures
        //camera.startPreview();

        String pictureName = "testPicture.png";
        FileManager fm = new FileManager(context);
        File picture = fm.getFile(pictureName);

        String TAG = "PictureSave";
        if (picture == null){
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(picture);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }

        //TODO: The picture needs to be saved to memory
    }



}
