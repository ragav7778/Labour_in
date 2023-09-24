package com.example.laboursapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.laboursapp.databinding.ActivityContractorProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ContractorProfileActivity extends AppCompatActivity {
    ActivityContractorProfileBinding binding;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContractorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getData();
        cursor.moveToNext();
        id = cursor.getString(0);
        new ContractorProfile().execute();
    }

    private class ContractorProfile extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            try {
                String site_url_json = "http://10.0.2.2:8000/api/contractor_profile/" + id;
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

            try {
//                JSONArray jsonarray = new JSONArray(strJson);
//                JSONObject jsonobj = jsonarray.getJSONObject(0);

                JSONObject jsonObject = new JSONObject(strJson);

                String name = jsonObject.getString("name");
                String contact = jsonObject.getString("contact");
                String mailid = jsonObject.getString("mailid");
                String company = jsonObject.getString("company");


                binding.name.setText(name);
                binding.company.setText(company);
                binding.mailid.setText(mailid);
                binding.contact.setText(contact);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}