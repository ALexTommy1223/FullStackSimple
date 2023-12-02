package com.example.demospringapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.demospringapp.adapter.EmployeeAdapter;
import com.example.demospringapp.api.EmployeeApiServer;
import com.example.demospringapp.model.Employee;
import com.example.demospringapp.retrofit.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    List<Employee> listEmployee;
    FloatingActionButton addEmployee;
    EmployeeApiServer apiServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        addEmployee=findViewById(R.id.btnaddemployee);
        listEmployee=new ArrayList<>();
        adapter=new EmployeeAdapter(this,listEmployee);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        apiServer= RetrofitClient.getInstance().create(EmployeeApiServer.class);
        Call<List<Employee>> call=apiServer.getAllEmployees();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    List<Employee> employees = response.body();
                    if (employees != null) {
                        adapter.setList(employees);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Empty response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.e("API Error", "Error loading data", t);
                Toast.makeText(MainActivity.this, "Error load data", Toast.LENGTH_SHORT).show();
            }

        });
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddEmployeeActivity.class));
            }
        });
    }
}