package com.example.laboursapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.laboursapp.databinding.ActivityRegisterBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private String name, contact, email, password, company, gender;
    private int age;
    private double height, weight;
    private boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        binding.reasonDropDown.setAdapter(new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, new String[] {"Contractor",
                "Labourer"}));
        binding.reasonDropDown.setListSelection(0);

        binding.gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.reasonDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    binding.contractor.setVisibility(View.VISIBLE);
                    binding.labourer.setVisibility(View.GONE);
                }
                else{
                    binding.contractor.setVisibility(View.GONE);
                    binding.labourer.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.gender.check(R.id.male);
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = binding.reasonDropDown.getText().toString();
                name = binding.editname.getText().toString();
                contact = binding.editcontact.getText().toString();
                email = binding.edituserid.getText().toString();
                password = binding.editpassword.getText().toString();
                if (x.equals("Contractor")){
                    company = binding.editcompany.getText().toString();
                    try {
                        new ContractorRegister().execute().get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    int z = binding.gender.getCheckedRadioButtonId();
                    boolean z1 = R.id.male==z;
                    if (z1){
                        gender="male";
                    }
                    else{
                        gender="female";
                    }
                    age = Integer.parseInt(binding.editage.getText().toString());
                    height = Double.parseDouble(binding.editheight.getText().toString());
                    weight = Double.parseDouble(binding.editweight.getText().toString());
                    try {
                        new LabourerRegister().execute().get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private class ContractorRegister extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/contractor_register";
                URL url = new URL(site_url_json);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("name=" + name + "&password=" + password + "&contact=" + contact + "&email=" +
                        email + "&company=" + company);
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }
    }


    private class LabourerRegister extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/labourer_register";
                URL url = new URL(site_url_json);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("name=" + name + "&password=" + password + "&contact=" + contact
                        + "&email=" + email + "&age=" + age + "&height=" + height + "&weight=" + weight +
                        "&gender=" + gender);
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