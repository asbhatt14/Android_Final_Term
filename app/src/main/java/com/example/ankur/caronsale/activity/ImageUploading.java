package com.example.ankur.caronsale.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.helper.CarDetailsDAO;
import com.example.ankur.caronsale.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageUploading extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView txtToolbar;
    ImageView imgToolbarBack,imgImageUpload;
    Button btnFinish;
    static final int CAMERA_REQUEST_CODE = 01;
    static final int CAMERA_CODE = 05;
    String titleForSale,priceOfCar,description,drivenKms,MfgYear,city,carCapacity,ownerType;
    String filePath="";

    Car objCar;
    Boolean forEdit;


    String[] permissions = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_uploading);
        initToolBar();
        imgImageUpload = (ImageView) findViewById(R.id.imgImageUpload);
        btnFinish = (Button)findViewById(R.id.btnFinish);
        imgImageUpload.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        titleForSale = b.getString("titleForSale");
        priceOfCar = b.getString("priceOfCar");
        description =b.getString("description");
        drivenKms =b.getString("drivenKms");
        MfgYear =b.getString("MfgYear");
        city =b.getString("city");
        carCapacity =b.getString("carCapacity");
        ownerType =b.getString("ownerType");

        Intent intent = getIntent();
        forEdit = b.getBoolean("forEdit",false);
        objCar = (Car) b.getSerializable("carobject");

        if(forEdit){
            showImage();
        }
    }

    private void showImage() {
        Bitmap bitmap = BitmapFactory.decodeFile(objCar.getCarPhoto());

        Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);

        imgImageUpload.setImageBitmap(lowResBitmap);
        imgImageUpload.setScaleType(ImageView.ScaleType.FIT_XY);
        imgImageUpload.setTag(filePath);

        if(bitmap!=null)
        {
            bitmap.recycle();
            bitmap=null;
        }
    }


    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);


        txtToolbar.setText("Upload Images");

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
        switch (view.getId()){
            case R.id.imgImageUpload:
                if(checkPermissions()){
                    openGallery();
                }
                break;
            case R.id.btnFinish:
                loadFragment();
                break;
            default:
                break;
        }
    }

    private void loadFragment() {
        if(!titleForSale.isEmpty() && !priceOfCar.isEmpty() && !description.isEmpty() && !drivenKms.isEmpty() &&
                !ownerType.isEmpty() && !MfgYear.isEmpty() && !MfgYear.equalsIgnoreCase("MFG Year") &&
                !city.isEmpty() && !city.equalsIgnoreCase("Select City") && !carCapacity.isEmpty() &&
                !carCapacity.equalsIgnoreCase("Car Capacity") && !filePath.isEmpty()){

                    if(!forEdit){
                        objCar = new Car();
                    }


                    objCar.setModelName(titleForSale);
                    objCar.setPrice(priceOfCar);
                    objCar.setDescription(description);
                    objCar.setKmsDriven(drivenKms);
                    objCar.setOwnerType(ownerType);
                    objCar.setMfgYear(MfgYear);
                    objCar.setCarCapacity(carCapacity);
                    objCar.setCarPhoto(filePath);
                    objCar.setIsLiked(0);

                    SQLiteHandler db = new SQLiteHandler(this);
                    HashMap<String, String> user = db.getUserDetails();
                    String name = user.get("name");
                    String id = user.get("uid");
                    objCar.setOwnerId(id);

                    if(objCar!=null){
                        CarDetailsDAO carDetailsDAO = new CarDetailsDAO(this);
                        if(!forEdit){
                            carDetailsDAO.dbInsert(objCar);
                            Toast.makeText(this,"Car Details Added",Toast.LENGTH_SHORT).show();
                            Intent homeActivity = new Intent(this,HomeActivity.class);
                            startActivity(homeActivity);
                            finish();
                        }else {
                            carDetailsDAO.dbUpdateCar(objCar);
                            Toast.makeText(this,"Car Details Updated",Toast.LENGTH_SHORT).show();
                            Intent homeActivity = new Intent(this,HomeActivity.class);
                            startActivity(homeActivity);
                            finish();

                        }



                        carDetailsDAO.close();


                    }


        }else{
            Toast.makeText(getApplicationContext(),
                    "Please Upload Image", Toast.LENGTH_LONG)
                    .show();
        }


    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),CAMERA_REQUEST_CODE );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
/*                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openCamera();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }*/
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    /*String permissions = "";
                    for (String per : permissionsList) {
                        permissions += "\n" + per;
                    }*/
                    // permissions list of don't granted permission
                }
                return;

                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);//
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_REQUEST_CODE){
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
                cursor.close();


                Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);

                imgImageUpload.setImageBitmap(lowResBitmap);
                imgImageUpload.setScaleType(ImageView.ScaleType.FIT_XY);
                imgImageUpload.setTag(filePath);

                if(bitmap!=null)
                {
                    bitmap.recycle();
                    bitmap=null;
                }
            }
        }
    }

}
