package com.snake.cloudnext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

public class SecoundActivity extends AppCompatActivity {

    private Button buttonSave;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secound);

        buttonSave = findViewById(R.id.btnSave);
        editText = findViewById(R.id.edtName);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeIntoJsonFile();
            }
        });
    }

    private void writeIntoJsonFile() {
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/cloud_next");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            File file = new File(myDir, "output.json");
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id", "id coming from db");
            jsonObject.put("name", editText.getText().toString());
            jsonObject.put("designation", "Sr. Android dev");
            jsonObject.put("gender", "Male");
            jsonObject.put("salary", "100000");
            jsonObject.put("image","not found");

            jsonArray.put(jsonObject);

            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(jsonArray.toString());
            output.close();
            Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
