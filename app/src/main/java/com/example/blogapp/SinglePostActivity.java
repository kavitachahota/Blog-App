package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SinglePostActivity extends AppCompatActivity {

    ImageView singlePostImage;
    TextView singlePostTitle,singlePostDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        singlePostImage=findViewById(R.id.singlePostImage);
        singlePostTitle = findViewById(R.id.singlePostTitle);
        singlePostDescription = findViewById(R.id.singlePostDescription);

        String postTitle = getIntent().getStringExtra("postTitle");
        String postDescription = getIntent().getStringExtra("postDescription");
        String postImage = getIntent().getStringExtra("postImage");


        singlePostTitle.setText(postTitle);
        singlePostDescription.setText(postDescription);
        Glide.with(this).load(postImage).into(singlePostImage);
    }
}
