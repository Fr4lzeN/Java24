package com.example.idocument;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.idocument.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    int PICKFILE_REQUEST_CODE = 10;
    int SAVE_FILE_CODE = 1;
    int CREATE_FILE_CODE = 5;
    CreateTextDocument fabric;
    TextDocument current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fabric = new CreateTextDocument(this);
        binding.create.setOnClickListener(v -> {
            Log.d("new", "onCreateNew");
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, CREATE_FILE_CODE);
        });

        binding.open.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, PICKFILE_REQUEST_CODE);
        });

        binding.save.setOnClickListener(v -> {
            current.text=binding.text.getText().toString().replaceAll("\n", System.lineSeparator());
            current.save(this);
            });

        binding.exit.setOnClickListener(v -> {
            current = new TextDocument("");
            binding.text.setText("");
            binding.fileName.setText("");
            binding.path.setText("");
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("new", "onResult");
        if (data!=null){
            Log.d("new", data.getData().getPath());
            Log.d("new", String.valueOf(requestCode));
            if (requestCode==PICKFILE_REQUEST_CODE){
                current = (TextDocument) fabric.createOpen(data.getData());
                Log.d("new", "open file");
                binding.text.setText(current.text);
                binding.fileName.setText(current.fileName);
                binding.path.setText(current.path.getPath());
                return;
            }
            if (requestCode==CREATE_FILE_CODE){
                current = (TextDocument) fabric.createNew(data.getData());
                Log.d("new", current.toString());
                binding.text.setText(current.text);
                binding.fileName.setText(current.fileName);
                binding.path.setText(current.path.getPath());
                return;
            }
            if (requestCode==SAVE_FILE_CODE){
                current.save(this);
            }

        }else{
            Log.d("Read","null data");
        }
    }
}