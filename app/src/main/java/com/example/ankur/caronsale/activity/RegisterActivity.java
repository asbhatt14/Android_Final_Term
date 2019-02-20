package com.example.ankur.caronsale.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankur.caronsale.Model.REGISTERD_USERS;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.app.AppConfig;
import com.example.ankur.caronsale.app.AppController;
import com.example.ankur.caronsale.helper.SQLiteHandler;
import com.example.ankur.caronsale.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    Toolbar toolbar;
    TextView txtToolbar;
    ImageView imgToolbarBack;
    EditText edtFirstName,edtLastName,edtStreetAddress,edtPostalCode,edtPhoneNumber,edtSignUpEmail;
    EditText edtSignUpPassword,edtSignUpConfirmPassword;
    Button btnRegister;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    REGISTERD_USERS objUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolBar();
        objUser = new REGISTERD_USERS();

        edtFirstName = (EditText) findViewById(R.id.edtFirstName);
        edtLastName = (EditText) findViewById(R.id.edtLastName);
        edtStreetAddress = (EditText) findViewById(R.id.edtStreetAddress);
        edtPostalCode = (EditText) findViewById(R.id.edtPostalCode);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtSignUpEmail = (EditText) findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = (EditText) findViewById(R.id.edtSignUpPassword);
        edtSignUpConfirmPassword = (EditText) findViewById(R.id.edtSignUpConfirmPassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    HomeActivity.class);
            startActivity(intent);
            finish();
        }
        btnRegister.setOnClickListener(this);

    }
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Sign Up");

        setSupportActionBar(toolbar);

        imgToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                String name = firstName + " "+lastName;

                String email = edtSignUpEmail.getText().toString().trim();
                String password = edtSignUpPassword.getText().toString().trim();
                String street_address = edtStreetAddress.getText().toString().trim();
                String postalCode = edtPostalCode.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String confirmPassword = edtSignUpConfirmPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !street_address.isEmpty()
                        && !postalCode.isEmpty() && !phoneNumber.isEmpty()) {
                    if(password.equals(confirmPassword)){
                        registerUser(name, email, password,phoneNumber,street_address,postalCode);
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Both Password must same", Toast.LENGTH_LONG)
                                .show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password, final String phoneNumber, final String street_address, final String postalCode) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String phone_number = user.getString("phone_number");
                        String street_address = user.getString("street_address");
                        String postalCode = user.getString("postal_code");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table

                        /*String name, String email, String uid, String created_at, String phone_number,
                                String street_address, String postalCode)*/
                        db.addUser(name, email, uid, created_at,phone_number,street_address,postalCode);

                        objUser.setUserName(name);
                        objUser.setUserEmail(email);
                        objUser.setPhoneNumber(phoneNumber);
                        objUser.setStreet_address(street_address);
                        objUser.setPostalCode(postalCode);
                        objUser.setCreated_at(created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        session.setLogin(true);

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("phone_number", phoneNumber);
                params.put("street_address", street_address);
                params.put("postal_code", postalCode);

                return params;
            }

        };

        // Adding request to request queue
        //strReq.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

