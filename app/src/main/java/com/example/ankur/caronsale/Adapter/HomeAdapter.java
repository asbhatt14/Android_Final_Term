package com.example.ankur.caronsale.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.caronsale.Fragment.FavoriteFragment;
import com.example.ankur.caronsale.Model.Car;
import com.example.ankur.caronsale.Model.Favorite;
import com.example.ankur.caronsale.Model.Like;
import com.example.ankur.caronsale.R;
import com.example.ankur.caronsale.activity.AddCarActivity;
import com.example.ankur.caronsale.activity.AddCommentActivity;
import com.example.ankur.caronsale.activity.CarDetailActivity;
import com.example.ankur.caronsale.activity.DialogActivity;
import com.example.ankur.caronsale.activity.HomeActivity;
import com.example.ankur.caronsale.helper.CarDetailsDAO;
import com.example.ankur.caronsale.helper.FavoriteDAO;
import com.example.ankur.caronsale.helper.LikeDAO;
import com.example.ankur.caronsale.helper.SQLiteHandler;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{
    private SQLiteHandler db;    private List<Car> carList;

    Context thisconext;
    boolean MyPost;

    public HomeAdapter(Context thisconext, List<Car> carList, boolean MyPost) {
        this.carList = carList;
        this.thisconext = thisconext;
        this.MyPost = MyPost;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_home_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Car objCar = carList.get(position);
        holder.txtModelName.setText(objCar.getModelName());
        holder.txtKmsDriven.setText(objCar.getKmsDriven());

        String id = objCar.getOwnerId();

        db = new SQLiteHandler(thisconext);
        final HashMap<String, String> user = db.getUserDetails();


        final HashMap<String, String> userPost = db.getUserDetailsFromID(id);
        String userNamePost = userPost.get("name");



        String name = user.get("name");

        holder.txtOwnerName.setText(userNamePost);
        holder.txtPrice.setText(objCar.getPrice());

        holder.lnrMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newActivity = new Intent(thisconext.getApplicationContext(), CarDetailActivity.class);
                newActivity.putExtra("carDetail",objCar);
                thisconext.startActivity(newActivity);
            }
        });

/*        For now if(objCar.getIsLiked() == 1){
            holder.imgLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.favorite));
            //   objCar.setIsLiked(1);

        }else{
            holder.imgLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.unfavorite));
            // objCar.setIsLiked(0);
        }*/


        final String currentUserId = user.get("uid");
        final long carID = objCar.getCarId();
        final FavoriteDAO favoriteDAO = new FavoriteDAO(thisconext);

        List<Favorite> lstFavTemp = new ArrayList<Favorite>();
        lstFavTemp = favoriteDAO.dbSearch(currentUserId,carID);

        if(lstFavTemp!=null && lstFavTemp.size()>0){
            for(int i=0;i<lstFavTemp.size();i++){
                if(lstFavTemp.get(i).getCarID() == objCar.getCarId()){
                    holder.imgLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.favorite));
                }else{
                    holder.imgLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.unfavorite));
                }
            }

        }

        holder.imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentActivity = new Intent(thisconext, AddCommentActivity.class);
                commentActivity.putExtra("carDetail",objCar);
                commentActivity.putExtra("currentUserId",currentUserId);
                thisconext.startActivity(commentActivity);
            }
        });

        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Favorite> lstFav = new ArrayList<Favorite>();
                lstFav = favoriteDAO.dbSearch(currentUserId,carID);

                CarDetailsDAO carDetailsDAO = new CarDetailsDAO(thisconext);

                if(lstFav!=null && lstFav.size()>0){
                    holder.imgLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.unfavorite));
                  //  carDetailsDAO.dbUpdate(carID);
                    favoriteDAO.dbUpdate(currentUserId,carID);
                }else{
                    holder.imgLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.favorite));
                    //carDetailsDAO.dbUpdateCar(carID);
                    favoriteDAO.dbInsert(currentUserId,carID);
                }



