package com.example.laboursapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.laboursapp.Adapters.ContractorSeeWorkAdapter;
import com.example.laboursapp.Adapters.LabourSearchWork;
import com.example.laboursapp.Classes.Work;
import com.example.laboursapp.databinding.ActivityLabourSearchWorkBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LabourSearchWorkActivity extends AppCompatActivity {
    ActivityLabourSearchWorkBinding binding;
    private ArrayList<Work> workArrayList;
    private String id="";
    private LabourSearchWork recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLabourSearchWorkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            new ContractorSeeWork().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerViewAdapter = new LabourSearchWork(LabourSearchWorkActivity.this,
                workArrayList);
        binding.recyclerView.setAdapter(recyclerViewAdapter);

    }

    private class ContractorSeeWork extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/labour_search_work/" + id;
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
                workArrayList = new ArrayList<>();

                JSONArray jsonarray = new JSONArray(resultJson);

                for (int i=0;i<resultJson.length();i++){
                    JSONObject jsonobj = jsonarray.getJSONObject(i);
                    workArrayList.add(new Work(jsonobj.getString("job_desc"),
                            jsonobj.getString("job_type"),
                            jsonobj.getInt("noofdays"),
                            jsonobj.getInt("noofemp"),
                            jsonobj.getInt("noofemphired"),
                            jsonobj.getDouble("salary"),
                            jsonobj.getInt("work_id")));
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