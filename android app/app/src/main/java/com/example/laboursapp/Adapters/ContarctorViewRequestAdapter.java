package com.example.laboursapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laboursapp.Classes.Request;
import com.example.laboursapp.Classes.Work;
import com.example.laboursapp.DBHelper;
import com.example.laboursapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContarctorViewRequestAdapter extends RecyclerView.Adapter<ContarctorViewRequestAdapter.ViewHolder>{
    private Context context;
    private List<Request> data;
    private String email;
    private int request_id, accepted;
    
    public ContarctorViewRequestAdapter(Context context, List<Request> data) {
            this.context = context;
            this.data = data;
    }
    
    @NonNull
    @Override
    public ContarctorViewRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contractor_view_requests,
            parent,false);
            return new ContarctorViewRequestAdapter.ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ContarctorViewRequestAdapter.ViewHolder holder, int position) {
            Request x = data.get(position);
            holder.job_desc.setText(x.getJob_desc());
            holder.job_type.setText(x.getJob_type());
            holder.noofdays.setText("Days : "+x.getNoofdays());
            holder.salary.setText("Salary : "+x.getSalary());
            holder.noofemp.setText("No of employees required : "+x.getNoofemp());
            holder.noofemphired.setText("No of employees hired : "+x.getNoofemphired());
            holder.name.setText("Name : "+x.getName());
            holder.age.setText("Age : "+x.getAge());
            holder.height.setText("Height : "+x.getHeight());
            holder.weight.setText("Weight : "+x.getWeight());
            
            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    request_id=x.getRequest_id();
                    email=x.getEmail();
                    accepted=1;
                    try {
                        new ContractorAcceptRequest().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<data.size();i++){
                        if (data.get(i).getRequest_id()==request_id){
                            data.remove(i);
                        }
                    }
                    Toast.makeText(context.getApplicationContext(), "Accepted", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
            
            holder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    request_id=x.getRequest_id();
                    email=x.getEmail();
                    accepted=0;
                    try {
                        new ContractorAcceptRequest().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i=0;i<data.size();i++){
                        if (data.get(i).getRequest_id()==request_id){
                            data.remove(i);
                        }
                    }
                    Toast.makeText(context.getApplicationContext(), "Rejected", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
    
     
    }
    
    @Override
    public int getItemCount() {
            return data.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    
        public TextView job_desc, job_type, noofdays, noofemp, noofemphired, salary, name, age , height,weight;
        public Button accept, reject;
    
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    
            itemView.setOnClickListener(this);
    
            job_desc = itemView.findViewById(R.id.see_req_job_desc);
            job_type = itemView.findViewById(R.id.see_req_job_type);
            noofdays = itemView.findViewById(R.id.see_req_noofdays);
            noofemp = itemView.findViewById(R.id.see_req_emp_required);
            noofemphired = itemView.findViewById(R.id.see_req_employees_hired);
            salary = itemView.findViewById(R.id.see_req_salary);
            name = itemView.findViewById(R.id.see_req_name);
            age = itemView.findViewById(R.id.see_req_age);
            height = itemView.findViewById(R.id.see_req_height);
            weight = itemView.findViewById(R.id.see_req_weight);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
        }
    
        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Request x = data.get(position);
    
    //            Intent intent = new Intent(context, CopyUpdateCode.class);
    //            intent.putExtra("code_data",name);
    //            intent.putExtra("sub_name_data", password.getSubname());
    //            intent.putExtra("codename_data", password.getCodename());
    //            context.startActivity(intent);
        }
    }

    private class ContractorAcceptRequest extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/contractor_accept_request/" + email
                        +"/"+ request_id+"/"+accepted;
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }
    }
}

