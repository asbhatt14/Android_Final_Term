    package com.example.ankur.caronsale.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ankur.caronsale.R;

import java.util.List;

    public class DialogActivity extends AppCompatActivity {

        Toolbar toolbar;
        TextView txtToolbar;
        ImageView imgToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout);

        initToolBar();

        ListView lstDialog = (ListView) findViewById(R.id.lstDialog);

        Intent intent = getIntent();
        List<String> userName = intent.getStringArrayListExtra("nameList");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,userName);
        lstDialog.setAdapter(adapter);
    }
        public void initToolBar() {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
            imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

            txtToolbar.setText("People who Liked");

            setSupportActionBar(toolbar);

            imgToolbarBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        }
}
