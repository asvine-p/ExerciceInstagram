package com.example.priteshasvinetsakou.exerciceinsatgram;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;


public class UserImage extends AppCompatActivity {

    List<Image>         imageList       =   new ArrayList<>();
    String              userImageUrl;
    String              username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Instagram Profil");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.exit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.instagramApp.resetAccessToken();
                Intent mIntent =   new Intent(UserImage.this, MainActivity.class);
                startActivity(mIntent);
                finish();
            }
        });

        try {
            initList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView mListView       =   (ListView) findViewById(R.id.lv_image_media);
        MediaImageAdapter   mMediaAdapter   =   new MediaImageAdapter();
        mListView.setAdapter(mMediaAdapter);

    }

    //Parse JSON and fill the list of Photos
    public void initList() throws JSONException {
        JSONArray wholeData   =   ApplicationData.data.getJSONArray("data");

        for(int i = 0; i < wholeData.length(); i++) {
            JSONObject data_line       = wholeData.getJSONObject(i);
            JSONArray   tagList         = data_line.getJSONArray("tags");
            String tag                  =   "";
            String   ImageUrl           =    data_line.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
            userImageUrl                =   data_line.getJSONObject("user").getString("profile_picture");
            username                    =   data_line.getJSONObject("user").getString("username");
            for (int j = 0; j < tagList.length(); j++) {
                tag = tag.concat(tagList.getString(j)) + " ";
            }
            imageList.add(new Image(tag, ImageUrl));

        }
    }

    //Fill the ListView with DataSet by creating an adapter
    public class MediaImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public Object getItem(int position) {
            return imageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder  mViewHolder = new ViewHolder();
            View                    my_view;


            if (convertView == null) {
                my_view = View.inflate(getApplicationContext(), R.layout.item_image, null);
                mViewHolder = new ViewHolder();
            }
            else
                my_view = convertView;
            mViewHolder.tv_newsfeed_image_name      =   (TextView) my_view.findViewById(R.id.tv_newsfeed_image_name);
            mViewHolder.tv_user_name                =   (TextView) my_view.findViewById(R.id.tv_user_name);
            mViewHolder.iv_newsfeed_image_content   =   (ImageView) my_view.findViewById(R.id.iv_newfeed_image_content);
            mViewHolder.iv_profile_image            =   (ImageView) my_view.findViewById(R.id.iv_newsfeed_image_profile_image);
            mViewHolder.tv_newsfeed_image_name.setText(imageList.get(position).Description);
            mViewHolder.tv_user_name.setText(username);
            Picasso.with(UserImage.this).load(imageList.get(position).ImageUrl).into(mViewHolder.iv_newsfeed_image_content);
            Picasso.with(UserImage.this).load(userImageUrl).into(mViewHolder.iv_profile_image);

            return my_view;

        }
    }
    class ViewHolder {
        TextView    tv_newsfeed_image_name;
        TextView    tv_user_name;
        ImageView   iv_newsfeed_image_content;
        ImageView   iv_profile_image;
    }
}

