package com.example.jaalm.doctorfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
     * @param stateSaved
     */
    @Override
    protected void onCreate(final Bundle stateSaved) {
        super.onCreate(stateSaved);
        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);
        // Capture npi input.
        final EditText npiInput = (EditText) findViewById(R.id.NPIinput);
        final EditText doctorType = (EditText) findViewById(R.id.doctorType);
        final EditText postalCode = (EditText) findViewById(R.id.postalCode);
        final TextView doctorDisplay = (TextView) findViewById(R.id.doctorDisplay);
        // Capture our button from layout
        Button button = (Button) findViewById(R.id.corky);
        //Register the onClick listener with the implementation above
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAPICall(npiInput.getText().toString(), doctorType.getText().toString(),
                        postalCode.getText().toString());
                doctorDisplay.setText(newText);
                System.out.println(newText);
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

    String startAPICall(final String number, final String taxonomy, final String postalCode) {
        //https://github.com/jaimea99/DoctorSearch/upload
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://npiregistry.cms.hhs.gov/api/?number="
                    + "&taxonomy_description=" + taxonomy + "&postal_code="
                    + postalCode + "&limit=1",null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject responding) {
                            apiCallDone(responding);
                            System.out.println(responding.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError givenError) {
                    Log.e(TAG, givenError.toString());
                }
            });
            jsonObjectRequest.setShouldCache(false);
            requestQueue.add(jsonObjectRequest);
            //System.out.println(jsonObjectRequest.toString());
            return jsonObjectRequest.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Handle the API response.
     *
     * @param given response from API.
     */
    String newText;
    void apiCallDone(final JSONObject given) {
        try {
            newText = given.getJSONArray("results").get(0).toString();
            Log.d(TAG, given.toString(2));
            //eSystem.out.println(given.toString());
        } catch (JSONException lost) {
        }
    }
}
