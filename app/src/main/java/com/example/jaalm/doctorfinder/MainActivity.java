package com.example.jaalm.doctorfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public final class MainActivity extends AppCompatActivity {
    private static final String TAG = "Doctor Search:Main";
    private static RequestQueue requestQueue;

    /**
     * Run when the screen pops up.
     *
     * @param stateSaved
     */
    @Override
    protected void onCreate(final Bundle stateSaved) {
        super.onCreate(stateSaved);
        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);
        // Capture our button from layout
        Button button = (Button) findViewById(R.id.corky);
        //Register the onClick listener with the implementation above
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /**
     * Run when this activity no longer runs.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    void startAPICall(final String number, final String taxonomy, final String postalCode,
                      final String displayNumber) {
        //https://github.com/jaimea99/DoctorSearch/upload
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    1"https://npiregistry.cms.hhs.gov/api/?number=" + number
                    + "&taxonomy_description=" + taxonomy + "&postal_code="
                    + postalCode + "&limit" + displayNumber);
            null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject responding) {
                            apiCallDone(responding);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError givenError) {
                    Log.e(TAG, givenError.toString());
                }
            });
            jsonObjectRequest.setShouldCache(false);
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the API response.
     *
     * @param given response from API.
     */
    void apiCallDone(final JSONObject given) {
        try {
            Log.d(TAG, given.toString(2));
        } catch (JSONException lost) {
        }
    }
}