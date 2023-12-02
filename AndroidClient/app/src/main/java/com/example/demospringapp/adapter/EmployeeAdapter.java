package com.example.demospringapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demospringapp.R;
import com.example.demospringapp.UpdateEmployeeActivity;
import com.example.demospringapp.api.EmployeeApiServer;
import com.example.demospringapp.model.Employee;
import com.example.demospringapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private Context context;
    private List<Employee> list;

    public EmployeeAdapter(Context context, List<Employee> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Employee> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_employee,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.ViewHolder holder, int position) {
        Employee model=list.get(position);
        holder.id.setText(String.valueOf(model.getId()));
        holder.firstName.setText(model.getFirstName());
        holder.lastName.setText(model.getLastName());
        holder.email.setText(model.getEmail());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int employeeId=model.getId();
                String firstName=model.getFirstName();
                String lastName=model.getLastName();
                String email=model.getEmail();

                Intent intent=new Intent(context, UpdateEmployeeActivity.class);

                intent.putExtra("employeeId",employeeId);
                intent.putExtra("firstName",firstName);
                intent.putExtra("lastName",lastName);
                intent.putExtra("email",email);

                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeApiServer apiServer= RetrofitClient.getInstance().create(EmployeeApiServer.class);
                Call call=apiServer.deleteEmployee(model.getId());
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful()){
                            removeItem(holder.getAdapterPosition());
                            Toast.makeText(context, "Delete Employee Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.e("Api error", "delete errorr",t);
                        Toast.makeText(context, "delete employee falied", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void removeItem(int adapterPosition) {
        list.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition,getItemCount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id,firstName,lastName,email;
        private Button update, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.employee_Id);
            firstName=itemView.findViewById(R.id.firstName);
            lastName=itemView.findViewById(R.id.lastName);
            email=itemView.findViewById(R.id.email);
            update=itemView.findViewById(R.id.update);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
