package com.example.ankur.caronsale.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.R;

public class SpecificationFragment extends Fragment {

    TextView txtOwnerTypeTextDetail,txtCarMFGYearDetail,txtCarcapacityDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_specification, container, false);
        txtCarcapacityDetail= (TextView) rootView.findViewById(R.id.txtCarcapacityDetail);
        txtCarMFGYearDetail= (TextView) rootView.findViewById(R.id.txtCarMFGYearDetail);
        txtOwnerTypeTextDetail= (TextView) rootView.findViewById(R.id.txtOwnerTypeTextDetail);

        Car objCar = (Car) getArguments().getSerializable("carObject");

        txtCarcapacityDetail.setText(objCar.getCarCapacity());
        txtCarMFGYearDetail.setText(objCar.getMfgYear());
        txtOwnerTypeTextDetail.setText(objCar.getOwnerType());


        return  rootView;
    }
}
