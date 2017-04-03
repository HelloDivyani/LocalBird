package com.example.rssongira.localbird;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RSSongira on 4/3/2017.
 */
public class Details_Main extends AppCompatActivity
{
    private String TAG = MainActivity.class.getSimpleName();
        private String Id;
    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placesdetails_main);
        Id = getIntent().getExtras().getString("PLACE_ID");

        url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+Id+"&key=AIzaSyB5J0DVdARLzuVMVp7pQlSMYeqtbDAaUuo";

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Details_Main.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Toast.makeText(MainActivity.this,"Inside do ",Toast.LENGTH_LONG).show();
            HttpHandler_First sh = new HttpHandler_First();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            //Toast.makeText(MainActivity.this,"JSONSTR",Toast.LENGTH_LONG).show();
            if (jsonStr != null) {

                try {
                    // Toast.makeText(MainActivity.this,"Inside TRY STARt",Toast.LENGTH_SHORT).show();
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    //Toast.makeText(MainActivity.this,"object cfreated success",Toast.LENGTH_LONG).show();
                    // Getting JSON Array node
                    JSONObject contacts = null;
                    try
                    {
                        contacts = jsonObj.getJSONObject("result");

                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                    // looping through All Contacts
                    //for (int i = 0; i < contacts.length(); i++) {
                    //JSONObject c = contacts.getJSONObject();

                    String name = contacts.getString("name");
                    String formatted_address = contacts.getString("formatted_address");
                    //String email = c.getString("email");
                    // String address = c.getString("address");
                    //String gender = c.getString("gender");

                    // Phone node is JSON Object
                       /* JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");*/

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("name", name);
                    contact.put("formatted_address", formatted_address);

                    //contact.put("email", email);
                    //contact.put("mobile", mobile);

                    // adding contact to contact list
                    contactList.add(contact);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Details_Main.this, contactList,R.layout.placesdetails_list, new String[]{"name","formatted_address"
            }, new int[]{R.id.name, R.id.place_id});

            lv.setAdapter(adapter);
        }

    }
}
