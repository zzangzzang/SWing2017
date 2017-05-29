package com.example.yoon.swing;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by kimym on 2017-05-14.
 */

public class SwingApplication extends Application {

    public static final String DATA_STORAGE_NAME = "swing";

    private static  SwingApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static File getVideoResourcesStorage() {
        File gameDataStorage = new File(getNativeStorage(), DATA_STORAGE_NAME);
        if (!gameDataStorage.exists()) {
            gameDataStorage.mkdirs();
        }
        makeNomediaFile(gameDataStorage.getPath());
        return gameDataStorage;
    }

    public static void makeNomediaFile(String directoryPath) {
        File file = new File(directoryPath, ".nomedia");
        if (file.exists()) {
            return;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getNativeStorage() {
        if (isExternalStorageAvailable()) {
            File externalStoragePath = getExternalStorage();
            if (externalStoragePath != null) {
                makeNomediaFile(externalStoragePath.getPath());
                return externalStoragePath;
            }
        }

        File internalStoragePath = getInternalStoragePath();
        makeNomediaFile(internalStoragePath.getPath());
        return internalStoragePath;
    }

    private static File getExternalStorage() {
        return instance.getExternalFilesDir(null);
    }

    public static File getInternalStoragePath() {
        return instance.getFilesDir();
    }

    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
