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
import retrofit2.Retrofit;

public class AddEmployeeActivity extends AppCompatActivity {
    EditText firstName,lastName,email;
    Button save, cancel;
    EmployeeApiServer apiServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        firstName=findViewById(R.id.addfirstname);
        lastName=findViewById(R.id.addlastname);
        email=findViewById(R.id.addemail);

        save=findViewById(R.id.save);
        cancel=findViewById(R.id.cancel);
        apiServer= RetrofitClient.getInstance().create(EmployeeApiServer.class);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first=firstName.getText().toString().trim();
                String last=lastName.getText().toString().trim();
                String emailUser=email.getText().toString().trim();

                if(first.isEmpty()||last.isEmpty()||emailUser.isEmpty()){
                    return;
                }
                Employee newEmployee=new Employee();
                newEmployee.setFirstName(first);
                newEmployee.setLastName(last);
                newEmployee.setEmail(emailUser);

                saveEmployee(newEmployee);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEmployeeActivity.this,MainActivity.class));
            }
        });
    }

    private void saveEmployee(Employee newEmployee) {
        Call<Employee> call=apiServer.saveEmployee(newEmployee);
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AddEmployeeActivity.this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddEmployeeActivity.this,MainActivity.class));
                }
                else{
                    Toast.makeText(AddEmployeeActivity.this, "Error saving", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.e("Api error","error saving employee",t);
                Toast.makeText(AddEmployeeActivity.this, "Error saving employee", Toast.LENGTH_SHORT).show();
            }
        });
    }
}