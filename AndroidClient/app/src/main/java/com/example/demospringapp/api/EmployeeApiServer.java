package com.example.demospringapp.api;

import com.example.demospringapp.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeApiServer {
    @GET("employee")
    Call<List<Employee>> getAllEmployees();
    @GET("employee/{id}")
    Call<Employee> getEmployeeById(@Path("id") int id);
    @POST("employee")
    Call<Employee> saveEmployee(@Body Employee employee);
    @PUT("employee/{id}")
    Call<Employee> updateEmployee(@Path("id") int id, @Body Employee employee);
    @DELETE("employee/{id}")
    Call<Void> deleteEmployee(@Path("id") int id);
}
