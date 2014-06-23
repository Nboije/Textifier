package com.example.textifier;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.support.v4.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
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

        String pictureName = "testPicture.png";
        
        SaveFileDialog sFD = new SaveFileDialog();
        //FragmentManager fragmentManager = sFD.getFragmentManager();

//        sFD.show(sFD.getActivity().getSupportFragmentManager(), "saveFile");

        //TODO: The picture needs to be saved to memory
    }

    private void savePicture(String pictureName, byte[] bytes){
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
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public class SaveFileDialog extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.save_file_dialog, null))
                    .setPositiveButton(R.string.savefile, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Save picture
                            Toast.makeText(context, "Saved picture", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            Toast.makeText(context, "Cancelled save picture", Toast.LENGTH_SHORT).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

}
