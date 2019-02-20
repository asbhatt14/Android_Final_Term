package com.example.ankur.caronsale.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.activity.HomeActivity;
import com.example.ankur.caronsale.helper.SQLiteHandler;
import com.example.ankur.caronsale.helper.SessionManager;

import java.util.HashMap;

public class UserProfileFragment extends Fragment implements View.OnClickListener {

    EditText edtFirstNameUserProf,edtLastNameUserProf,edtStreetAddressUserProf,edtPostalCodeUserProf,edtPhoneNumberUserProf,edtSignUpEmailUserProf;
    private SQLiteHandler db;
    private SessionManager session;
    Button btnUpdate,btnCancel;
    String uniqueId;
    String createdAt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_userprofile, container, false);

        edtFirstNameUserProf = (EditText) rootView.findViewById(R.id.edtFirstNameUserProf);
        edtLastNameUserProf = (EditText) rootView.findViewById(R.id.edtLastNameUserProf);
        edtStreetAddressUserProf = (EditText) rootView.findViewById(R.id.edtStreetAddressUserProf);
        edtPostalCodeUserProf = (EditText) rootView.findViewById(R.id.edtPostalCodeUserProf);
        edtPhoneNumberUserProf = (EditText) rootView.findViewById(R.id.edtPhoneNumberUserProf);
        edtSignUpEmailUserProf = (EditText) rootView.findViewById(R.id.edtSignUpEmailUserProf);
        btnUpdate = (Button)rootView.findViewById(R.id.btnUpdate);
        btnCancel = (Button)rootView.findViewById(R.id.btnCancel);


        edtSignUpEmailUserProf.setEnabled(false);
        edtFirstNameUserProf.setEnabled(false);
        edtLastNameUserProf.setEnabled(false);


        db = new SQLiteHandler(getActivity().getApplicationContext());

        // session manager
        //session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
        String phone_number = user.get("PHONE_NUMBER");
        String STREET_ADDRESS = user.get("STREET_ADDRESS");
        String POSTAL_CODE = user.get("POSTAL_CODE");
        uniqueId = user.get("uid");
        createdAt = user.get("created_at");

        edtFirstNameUserProf.setText(name);
        edtSignUpEmailUserProf.setText(email);
        edtPhoneNumberUserProf.setText(phone_number);
        edtStreetAddressUserProf.setText(STREET_ADDRESS);
        edtPostalCodeUserProf.setText(POSTAL_CODE);

        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate:
                String nameUpdate = edtFirstNameUserProf.getText().toString().trim();

                String emailUpdate = edtSignUpEmailUserProf.getText().toString().trim();
                String street_addressUpdate = edtStreetAddressUserProf.getText().toString().trim();
                String postalCodeUpdate = edtPostalCodeUserProf.getText().toString().trim();
                String phoneNumberUpdate = edtPhoneNumberUserProf.getText().toString().trim();

                if (!street_addressUpdate.isEmpty()
                        && !postalCodeUpdate.isEmpty() && !phoneNumberUpdate.isEmpty()) {
                    db.dbUpdate(nameUpdate,emailUpdate,uniqueId,createdAt,phoneNumberUpdate,street_addressUpdate,postalCodeUpdate);

                    Intent home = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                    getActivity().startActivity(home);
                    getActivity().finish();

                    //db.addUser(name, email, unique_id, created_at,phone_number,street_address,postalCode);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case R.id.btnCancel:
                Intent home = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                getActivity().startActivity(home);
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
