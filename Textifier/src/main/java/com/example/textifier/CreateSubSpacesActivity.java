package com.example.textifier;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLCell;
import com.jmatio.types.MLDouble;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import Jama.SingularValueDecomposition;
import Jama.Matrix;

/**
 * Created by Niklas on 2014-01-19.
 */
public class CreateSubSpacesActivity {

   private void copyAssets(Context context)
   {
       AssetManager assetManager = context.getAssets();
       try{

           InputStream assets = assetManager.open("DataSet.mat");
       }
       catch (IOException e) {
           Log.e("tag", "Failed to get asset file list.", e);
       }
       String[] files = null;
       try {
           files = assetManager.list("");
       }
       catch (IOException e)
       {
           Log.e("tag", "Failed to get asset file list.", e);
       }
       for(String filename : files) {
           InputStream in = null;
           OutputStream out = null;
           try {
               in = assetManager.open(filename);
               File outFile = new File(context.getExternalFilesDir(null), filename);
               out = new FileOutputStream(outFile);
               copyFile(in, out);
               in.close();
               in = null;
               out.flush();
               out.close();
               out = null;
           }
           catch(IOException e) {
               Log.e("tag", "Failed to copy asset file: " + "DataSet", e);
           }
       }
   }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }


    public MatFileReader readMatFile(Context context){
        copyAssets(context);
        File file = new File(context.getExternalFilesDir(null), "DataSet.mat");

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


        CreateSubSpace(matFile, 5);
        return null;
    }

    private void CreateSubSpace( MatFileReader matfile, int k)
    {
        SingularValueDecomposition svd0 = null;
        Matrix uk0 = null;
        SingularValueDecomposition svd1 = null;
        Matrix uk1 = null;
        SingularValueDecomposition svd2 = null;
        Matrix uk2 = null;
        SingularValueDecomposition svd3 = null;
        Matrix uk3 = null;
        SingularValueDecomposition svd4 = null;
        Matrix uk4 = null;
        SingularValueDecomposition svd5 = null;
        Matrix uk5 = null;
        SingularValueDecomposition svd6 = null;
        Matrix uk6 = null;
        SingularValueDecomposition svd7 = null;
        Matrix uk7 = null;
        SingularValueDecomposition svd8 = null;
        Matrix uk8 = null;
        SingularValueDecomposition svd9 = null;
        Matrix uk9 = null;

        double[][] dataRefSet = ((MLDouble)matfile.getMLArray("RefSet")).getArray();
        double[][] dataRefAns = ((MLDouble)matfile.getMLArray("RefAns")).getArray();

        for(int i = 0; i < 10; i++)
        {
            double[][] result = new double[][]{};
            int loop = 0;
            for(int j = 0; j < dataRefAns[0].length; j++)
            {
                if(dataRefAns[0][j] == i)
                {
                    for(int l = 0; l < 256; l++)
                    {
                        result[l][loop] = dataRefSet[l][j];
                        loop ++;
                    }
                }
            }
            Matrix resultMatrix = new Matrix(result);



            switch(i){
                case 0: svd0 = resultMatrix.svd();
                    uk0 = subspaces(svd0, k);
                    break;
                case 1: svd1 = resultMatrix.svd();
                    uk1 = subspaces(svd1, k);
                    break;
                case 2: svd2 = resultMatrix.svd();
                    uk2 = subspaces(svd2, k);
                    break;
                case 3: svd3 = resultMatrix.svd();
                    uk3 = subspaces(svd3, k);
                    break;
                case 4: svd4 = resultMatrix.svd();
                    uk4 = subspaces(svd4, k);
                    break;
                case 5: svd5 = resultMatrix.svd();
                    uk5 = subspaces(svd5, k);
                    break;
                case 6: svd6 = resultMatrix.svd();
                    uk6 = subspaces(svd6, k);
                    break;
                case 7: svd7 = resultMatrix.svd();
                    uk7 = subspaces(svd7, k);
                    break;
                case 8: svd8 = resultMatrix.svd();
                    uk8 = subspaces(svd8, k);
                    break;
                case 9: svd9 = resultMatrix.svd();
                    uk9 = subspaces(svd9, k);
                    break;
            }
        }

    }
    private Matrix subspaces(SingularValueDecomposition s, int k)
    {
        Matrix u = s.getU();
        double[][] result = null;
        for(int i = 0; i < k; i++){
            for(int j = 0; j < 256; j++)
            {
                result[j][i] = u.get(j,i);
            }
        }
        return new Matrix(result);
    }
}
