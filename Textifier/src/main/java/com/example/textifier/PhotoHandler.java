package com.example.textifier;

import android.content.Context;
import android.hardware.Camera;
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

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {

        //TODO: The picture needs to be saved to memory
    }
}
