package com.snake.cloudnext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

    private Context mContext;

    private Button buttonSave;
    private EditText edtName;
    private EditText edtSalary;
    private EditText edtExp;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private RadioButton radioButtonOther;
    private Spinner spinner;
    private String[] spinnerArray = {"Select Desc", "Associate", "Sr. Associate", "Manager", "CEO"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secound);

        mContext = this;
        buttonSave = findViewById(R.id.btnSave);
        edtName = findViewById(R.id.edtName);
        edtSalary = findViewById(R.id.edtSalary);
        edtExp = findViewById(R.id.edtExp);
        radioButtonMale = findViewById(R.id.radioMale);
        radioButtonFemale = findViewById(R.id.radioFemale);
        radioButtonOther = findViewById(R.id.radioOthers);
        spinner = findViewById(R.id.spinner);


        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmptyValues();
            }
        });
    }

    private void checkEmptyValues() {
        if (edtName.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please Enter Your Full Name", Toast.LENGTH_SHORT).show();
        } else if (edtSalary.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please Enter Your Salary", Toast.LENGTH_SHORT).show();
        } else if (edtExp.getText().toString().isEmpty()) {
            Toast.makeText(mContext, "Please Enter Years of Experience", Toast.LENGTH_SHORT).show();
        } else if (spinner.getSelectedItem().equals("Select Desc")) {
            Toast.makeText(mContext, "Please Select Designation", Toast.LENGTH_SHORT).show();
        } else {
            if (radioButtonMale.isChecked()) {
                saveDataIntoDb(edtName.getText().toString(), radioButtonMale.getText().toString(), edtSalary.getText().toString(), spinner.getSelectedItem().toString(), edtExp.getText().toString());
            } else if (radioButtonFemale.isChecked()) {
                saveDataIntoDb(edtName.getText().toString(), radioButtonFemale.getText().toString(), edtSalary.getText().toString(), spinner.getSelectedItem().toString(), edtExp.getText().toString());
            } else if (radioButtonOther.isChecked()) {
                saveDataIntoDb(edtName.getText().toString(), radioButtonOther.getText().toString(), edtSalary.getText().toString(), spinner.getSelectedItem().toString(), edtExp.getText().toString());
            }
        }
    }

    private void saveDataIntoDb(String name, String gender, String salary, String designation, String exp) {
        SQLiteDatabase database = new SQLiteDBHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_NAME, name);
        values.put(SQLiteDBHelper.COLUMN_GENDER, gender);
        values.put(SQLiteDBHelper.COLUMN_SALARY, salary);
        values.put(SQLiteDBHelper.COLUMN_EXP, exp);
        values.put(SQLiteDBHelper.COLUMN_DES, designation);
        database.insert(SQLiteDBHelper.TABLE_NAME, null, values);

        database.close();

        Toast.makeText(mContext, "Data Saved ", Toast.LENGTH_LONG).show();

        startActivity(new Intent(this,MainActivity.class));
        finish();

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
