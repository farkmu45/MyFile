package com.example.myfile;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfile.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonView.setOnClickListener(view -> {
            Intent intent = new Intent(this, ResultActivity.class);
            startActivity(intent);
        });

        binding.buttonPrivate.setOnClickListener(view -> {
            String data = binding.textviewText.getEditText().getText().toString();
            String filename = "myfile.txt";
            File folder = getExternalFilesDir("Fark");
            File file = new File(folder, filename);
            try {
                writeData(data, new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Saved in " + folder, Toast.LENGTH_SHORT).show();
        });

        binding.buttonPublic.setOnClickListener(view -> {
            String data = binding.textviewText.getEditText().getText().toString();
            String filename = "myfile.txt";

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
            Uri fileUri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

            try {
                OutputStream fileOutputStream = getContentResolver().openOutputStream(fileUri);
                writeData(data, fileOutputStream);
                Toast.makeText(this, "Saved in" + Environment.DIRECTORY_DOWNLOADS, Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeData(String data, OutputStream file) {
        try {
            file.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}