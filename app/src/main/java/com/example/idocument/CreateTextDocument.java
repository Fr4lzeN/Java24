package com.example.idocument;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class CreateTextDocument implements ICreateDocumentInterface{

    Context context;

    public CreateTextDocument(Context context) {
        this.context = context;
    }

    @Override
    public IDocument createNew(Uri path) {
        try {
            Log.d("new", "new file "+path.getPath());
            ParcelFileDescriptor pfd = context.getContentResolver().
                    openFileDescriptor(path, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            String line = "Overwritten at " + System.currentTimeMillis() +
                    "\n";
            Log.d("new", line);
            fileOutputStream.write((line).getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            pfd.close();
            return new TextDocument(line, path.getPath().substring(path.getPath().lastIndexOf("/")), path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IDocument createOpen(Uri path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     context.getContentResolver().openInputStream(path);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line+System.lineSeparator());
            }
            TextDocument document = new TextDocument(stringBuilder.toString(), path.getPath().substring(path.getPath().lastIndexOf("/")), path);
            return document;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
