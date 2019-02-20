package com.example.ankur.caronsale.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankur.caronsale.Adapter.ViewPagerAdapter;
import com.example.ankur.caronsale.Fragment.SpecificationFragment;
import com.example.ankur.caronsale.Fragment.UserDetailFragment;
import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.helper.SQLiteHandler;

import java.util.HashMap;

public class CarDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtToolbar;
    ImageView imgToolbarBack,imgDetailPhoto;
    TextView txtCarModelDetail,txtCarLocaitonDetail,txtCarPriceDetail;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SQLiteHandler db;
    HashMap<String, String> user;
    String STREET_ADDRESS,name,email,phone_number,POSTAL_CODE;

    Car objCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        initToolBar();
        imgDetailPhoto = (ImageView) findViewById(R.id.imgDetailPhoto);
        txtCarModelDetail = (TextView) findViewById(R.id.txtCarModelDetail);
        txtCarLocaitonDetail = (TextView) findViewById(R.id.txtCarLocaitonDetail);
        txtCarPriceDetail = (TextView) findViewById(R.id.txtCarPriceDetail);

        db = new SQLiteHandler(getApplicationContext());



        Intent intent = getIntent();
        objCar = (Car) intent.getSerializableExtra("carDetail");
        if(objCar!=null){
            fillDetails();
            user = db.getUserDetailsFromID(objCar.getOwnerId());

            name = user.get("name");
            email = user.get("email");
            phone_number = user.get("PHONE_NUMBER");
            STREET_ADDRESS = user.get("STREET_ADDRESS");
            POSTAL_CODE = user.get("POSTAL_CODE");
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void fillDetails() {
        Bitmap bitmap = BitmapFactory.decodeFile(objCar.getCarPhoto());
        Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,500,500,true);
        imgDetailPhoto.setImageBitmap(lowResBitmap);
        if(bitmap!=null)
        {
            bitmap.recycle();
            bitmap=null;
        }
        txtCarModelDetail.setText(objCar.getModelName());
        txtCarPriceDetail.setText(objCar.getPrice());
        txtCarLocaitonDetail.setText(objCar.getDescription());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putSerializable("carObject",objCar);

        Fragment specificationFragement = new SpecificationFragment();
        specificationFragement.setArguments(bundle);

        Fragment UserDetailFragment = new UserDetailFragment();
        UserDetailFragment.setArguments(bundle);

        adapter.addFragment(specificationFragement, "Specification");
       // adapter.addFragment(specificationFragement, "Engine Speicifation");
        adapter.addFragment(UserDetailFragment, "Owner Details");
        viewPager.setAdapter(adapter);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Car Detail Page");

        setSupportActionBar(toolbar);

        imgToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
