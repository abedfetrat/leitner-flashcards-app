package com.abed.leitnerflashcards.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileManager {
    private static final String TAG = FileManager.class.getSimpleName();
    private static FileManager INSTANCE;
    private static String filesDir;

    public static FileManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FileManager(context);
        }
        return INSTANCE;
    }

    private FileManager(Context context) {
        filesDir = context.getFilesDir().getAbsolutePath();
    }

    public String saveFile(ContentResolver resolver, Uri uri) {
        String destFilePath = filesDir + File.separator + UUID.randomUUID().toString();
        SaveFileTask task = new SaveFileTask(resolver, destFilePath);
        task.execute(uri);
        return destFilePath;
    }

    public GetFileTask getFile(String path) {
        GetFileTask task = new GetFileTask();
        task.execute(path);
        return task;
    }

    public static class SaveFileTask extends AsyncTask<Uri, Void, Void> {
        private String destFilePath;
        private ContentResolver contentResolver;
        SaveFileTask(ContentResolver resolver, String dest) {
            contentResolver = resolver;
            destFilePath = dest;
        }
        @Override
        protected Void doInBackground(Uri... uris) {
            try (BufferedInputStream in = new BufferedInputStream(contentResolver.openInputStream(uris[0]));
                 BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destFilePath))) {
                byte[] buffer = new byte[1024];
                int len = in.read(buffer);
                while (len != -1) {
                    out.write(buffer, 0, len);
                    len = in.read(buffer);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }
    }

    public static class GetFileTask extends AsyncTask<String, Void, File> {
        private OnSuccessListener<File> listener;
        @Override
        protected File doInBackground(String... paths) {
            return new File(paths[0]);
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            if (file != null && listener != null)
                listener.onSuccess(file);
        }

        public void addOnSuccessListener(OnSuccessListener<File> listener) {
            this.listener = listener;
        }
    }
}