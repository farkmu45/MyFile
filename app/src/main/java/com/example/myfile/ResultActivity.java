package com.example.myfile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfile.databinding.ActivityResultBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResultActivity extends AppCompatActivity {


    private ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        binding.buttonPrivate.setOnClickListener(view -> {
            showPrivate();
        });

        binding.buttonPublic.setOnClickListener(view -> {
            showPublic();
        });
    }

    private void showPublic() {
        String filename = "myfile.txt";
        File file = new File(Environment.getExternalStorageDirectory() + "/Download", filename);
        String text = getData(file);
        binding.resultText.setText(text);
    }

    private void showPrivate() {
        String filename = "myfile.txt";
        File file = new File(getExternalFilesDir("Fark"), filename);
        String text = getData(file);
        binding.resultText.setText(text);
    }

    private String getData(File file) {
        StringBuilder data = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file)) {
            int content;
            while ((content = fis.read()) != -1) {
                data.append((char) content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data.toString();
    }

}