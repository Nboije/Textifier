package com.example.textifier;

/**
 * Created by Niklas on 2014-01-24.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetFile {

    public String readTextFile(String fileName) {
        String returnvalue = "";
        FileReader file = null;
        String line = "";
        try {
            file = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(file);
            while ((line = reader.readLine()) != null) {
                returnvalue += line + "\n";
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        catch (IOException e) {
            throw new RuntimeException("IO Error occured");
        }
        finally {

            if (file != null) {
                try {
                    file.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return returnvalue;
    }

}
