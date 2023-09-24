package com.example.laboursapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
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

import com.example.laboursapp.Classes.Work;
import com.example.laboursapp.ContractorSeeWorkActivity;
import com.example.laboursapp.DBHelper;
import com.example.laboursapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContractorSeeWorkAdapter extends RecyclerView.Adapter<ContractorSeeWorkAdapter.ViewHolder>{
    private Context context;
    private List<Work> data;
    private String id;
    private int work_id;

    public ContractorSeeWorkAdapter(Context context, List<Work> data) {
        this.context = context;
        this.data = data;
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
    }

    @NonNull
    @Override
    public ContractorSeeWorkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contractor_see_work,
                parent,false);
        return new ContractorSeeWorkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractorSeeWorkAdapter.ViewHolder holder, int position) {
        Work x = data.get(position);
        holder.job_desc.setText(x.getJob_desc());
        holder.job_type.setText(x.getJob_type());
        holder.noofdays.setText("Days : "+x.getNoofdays());
        holder.salary.setText("Salary : "+x.getSalary());
        holder.noofemp.setText("No of employees required : "+x.getNoofemp());
        holder.noofemphired.setText("No of employees hired : "+x.getNoofemphired());
        
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                work_id=x.getWork_id();
                try {
                    new ContractorDeleteWork().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i=0;i<data.size();i++){
                    if (data.get(i).getWork_id()==work_id){
                        data.remove(i);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView job_desc, job_type, noofdays, noofemp, noofemphired, salary;
        public Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            job_desc = itemView.findViewById(R.id.see_job_desc);
            job_type = itemView.findViewById(R.id.see_job_type);
            noofdays = itemView.findViewById(R.id.see_noofdays);
            noofemp = itemView.findViewById(R.id.see_employees_required);
            noofemphired = itemView.findViewById(R.id.see_employees_hired);
            salary = itemView.findViewById(R.id.see_salary);
            delete = itemView.findViewById(R.id.see_delete);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Work x = data.get(position);

//            Intent intent = new Intent(context, CopyUpdateCode.class);
//            intent.putExtra("code_data",name);
//            intent.putExtra("sub_name_data", password.getSubname());
//            intent.putExtra("codename_data", password.getCodename());
//            context.startActivity(intent);
        }
    }

    private class ContractorDeleteWork extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/contractor_delete_work/" + id+"/"+
                        work_id;
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

                JSONArray jsonarray = new JSONArray(resultJson);

                for (int i=0;i<resultJson.length();i++){
                    JSONObject jsonobj = jsonarray.getJSONObject(i);
                }


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


