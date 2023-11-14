package com.example.idocument;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextDocument extends IDocumentAbstract{

    String text;

    public TextDocument(String text, String fileName, Uri path) {
        super(fileName,path);
        this.text = text;
    }

    public TextDocument(String fileName) {
        super(fileName);
        this.text = "";
    }

    @Override
    public void save(Context context) {
        try {
            Log.d("new", "new file "+path.getPath());
            ParcelFileDescriptor pfd = context.getContentResolver().
                    openFileDescriptor(path, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            Log.d("new", text);
            fileOutputStream.write((text).getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            pfd.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public String toString() {
        return "TextDocument{" +
                "text='" + text + '\'' +
                ", fileName='" + fileName + '\'' +
                ", path=" + path +
                '}';
    }
}
