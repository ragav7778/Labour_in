package com.example.laboursapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.laboursapp.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private String id, password, select, selection;
    private int success=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        binding.reasonDropDown.setAdapter(new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, new String[] {"Contractor",
                "Labourer"}));
        binding.reasonDropDown.setListSelection(0);

        binding.gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });



        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = binding.edituserid.getText().toString();
                password = binding.editpassword.getText().toString();
                selection = binding.reasonDropDown.getText().toString();
                Log.e("Selection", ""+selection);
                if (selection.equals("Contractor")){
                    select="contractor_login";
                }
                else{
                    select="labour_login";
                }
                try {
                    new Login().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (success==1){
                    db.deleteData();
                    db.insertData(id, password, selection);
                    if (selection.equals("Contractor")){
                        Intent intent = new Intent(getApplicationContext(),
                                ContractorDashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(),
                                LabourDashBoardActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
                else{
                    binding.edituserid.setError("Wrong credentials");
                }
            }
        });


    }

    private class Login extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/"+select;
                URL url = new URL(site_url_json);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("id="+id+"&password="+password);
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

                JSONObject jsonObject = new JSONObject(resultJson);
                success = jsonObject.getInt("message");


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