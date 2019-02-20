package com.example.ankur.caronsale.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ankur.caronsale.Adapter.AddCommentAdapter;
import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.Model.Comment;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.helper.CommentDAO;
import com.example.ankur.caronsale.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCommentActivity extends AppCompatActivity  implements View.OnClickListener{

    Toolbar toolbar;
    TextView txtToolbar;
    ImageView imgToolbarBack,btnSend;
    EditText edtAddComment;
    ListView lstAddComment;
    Car objCar;
    String ownerId;
    String commentUserId;
    private SQLiteHandler db;
    ArrayList<Comment> lstComment;
    AddCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        initToolBar();
        btnSend = (ImageView) findViewById(R.id.btnSend);
        edtAddComment = (EditText) findViewById(R.id.edtAddComment);
        lstAddComment = (ListView) findViewById(R.id.lstAddComment);


        //id INTEGER PRIMARY KEY,carID INEGER,ownerId TEXT,comment_user_id TEXT, "


        Intent intent = getIntent();
        objCar = (Car) intent.getSerializableExtra("carDetail");
        commentUserId = intent.getStringExtra("currentUserId");
        ownerId = objCar.getOwnerId();

/*        db = new SQLiteHandler(this);
        final HashMap<String, String> user = db.getUserDetails();
        commentUserId = user.get("uid");*/
        btnSend.setOnClickListener(this);
        loadCommentList();
        lstAddComment.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

    }

    private void loadCommentList() {
        CommentDAO commentDAO = new CommentDAO(this);
        lstComment = (ArrayList<Comment>) commentDAO.dbSearch(objCar.getCarId());
        commentDAO.close();
        adapter = new AddCommentAdapter(getApplicationContext(),lstComment);
        lstAddComment.setAdapter(adapter);
        lstAddComment.setSelection(adapter.getCount()-1);
    }


    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);

        txtToolbar.setText("Add Comment");

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
        String commentText = edtAddComment.getText().toString();
        if(!commentText.isEmpty()){
            Comment objComment = new Comment();
            objComment.setCarID(objCar.getCarId());
            objComment.setComment_userID(commentUserId);
            objComment.setOwnerId(ownerId);
            objComment.setCommentText(commentText);

            if(objComment!=null){
                CommentDAO commentDAO = new CommentDAO(this);
                commentDAO.dbInsert(objComment);
                commentDAO.close();
                loadCommentList();
                edtAddComment.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
