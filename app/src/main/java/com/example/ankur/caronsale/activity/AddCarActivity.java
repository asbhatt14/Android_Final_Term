package com.example.ankur.caronsale.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.R;

public class AddCarActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView txtToolbar;
    ImageView imgToolbarBack;
    EditText edtTitle,edtPrice,edtDescription,txtDrivenKmHint,txtOwnerTypeText;
    TextView txtMfgYear,txtSelectCity,txtCarCapacity;
    LinearLayout lnrMfgYear,lnrCity,lnrCarCapacity;
    Button btnContinue;
    Car objCar;
    Boolean forEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        initToolBar();

        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        txtMfgYear = (TextView) findViewById(R.id.txtMfgYear);
        txtDrivenKmHint = (EditText) findViewById(R.id.txtDrivenKmHint);
        txtOwnerTypeText = (EditText) findViewById(R.id.txtOwnerTypeText);
        txtSelectCity = (TextView) findViewById(R.id.txtSelectCity);
        txtCarCapacity = (TextView) findViewById(R.id.txtCarCapacity);

        lnrMfgYear = (LinearLayout) findViewById(R.id.lnrMfgYear);
        lnrCity = (LinearLayout) findViewById(R.id.lnrCity);
        lnrCarCapacity = (LinearLayout) findViewById(R.id.lnrCarCapacity);

        btnContinue = (Button) findViewById(R.id.btnContinue);
        lnrMfgYear.setOnClickListener(this);
        lnrCity.setOnClickListener(this);
        lnrCarCapacity.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

        Intent intent = getIntent();
        forEdit = intent.getBooleanExtra("forEdit",false);
        objCar = (Car) intent.getSerializableExtra("carobject");

        if(forEdit){
            fillDetails();
        }

    }



    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Add Car Detail");

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
            case R.id.lnrMfgYear:
                showDialog();
                break;
            case R.id.lnrCity:
                showDialogCity();
                break;
            case R.id.lnrCarCapacity:
                showDialogCapacity();
                break;
            case R.id.btnContinue:
                OpenImageUploading();
                break;
            default:
                break;
        }
    }

    private void OpenImageUploading() {

        String titleForSale = edtTitle.getText().toString().trim();
        String priceOfCar = edtPrice.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String drivenKms = txtDrivenKmHint.getText().toString().trim();
        String ownerType = txtOwnerTypeText.getText().toString().trim();
        String MfgYear = txtMfgYear.getText().toString().trim();
        String city = txtSelectCity.getText().toString().trim();
        String carCapacity = txtCarCapacity.getText().toString().trim();

        if(!titleForSale.isEmpty() && !priceOfCar.isEmpty() && !description.isEmpty() && !drivenKms.isEmpty() &&
                !ownerType.isEmpty() && !MfgYear.isEmpty() && !MfgYear.equalsIgnoreCase("MFG Year") &&
                !city.isEmpty() && !city.equalsIgnoreCase("Select City") && !carCapacity.isEmpty() &&
                !carCapacity.equalsIgnoreCase("Car Capacity")){
            Intent imageUploading = new Intent(this,ImageUploading.class);
            Bundle b = new Bundle();
            b.putString("titleForSale",titleForSale);
            b.putString("priceOfCar",priceOfCar);
            b.putString("description",description);
            b.putString("drivenKms",drivenKms);
            b.putString("ownerType",ownerType);
            b.putString("MfgYear",MfgYear);
            b.putString("city",city);
            b.putString("carCapacity",carCapacity);
            if(forEdit){
                b.putSerializable("carobject",objCar);
                b.putBoolean("forEdit",forEdit);
            }
            imageUploading.putExtras(b);
            startActivity(imageUploading);
        }else{
            Toast.makeText(getApplicationContext(),
                    "Please enter all details!", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void showDialog() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select Mfg Year");


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        for(int i =2001;i<2019;i++){
            arrayAdapter.add(""+ i);
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                txtMfgYear.setText(strName);
            }
        });
        builderSingle.show();
    }
    private void showDialogCity() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select City");


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        arrayAdapter.add("Toronto");
        arrayAdapter.add("Brampton");
        arrayAdapter.add("Ottawa");
        arrayAdapter.add("Kingston");
        arrayAdapter.add("Lindsay");
        arrayAdapter.add("Hamilton");
        arrayAdapter.add("Peterborugh");
        arrayAdapter.add("Etobicock");



        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                txtSelectCity.setText(strName);
            }
        });
        builderSingle.show();
    }

    private void showDialogCapacity() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select Car Style");


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);

        arrayAdapter.add("2 Door");
        arrayAdapter.add("4 Door");
        arrayAdapter.add("Mini Van");
        arrayAdapter.add("Van");


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                txtCarCapacity.setText(strName);
            }
        });
        builderSingle.show();
    }

    private void fillDetails() {

        /*edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        txtMfgYear = (TextView) findViewById(R.id.txtMfgYear);
        txtDrivenKmHint = (EditText) findViewById(R.id.txtDrivenKmHint);
        txtOwnerTypeText = (EditText) findViewById(R.id.txtOwnerTypeText);
        txtSelectCity = (TextView) findViewById(R.id.txtSelectCity);
        txtCarCapacity = (TextView) findViewById(R.id.txtCarCapacity);

        lnrMfgYear = (LinearLayout) findViewById(R.id.lnrMfgYear);
        lnrCity = (LinearLayout) findViewById(R.id.lnrCity);
        lnrCarCapacity = (LinearLayout) findViewById(R.id.lnrCarCapacity);*/


/*        objCar.setModelName(titleForSale);
        objCar.setPrice(priceOfCar);
        objCar.setDescription(description);
        objCar.setKmsDriven(drivenKms);
        objCar.setOwnerType(ownerType);
        objCar.setMfgYear(MfgYear);
        objCar.setCarCapacity(carCapacity);
        objCar.setCarPhoto(filePath);
        objCar.setIsLiked(0);*/
        if(objCar!=null){
          edtTitle.setText(objCar.getModelName());
          edtPrice.setText(objCar.getPrice());
          edtDescription.setText(objCar.getDescription());
          txtMfgYear.setText(objCar.getMfgYear());
          txtDrivenKmHint.setText(objCar.getKmsDriven());
          txtOwnerTypeText.setText(objCar.getOwnerType());
          txtCarCapacity.setText(objCar.getCarCapacity());
        }
    }

}
