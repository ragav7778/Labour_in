package com.example.laboursapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.laboursapp.databinding.ActivityContractorAddWorkBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ContractorAddWorkActivity extends AppCompatActivity {
    ActivityContractorAddWorkBinding binding;
    private String[] typesOfWork;
    private String job_desc, typeOfWork, id="";
    private int noOfDays, noOfEmp;
    private Double salary;
    private boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContractorAddWorkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);

        if (!check){
            try {
                new TypeOfWork().execute().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                Log.d("Error", e.toString());
            }
            check=true;
        }

//        binding.reasonDropDown.setAdapter(new ArrayAdapter(getApplicationContext(),
//                android.R.layout.simple_spinner_dropdown_item, new String[] {"Plumber", "Carpenter"}));
//        binding.reasonDropDown.setText(typesOfWork[0]);

        binding.reasonDropDown.setAdapter(new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, typesOfWork));
//        binding.reasonDropDown.setText("Plumbing");

        binding.addwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                job_desc = binding.editjobdesc.getText().toString();
                typeOfWork = binding.reasonDropDown.getText().toString();
                noOfDays = Integer.parseInt(binding.editnoofdays.getText().toString());
                noOfEmp = Integer.parseInt(binding.editnoofemp.getText().toString());
                salary = Double.parseDouble(binding.editsalary.getText().toString());
                try {
                    new AddWork().execute().get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private class TypeOfWork extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/get_typesofwork";
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
                try {
                    JSONArray jsonarray = new JSONArray(resultJson);
                    typesOfWork = new String[jsonarray.length()];
//                JSONObject jsonobj = jsonarray.getJSONObject(0);

                    for (int i=0;i<jsonarray.length();i++){
                        typesOfWork[i]=jsonarray.getJSONObject(i).getString("work");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error", e.toString());
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

    private class AddWork extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/add_work";
                URL url = new URL(site_url_json);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("id="+id+"&job_desc="+job_desc+"&work="+typeOfWork+"&noofdays="+noOfDays+
                        "&noofemp="+noOfEmp+"&salary="+salary);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

//                JSONObject jsonObject = new JSONObject(resultJson);


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