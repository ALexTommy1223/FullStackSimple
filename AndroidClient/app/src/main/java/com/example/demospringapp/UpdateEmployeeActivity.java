package com.example.demospringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demospringapp.api.EmployeeApiServer;
import com.example.demospringapp.model.Employee;
import com.example.demospringapp.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployeeActivity extends AppCompatActivity {
    EditText updateFirstName, updateLastName, updateEmail;
    Button update , cancel;

    EmployeeApiServer apiServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        updateFirstName=findViewById(R.id.updatefirstname);
        updateLastName=findViewById(R.id.updatelastname);
        updateEmail=findViewById(R.id.updateemail);
        update=findViewById(R.id.update_save);
        cancel=findViewById(R.id.update_cancel);

        int employeeId=getIntent().getIntExtra("employeeId",-1);
        String firstName=getIntent().getStringExtra("firstName");
        String lastName=getIntent().getStringExtra("lastName");
        String email=getIntent().getStringExtra("email");

        updateFirstName.setText(firstName);
        updateLastName.setText(lastName);
        updateEmail.setText(email);

        apiServer= RetrofitClient.getInstance().create(EmployeeApiServer.class);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName=updateFirstName.getText().toString().trim();
                String lastName=updateLastName.getText().toString().trim();
                String email=updateEmail.getText().toString().trim();

                if(firstName.isEmpty()||lastName.isEmpty()||email.isEmpty()){
                    return;
                }
                Employee newEmployee=new Employee();
                newEmployee.setFirstName(firstName);
                newEmployee.setLastName(lastName);
                newEmployee.setEmail(email);

                updateEmployee(employeeId,newEmployee);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateEmployeeActivity.this,MainActivity.class));
            }
        });
    }

    private void updateEmployee(int employeeId, Employee newEmployee) {
        Call<Employee> call=apiServer.updateEmployee(employeeId, newEmployee);

        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UpdateEmployeeActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateEmployeeActivity.this,MainActivity.class));
                }
                else{
                    Toast.makeText(UpdateEmployeeActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.e("Api error","error updating employee",t);
                Toast.makeText(UpdateEmployeeActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}