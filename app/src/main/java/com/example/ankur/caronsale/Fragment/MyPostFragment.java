package com.example.ankur.caronsale.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ankur.caronsale.Adapter.HomeAdapter;
import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.activity.AddCarActivity;
import com.example.ankur.caronsale.helper.CarDetailsDAO;
import com.example.ankur.caronsale.helper.RecyclerTouchListener;
import com.example.ankur.caronsale.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyPostFragment extends Fragment {

    RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<Car> listCar;
    private FloatingActionButton fab;
    private SQLiteHandler db;
    HashMap<String, String> user;
    String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        listCar = new ArrayList<Car>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        db = new SQLiteHandler(getActivity().getApplicationContext());
        user = db.getUserDetails();

        id=user.get("uid");

        /*name = user.get("name");
        email = user.get("email");
        phone_number = user.get("PHONE_NUMBER");
        STREET_ADDRESS = user.get("STREET_ADDRESS");
        POSTAL_CODE = user.get("POSTAL_CODE");*/

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
        listCar = (ArrayList<Car>) agentDAO.dbSearchForUSer(id);
        agentDAO.close();
        homeAdapter = new HomeAdapter(getActivity().getApplicationContext(),listCar,true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(homeAdapter);
    }
}
