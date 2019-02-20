package com.example.ankur.caronsale.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.helper.SQLiteHandler;

import java.util.HashMap;

public class UserDetailFragment extends Fragment implements View.OnClickListener {

    TextView txtSendMessage,txtOwnerPhoneDetail,txtOwnerDetailName;
    private SQLiteHandler db;
    String phone_number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.userdetaillayout, container, false);

        txtSendMessage= (TextView) rootView.findViewById(R.id.txtSendMessage);
        txtOwnerPhoneDetail= (TextView) rootView.findViewById(R.id.txtOwnerPhoneDetail);
        txtOwnerDetailName= (TextView) rootView.findViewById(R.id.txtOwnerDetailName);

        Car objCar = (Car) getArguments().getSerializable("carObject");

        db = new SQLiteHandler(getActivity().getApplicationContext());

        String id = objCar.getOwnerId();

        final HashMap<String, String> userPost = db.getUserDetailsFromID(id);
        String userNamePost = userPost.get("name");

        String email = userPost.get("email");
        phone_number = userPost.get("PHONE_NUMBER");
        String STREET_ADDRESS = userPost.get("STREET_ADDRESS");
        String POSTAL_CODE = userPost.get("POSTAL_CODE");

        txtOwnerDetailName.setText(userNamePost);
        txtOwnerPhoneDetail.setText(phone_number);
        txtSendMessage.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendIntent.setType("vnd.android-dir/mms-sms");
        sendIntent.setData(Uri.parse("sms:"+phone_number));
        startActivity(sendIntent);
    }
}
