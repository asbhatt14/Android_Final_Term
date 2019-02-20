package com.example.ankur.caronsale.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ankur.caronsale.Model.Comment;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCommentAdapter extends ArrayAdapter<Comment> {

    private ArrayList<Comment> listOfAgents;
    Context context;
    private SQLiteHandler db;

    public AddCommentAdapter(@NonNull Context context, @NonNull List<Comment> objects) {

        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Comment objComment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_comment_layout, parent, false);
            viewHolder.txtComment = (TextView) convertView.findViewById(R.id.txtComment);
            viewHolder.txtCommentUsername = (TextView) convertView.findViewById(R.id.txtCommentUsername);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        String id = objComment.getComment_userID();
        db = new SQLiteHandler(context);
        final HashMap<String, String> user = db.getUserDetails();


        final HashMap<String, String> userPost = db.getUserDetailsFromID(id);
        String userNamePost = userPost.get("name");

        viewHolder.txtCommentUsername.setText(userNamePost);
        viewHolder.txtComment.setText(objComment.getCommentText());


        return result;
    }
}

    class ViewHolder{
        TextView txtCommentUsername;
        TextView txtComment;
    }