/*TODO::                if(finalLstFav !=null && finalLstFav.size()>0){


/*For now
                if(objCar.getIsLiked() == 0){
                    holder.imgLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.favorite));
                    objCar.setIsLiked(1);

                }else{
                    holder.imgLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.unfavorite));
                    objCar.setIsLiked(0);
                }


                CarDetailsDAO carDetailsDAO = new CarDetailsDAO(thisconext);
                carDetailsDAO.dbUpdate(objCar);
                carDetailsDAO.close();*/
                notifyDataSetChanged();
            }
        });

        if(objCar!=null && objCar.getCarPhoto()!=null){
           /* Bitmap bitmap = BitmapFactory.decodeFile(objCar.getCarPhoto());
            Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);
            holder.imgCarPhoto.setImageBitmap(lowResBitmap);
            if(bitmap!=null)
            {
                bitmap.recycle();
                bitmap=null;
            }*/
        }
        if(MyPost){
            holder.imgEditPost.setVisibility(View.VISIBLE);
        }else{
            holder.imgEditPost.setVisibility(View.GONE);
        }

        holder.imgEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editPostActivity = new Intent(thisconext, AddCarActivity.class);
                editPostActivity.putExtra("carobject", objCar);
                editPostActivity.putExtra("forEdit",true);
                thisconext.startActivity(editPostActivity);
            }
        });

        final LikeDAO likeDAO = new LikeDAO(thisconext);

        List<Like> lstLikeTemp = new ArrayList<Like>();
        lstLikeTemp = likeDAO.dbSearchForLike(currentUserId,carID);

        if(lstLikeTemp!=null){
            //for(int i=0;i<lstLikeTemp1.size();i++){
            if(lstLikeTemp.size()==0){
                holder.txtLike.setText("");
                holder.txtLike.setVisibility(View.GONE);
            }else {
                holder.txtLike.setText(""+lstLikeTemp.size());
                holder.txtLike.setVisibility(View.VISIBLE);
            }

            //}
        }

        if(lstLikeTemp!=null && lstLikeTemp.size()>0){
            for(int i=0;i<lstLikeTemp.size();i++){
                if(lstLikeTemp.get(i).getOwnerId().equalsIgnoreCase(currentUserId)){
                    holder.imgFacebookLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.facebook_liked));
                }else{
                    holder.imgFacebookLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.facebook_unliked));
                }
            }
        }

        holder.imgFacebookLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Like> lstLike = new ArrayList<Like>();
                lstLike = likeDAO.dbSearch(currentUserId,carID);

//                CarDetailsDAO carDetailsDAO = new CarDetailsDAO(thisconext);

                if(lstLike!=null && lstLike.size()>0){
                    holder.imgFacebookLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.facebook_unliked));
                    //  carDetailsDAO.dbUpdate(carID);
                    likeDAO.dbUpdate(currentUserId,carID);

                }else{
                    holder.imgFacebookLike.setImageDrawable(thisconext.getResources().getDrawable(R.drawable.facebook_liked));
                    //carDetailsDAO.dbUpdateCar(carID);
                    likeDAO.dbInsert(currentUserId,carID);

                }

                notifyDataSetChanged();
            }
        });

        holder.txtLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Like> lstLikeTemp = new ArrayList<Like>();
                lstLikeTemp = likeDAO.dbSearchForLike(currentUserId,carID);

                List<String> userName = new ArrayList<String>();

                for(int i =0;i<lstLikeTemp.size();i++){
                    final HashMap<String, String> userForName = db.getUserDetailsFromID(lstLikeTemp.get(i).getOwnerId());
                    String nameOfUser = userForName.get("name");
                    userName.add(nameOfUser);
                }


                       //Toast.makeText(thisconext,userName.get(j),Toast.LENGTH_SHORT).show();
                       Intent dialogActivity = new Intent(thisconext, DialogActivity.class);
                       dialogActivity.putStringArrayListExtra("nameList", (ArrayList<String>) userName);
                       thisconext.startActivity(dialogActivity);

            }
        });
    }


    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtModelName, txtKmsDriven, txtOwnerName,txtPrice,txtLike;
        public ImageView  imgCarPhoto,imgLike,imgComment,imgEditPost,imgFacebookLike;
        public LinearLayout lnrMain;

        public MyViewHolder(View view) {
            super(view);
            txtModelName = (TextView) view.findViewById(R.id.txtModelName);
            txtKmsDriven = (TextView) view.findViewById(R.id.txtKmsDriven);
            txtOwnerName = (TextView) view.findViewById(R.id.txtOwnerName);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            imgCarPhoto= (ImageView) view.findViewById(R.id.imgCarPhoto);
            imgComment= (ImageView) view.findViewById(R.id.imgComment);
            imgLike= (ImageView) view.findViewById(R.id.imgLike);
            lnrMain = (LinearLayout) view.findViewById(R.id.lnrMain);
            imgEditPost= (ImageView) view.findViewById(R.id.imgEditPost);
            imgFacebookLike = (ImageView) view.findViewById(R.id.imgLikeFav);
            txtLike= (TextView) view.findViewById(R.id.txtLike);
        }
    }
}
