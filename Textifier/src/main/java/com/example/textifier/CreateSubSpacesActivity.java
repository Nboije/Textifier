package com.example.textifier;

import android.app.Activity;
import com.jmatio.io.MatFileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Niklas on 2014-01-19.
 */
public class CreateSubSpacesActivity extends Activity {

    private File getDataSetFromResources()
    {
        File newFile = null;
        URL url = null;
        try{
            url = new URL("file://android_asset/dataset.mat");
        }
        catch( MalformedURLException e)
        {
            throw new RuntimeException("Malformed URL!");
        }
        try{
            newFile = new File(url.toURI());
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException("URI FAILED " + e);
        }
        finally
        {
            if(newFile != null)
            {
                return newFile;
            }
        }
        return null;
    }
    public MatFileReader readMatFile(){
        File file = getDataSetFromResources();

        MatFileReader matFile = null;

        try {
            matFile = new MatFileReader(file);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException("File not found");
        }
        catch (IOException e)
        {
            throw new RuntimeException("IO Error occured");
        }
        return matFile;
    }



}
