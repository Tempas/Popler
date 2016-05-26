package com.popler.ryan.popler.Util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 5/22/16.
 */
public class IoUtil {
    private static final String TAG = "IoUtil";

    public static <T> T loadObjectFromFile(String fileName, Context context) {
        File cacheDirectory = context.getCacheDir();
        File f = new File(cacheDirectory, fileName);

        T result = null;

        if (!f.exists()) {
            return result;
        }

        try {
            FileInputStream fileInputStream  = new FileInputStream(f);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            result = (T) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | RuntimeException e) {
            Log.e(TAG, "Error loading cached file " + fileName, e);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Error loading cached file " + fileName, e);
        }

        return result;
    }

    public static <T> void saveObjectToFile(String fileName, T object, Context context) {
        File cacheDirectory = context.getCacheDir();
        File f = new File(cacheDirectory, fileName);

        // Overwrite the file everytime
        if (f.exists()) {
            f.delete();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(object);
            objectOutputStream.close();

        } catch (IOException | RuntimeException e) {
            Log.e(TAG, "Error caching file " + fileName, e);
        }
    }
}
