package com.example.administrator.sqlitedbapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText etEmpName;
    private RadioButton rdMale, rdFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEmployee = (Button) findViewById(R.id.btnEmployee);
        final ListView lvEmployees = (ListView) findViewById(R.id.lvEmployees);

        etEmpName = (EditText) findViewById(R.id.etName);
        rdMale = (RadioButton) findViewById(R.id.rdMale);
        rdFemale = (RadioButton) findViewById(R.id.rdFemale);

        //lvEmployees.setBackgroundColor(Color.BLACK);

        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationDAL appDAL = new ApplicationDAL(getApplicationContext());

                appDAL.openDBConnection();

                long deptId = appDAL.createDepartment("IT Department");

                appDAL.createEmployee("Hasan", "Male", deptId);

                deptId = appDAL.createDepartment("Marketing Department");

                appDAL.createEmployee(etEmpName.getText().toString(), rdMale.isChecked() ? "Male" : "Female", deptId);

                for(Department department : appDAL.fetchAllDepartments()){
                    Log.d("DBApp", department.toString());
                }

                List<Employee> employees = appDAL.fetchAllEmployees();

                for(Employee employee : employees){
                    Log.d("DBApp", employee.toString());
                }

                //ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, employees);

                //lvEmployees.setAdapter(arrayAdapter);

                //SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, appDAL.fetchEmployeesCursor(), new String[]{DBClass.COL_EMP_GENDER, DBClass.COL_EMP_NAME}, new int[] {android.R.id.text1}, CursorAdapter.FLAG_AUTO_REQUERY);

                //lvEmployees.setAdapter(cursorAdapter);

                SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.datarow, appDAL.fetchEmployeesCursor(), new String[]{DBClass.COL_EMP_ID, DBClass.COL_EMP_NAME, DBClass.COL_EMP_GENDER}, new int[] {R.id.tvEmpId, R.id.tvEmpName, R.id.tvEmpGender}, CursorAdapter.NO_SELECTION);

                lvEmployees.setAdapter(cursorAdapter);

                appDAL.closeDBConnection();
            }
        });

    }

}
