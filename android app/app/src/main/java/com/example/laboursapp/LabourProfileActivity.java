package com.example.laboursapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.laboursapp.databinding.ActivityLabourProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LabourProfileActivity extends AppCompatActivity {
    ActivityLabourProfileBinding binding;
    private String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLabourProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);

        try {
            new LabourProfile().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class LabourProfile extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/labour_profile/" + id;
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
                JSONObject jsonObject = new JSONObject(resultJson);

                String name = jsonObject.getString("name");
                String contact = jsonObject.getString("contact");
                String mailid = jsonObject.getString("mailid");
                int age = jsonObject.getInt("age");
                double height = jsonObject.getDouble("height");
                double weight = jsonObject.getDouble("weight");
                String gender = jsonObject.getString("gender");

                binding.name.setText(name);
                binding.age.setText(String.valueOf(age));
                binding.mailid.setText(mailid);
                binding.contact.setText(contact);
                binding.height.setText(String.valueOf(height));
                binding.weight.setText(String.valueOf(weight));
                binding.gender.setText(gender);


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