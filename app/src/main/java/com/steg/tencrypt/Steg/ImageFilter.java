package com.steg.tencrypt.Steg;

import java.io.File;
import java.io.FileFilter;

public class ImageFilter implements FileFilter {
    File file;

    @Override
    public boolean accept(File file) {
        this.file = file;
        /**
         * Check if the file is valid
         */
        if(file.isDirectory()){
            return true;
        }
        if(file.getAbsolutePath().endsWith(".png") ||
                file.getAbsolutePath().endsWith(".jpg")){
            return true;
        }
        return false;
    }

    /**
     * Check if it is supported file formate
     * @return true if its supported
     */
    public boolean isSupported(){
        if(file.getAbsolutePath().endsWith(".png") ||
                file.getAbsolutePath().endsWith(".jpg")){
            return true;
        }

        return false;
    }

    /**
     * Get the file extension
     * @param f is the file to return the extension
     * @return returns the extension of the file in string
     */

    protected static String getExtension(File f){
        String filePath = f.getAbsolutePath();

        return filePath.split("\\.")[-1];
    }


}
