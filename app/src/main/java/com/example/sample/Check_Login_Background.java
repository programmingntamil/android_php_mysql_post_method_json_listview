package com.example.sample;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

public class Check_Login_Background extends AsyncTask
{
    public Context context;
    public TextView status;
    public ListView listview;
    public Check_Login_Background(Context context, TextView status, ListView listView)
    {
        this.context = context;
        this.status = status;
        this.listview = listView;
    }


    protected void onPreExecute(){
    }

    @Override
    protected Object doInBackground(Object[] objects)
    {
        String username = ((EditText)objects[0]).getText().toString();
        String password = ((EditText)objects[1]).getText().toString();
        try {
            //URL url = new URL("http://localhost/dashboard/android_api/login_check.php?username=" +username + "&&password=" + password + "");

            String url_link = "http://192.168.43.22/dashboard/android_api/login_check.php";

            // www.000webhost.com


            String user_pass = URLEncoder.encode("username","UTF-8") +"="+URLEncoder.encode(username,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");


            URL url = new URL(url_link);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(user_pass);
            writer.flush();


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            StringBuffer sb = new StringBuffer("");
            String line = "";

            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }

            in.close();
            return sb.toString();
        }
        catch(Exception exception)
        {

            return "Exception "+exception.toString();
        }


    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        String total_data = "";



        try
        {
            JSONObject json_object_courier = new JSONObject(o.toString());

            Iterator<String> iterator = json_object_courier.keys();
            ArrayList<String> al_courier = new ArrayList<String>();

            while(iterator.hasNext())
            {
                String key = iterator.next();
                Object courier_object = json_object_courier.get(key);
                //total_data += key+" => "+courier_object.toString()+" * ";
                al_courier.add(key+" => "+courier_object.toString());
            }


            String[] string_array_courier = new String[al_courier.size()];

            for(int i=0;i<al_courier.size();i++)
            {
                string_array_courier[i] = al_courier.get(i);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,string_array_courier);
            listview.setAdapter(adapter);

            //this.status.setText("Values "+total_data);
        }
        catch(Exception exception)
        {
            this.status.setText("Exception "+exception.toString());
        }


    }
}
