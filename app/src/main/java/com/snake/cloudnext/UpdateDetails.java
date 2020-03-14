package com.snake.cloudnext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import static com.snake.cloudnext.SQLiteDBHelper.TABLE_NAME;

public class UpdateDetails extends AppCompatActivity {

    private Context mContext;

    private Button buttonSave;
    private EditText edtName;
    private EditText edtSalary;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private RadioButton radioOther;
    private EditText edtExp;
    private int dataId;
    private Spinner spinner;
    private String[] spinnerArray = {"Select Desc", "Associate", "Sr. Associate", "Manager", "CEO"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        mContext = this;
        edtName = findViewById(R.id.edtName);
        edtSalary = findViewById(R.id.edtSalary);
        edtExp = findViewById(R.id.edtExp);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioOther = findViewById(R.id.radioOthers);
        spinner = findViewById(R.id.spinner);
        buttonSave = findViewById(R.id.btnSave);


        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        if (getIntent() != null) {
            dataId = getIntent().getIntExtra("id", 0);

            readDetails(dataId);
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmptyValues();
            }
        });
    }

    private void readDetails(int empId) {
        SQLiteDatabase database = new SQLiteDBHelper(this).getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE id = '" + empId + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d(this.getClass().getSimpleName(), "readFromDB: " + cursor.getInt(0) + cursor.getString(2) + cursor.getColumnName(3) + cursor.getColumnName(4));
                edtName.setText(cursor.getString(1));
                edtSalary.setText(cursor.getString(3));
                edtExp.setText(cursor.getString(4));
                if (cursor.getString(2).equals("Male")) {
                    radioMale.setChecked(true);
                } else if (cursor.getString(2).equals("Female")) {
                    radioFemale.setChecked(true);
                } else if (cursor.getString(2).equals("Others")) {
                    radioOther.setChecked(true);
                }
                if (cursor.getString(5).equals("Associate")) {
                    spinner.setSelection(1);
                } else if (cursor.getString(5).equals("Sr. Associate")) {
                    spinner.setSelection(2);
                } else if (cursor.getString(5).equals("Manager")) {
                    spinner.setSelection(3);
                } else if (cursor.getString(5).equals("CEO")) {
                    spinner.setSelection(4);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
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
            if (radioMale.isChecked()) {
                saveDataIntoDb(dataId, edtName.getText().toString(), radioMale.getText().toString(), edtSalary.getText().toString(), spinner.getSelectedItem().toString(), edtExp.getText().toString());
            } else if (radioFemale.isChecked()) {
                saveDataIntoDb(dataId, edtName.getText().toString(), radioFemale.getText().toString(), edtSalary.getText().toString(), spinner.getSelectedItem().toString(), edtExp.getText().toString());
            } else if (radioOther.isChecked()) {
                saveDataIntoDb(dataId, edtName.getText().toString(), radioOther.getText().toString(), edtSalary.getText().toString(), spinner.getSelectedItem().toString(), edtExp.getText().toString());
            }
        }
    }

    private void saveDataIntoDb(int dataId, String name, String gender, String salary, String designation, String exp) {
        Log.d(this.getClass().getSimpleName(), "saveDataIntoDb: " + name + gender + salary + designation + exp);
        SQLiteDatabase database = new SQLiteDBHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_NAME, name);
        values.put(SQLiteDBHelper.COLUMN_GENDER, gender);
        values.put(SQLiteDBHelper.COLUMN_SALARY, salary);
        values.put(SQLiteDBHelper.COLUMN_EXP, exp);
        values.put(SQLiteDBHelper.COLUMN_DES, designation);

        database.update(SQLiteDBHelper.TABLE_NAME, values, "id=" + dataId, null);

        Toast.makeText(mContext, "Data Updated ", Toast.LENGTH_LONG).show();
        database.close();

        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    private void deleteDetails(int dataId) {
        SQLiteDatabase database = new SQLiteDBHelper(mContext).getWritableDatabase();
        database.delete(
                SQLiteDBHelper.TABLE_NAME,  // Where to delete
                "id=" + dataId,
                null);  // What to delete
        Toast.makeText(mContext, "Data Deleted ", Toast.LENGTH_LONG).show();
        database.close();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            deleteDetails(dataId);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
