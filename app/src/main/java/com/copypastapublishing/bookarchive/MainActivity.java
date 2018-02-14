package com.copypastapublishing.bookarchive;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private static final String URL_DATA = "http://babelli-gutenberg-copypasta.appspot.com/appsearch/";
    private static final Integer MY_SOCKET_TIMEOUT_MS =16000;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<DevelopersList> developersLists;
    private Context mContext;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting up recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        developersLists = new ArrayList<>();
        //calling the loadUrl method
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchbutton);
        EditText ourSearchView= (EditText) findViewById(R.id.searchView);


        searchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText sv = (EditText) findViewById(R.id.searchView);
                String searchTerm=sv.getText().toString();
                loadUrlData2(searchTerm);

            }


        });
    }
    //defining the loadUrl method ( calls the api for the json data)
    private void loadUrlData(String searchterm) {
        developersLists.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonArrayRequest myRequest = new JsonArrayRequest(Request.Method.GET,
                URL_DATA+searchterm, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        try{
                            for (int i = 0; i < response.length(); i++) { // Walk through the Array.
                                JSONObject obj = response.getJSONObject(i);
                                DevelopersList developers = new DevelopersList(obj.getString("title"), obj.getString("author"), obj.getString("textlink"), obj.getString("epublink"), obj.getString("bookid"));
                                developersLists.add(developers);
                            }

                            adapter = new DevelopersAdapter(developersLists, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//This indicates that the reuest has either time out or there is no connection
                    Toast.makeText(MainActivity.this, "No Connection Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (error instanceof AuthFailureError) {
// Error indicating that there was an Authentication Failure while performing the request
                    Toast.makeText(MainActivity.this, "Auth Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (error instanceof ServerError) {
//Indicates that the server responded with a error response
                    Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (error instanceof NetworkError) {
//Indicates that there was network error while performing the request
                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (error instanceof ParseError) {
// Indicates that the server response could not be parsed
                    Toast.makeText(MainActivity.this, "Parse Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }){ @Override
        public String getBodyContentType() {
            return "application/x-www-form-urlencoded; charset=UTF-8";
        }};;

        myRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        /**StringRequest stringRequest = new StringRequest(Request.Method.GET,
         URL_DATA, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
        progressDialog.dismiss();
        try {
        JSONArray arr = new JSONArray(response);
        for (int i = 0; i < arr.length(); i++) { // Walk through the Array.
        JSONObject obj = arr.getJSONObject(i);
        DevelopersList developers = new DevelopersList(obj.getString("title"));
        developersLists.add(developers);
        }

        adapter = new DevelopersAdapter(developersLists, getApplicationContext());
        recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
        e.printStackTrace();
        }
        }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
        //This indicates that the reuest has either time out or there is no connection
        Toast.makeText(MainActivity.this, "No Connection Error", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        } else if (error instanceof AuthFailureError) {
        // Error indicating that there was an Authentication Failure while performing the request
        Toast.makeText(MainActivity.this, "Auth Error", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        } else if (error instanceof ServerError) {
        //Indicates that the server responded with a error response
        Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        } else if (error instanceof NetworkError) {
        //Indicates that there was network error while performing the request
        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        } else if (error instanceof ParseError) {
        // Indicates that the server response could not be parsed
        Toast.makeText(MainActivity.this, "Parse Error", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        }

        }
        }){ @Override
         public String getBodyContentType() {
         return "application/x-www-form-urlencoded; charset=UTF-8";
         }};

         //defining the requestQueue
         RequestQueue requestQueue = Volley.newRequestQueue(this);
         requestQueue.add(stringRequest);
         }**/
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myRequest);}

    private void loadUrlData2(String searchterm){
        developersLists.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

            mContext = getApplicationContext();
            mActivity = MainActivity.this;

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        String stSplit = searchterm.replace(" ","%20");

    // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET,
            URL_DATA+stSplit,
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // Do something with response
                    //mTextView.setText(response.toString());

                    // Process the JSON
                    try{
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                            // Get current json object
                            JSONObject student = response.getJSONObject(i);

                            // Get the current student (json object) data
                            String title = student.getString("title");
                            String author= student.getString("author");
                            String textlink = student.getString("textlink");
                            String epublink = student.getString("epublink");
                            String bookid = student.getString("bookid");


                            // Display the formatted json data in text view
                            DevelopersList developers = new DevelopersList(title, author, textlink, epublink, bookid);
                            developersLists.add(developers);
                        }

                        adapter = new DevelopersAdapter(developersLists, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    // Do something when error occurred
                    Toast.makeText(MainActivity.this, "Search term not found.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
    );

// Add JsonArrayRequest to the RequestQueue
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonArrayRequest);
                        }
}

