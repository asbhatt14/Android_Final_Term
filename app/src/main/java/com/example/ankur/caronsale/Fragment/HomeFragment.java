package com.example.ankur.caronsale.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ankur.caronsale.Adapter.HomeAdapter;
import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.activity.AddCarActivity;
import com.example.ankur.caronsale.activity.CarDetailActivity;
import com.example.ankur.caronsale.activity.HomeActivity;
import com.example.ankur.caronsale.activity.MainActivity;
import com.example.ankur.caronsale.helper.CarDetailsDAO;
import com.example.ankur.caronsale.helper.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<Car> listCar;
    private FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        listCar = new ArrayList<Car>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        //loadCarList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newActivity = new Intent(getActivity().getApplicationContext(), AddCarActivity.class);
                startActivity(newActivity);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            //    Movie movie = movieList.get(position);
              //  Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
              //  Intent newActivity = new Intent(getActivity().getApplicationContext(), CarDetailActivity.class);
               // startActivity(newActivity);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCarList();
    }

    private void loadCarList() {
        CarDetailsDAO agentDAO = new CarDetailsDAO(getActivity().getApplicationContext());
        listCar = (ArrayList<Car>) agentDAO.dbSearch();
        agentDAO.close();
        homeAdapter = new HomeAdapter(getActivity().getApplicationContext(),listCar,false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(homeAdapter);
    }

    private void setALlCars(){


        //String modelName, String kmsDriven, String price, String mfgYear, String ownerType, String description, String carCapacity) {
        Car objCar = new Car(101,"abc","100000","$3000","2013","1st","Good Condition","4 door");
        listCar.add(objCar);

        objCar = new Car(102,"abc1","200000","$4000","2014","2nd","Ok condition","2 door");
        listCar.add(objCar);

        objCar = new Car(103,"abc2","300000","$5000","2016","3rd","Perfect Condition","2 door Sports Car");
        listCar.add(objCar);

       // homeAdapter.notifyDataSetChanged();
    }


}