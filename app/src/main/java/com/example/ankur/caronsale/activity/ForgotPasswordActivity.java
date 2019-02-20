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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.app.AppConfig;
import com.example.ankur.caronsale.app.AppController;
import com.example.ankur.caronsale.helper.SendMailTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();
    Toolbar toolbar;
    TextView txtToolbar;
    ImageView imgToolbarBack;
    EditText edtEmailForgotPassword,edtCode,edtNewPassword,edtConfirmNewPassword;
    Button btnForgotPassword,btnChangePassword;
    String randomCode = null;
    String emailAddress;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initToolBar();
        edtEmailForgotPassword = (EditText) findViewById(R.id.edtEmailForgotPassword);
        edtCode = (EditText) findViewById(R.id.edtCode);
        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = (EditText) findViewById(R.id.edtConfirmNewPassword);
        btnForgotPassword = (Button) findViewById(R.id.btnForgotPassword);
        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);

        btnForgotPassword.setOnClickListener(this);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Forgot Password");

        setSupportActionBar(toolbar);

        imgToolbarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View view) {
        emailAddress = edtEmailForgotPassword.getText().toString().trim();

        if(!emailAddress.isEmpty()){


            ForgotPasswordActivity random  = null;

            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

            randomCode = random.randomAlphaNumeric(10).toString();

           // Toast.makeText(this, citizenPassword, Toast.LENGTH_SHORT).show();

            //System.out.println(citizenPassword);

            //String citizenName = "Maunil";


            String fromEmail = "anshab2714@gmail.com";
            String fromPassword = "testUSER@123";
            String toEmail = emailAddress;
            String emailSubject ="Info";
            String emailBody = "Hello User"+" Your password is "+randomCode;

            new SendMailTask(ForgotPasswordActivity.this).execute(fromEmail,
                    fromPassword, toEmail, emailSubject, emailBody);
            Toast.makeText(this, "Code sent to your email Address ", Toast.LENGTH_SHORT).show();

            changePassword();
        }
    }

    private void changePassword() {
        edtCode.setVisibility(View.VISIBLE);
        edtNewPassword.setVisibility(View.VISIBLE);
        edtConfirmNewPassword.setVisibility(View.VISIBLE);
        edtEmailForgotPassword.setVisibility(View.GONE);
        btnForgotPassword.setVisibility(View.GONE);
        btnChangePassword.setVisibility(View.VISIBLE);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edtCode.getText().toString().trim();
                String password  = edtNewPassword.getText().toString().trim();
                String confirmPassword = edtConfirmNewPassword.getText().toString().trim();
                /*if(code.equals(randomCode)){
                    if(password.equals(confirmPassword)){*/
                        forgotPasswordAPI(emailAddress,password);
                /*    }
                }*/
            }
        });
    }

    private void forgotPasswordAPI(final String email, final String password) {

        String tag_string_req = "req_forgot";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.FORGOT_PASSWORD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
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
                        //db.addUser(name, email, uid, created_at, phone_number, street_address, postalCode);

                        /*objUser.setUserName(name);
                        objUser.setUserEmail(email);
                        objUser.setPhoneNumber(phoneNumber);
                        objUser.setStreet_address(street_address);
                        objUser.setPostalCode(postalCode);
                        objUser.setCreated_at(created_at);*/

                        Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_LONG).show();


                        // Launch login activity
                        Intent intent = new Intent(
                                ForgotPasswordActivity.this,
                                LoginActivity.class);
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
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        //strReq.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public static String randomAlphaNumeric(int count) {

        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }

        return builder.toString();

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
