package com.example.ankur.caronsale.Fragment;

import android.content.Intent;
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

import com.example.ankur.caronsale.Adapter.FavoriteAdapter;
import com.example.ankur.caronsale.Adapter.HomeAdapter;
import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.Model.Favorite;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.activity.AddCarActivity;
import com.example.ankur.caronsale.helper.CarDetailsDAO;
import com.example.ankur.caronsale.helper.FavoriteDAO;
import com.example.ankur.caronsale.helper.RecyclerTouchListener;
import com.example.ankur.caronsale.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    private FavoriteAdapter homeAdapter;
    private List<Car> listCar;
    private FloatingActionButton fab;
    private SQLiteHandler db;
    String currentUserID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        listCar = new ArrayList<Car>();

        db = new SQLiteHandler(getActivity().getApplicationContext());
        final HashMap<String, String> user = db.getUserDetails();
        currentUserID = user.get("uid");

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

    public void loadCarList() {
        CarDetailsDAO agentDAO = new CarDetailsDAO(getActivity().getApplicationContext());

        FavoriteDAO favoriteDAO = new FavoriteDAO(getActivity().getApplicationContext());
        List<Favorite> lstFavTemp = new ArrayList<Favorite>();
        lstFavTemp = favoriteDAO.dbSearchCar(currentUserID);

        for(int i =0;i<lstFavTemp.size();i++){
            listCar.add(agentDAO.dbSearchFavCar(lstFavTemp.get(i).getCarID()));

        }
        agentDAO.close();
        homeAdapter = new FavoriteAdapter(getActivity().getApplicationContext(),listCar,false,this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(homeAdapter);
    }
}
