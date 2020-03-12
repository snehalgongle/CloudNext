package com.snake.cloudnext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView noDataImage;
    private List<Data> dataList = new ArrayList();
    private Adapter adapter;
    private FloatingActionButton fab;
    private int REQUEST_CODE=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleView);
        noDataImage = findViewById(R.id.noDataImage);
        fab = findViewById(R.id.fab);

        adapter = new Adapter(this, dataList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new HalfCustomDivider());
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SecoundActivity.class));
                finish();
            }
        });

        if (((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))) {
            readFromJsonFile();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

    }

    private void readFromJsonFile() {
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/cloud_next");
            /*if (!myDir.exists()) {
                myDir.mkdirs();
            }*/
            File file = new File(myDir, "output.json");
            /*if (file.exists ())
                file.delete ();
            OutputStream outputStream = new FileOutputStream(file);*/
            InputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            JSONArray jArray = new JSONArray(bufferedReader.readLine());
            JSONObject jObject;
            for (int i = 0; i < jArray.length(); i++) {
                jObject = jArray.getJSONObject(i);
                dataList.add(new Data(jObject.getString("name"), jObject.getString("designation"), jObject.getString("gender"), jObject.getString("salary"), jObject.getString("image")));
            }
            inputStream.close();
            adapter.notifyDataSetChanged();
            noDataImage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.d(this.getClass().getSimpleName(), "readFromJsonFile: "+e.getMessage());
            recyclerView.setVisibility(View.GONE);
            noDataImage.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) && (grantResults.length > 0 && grantResults[2] == PackageManager.PERMISSION_GRANTED)) {
            } else {
                Toast.makeText(this, "Please accept all the permissions", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }
}
