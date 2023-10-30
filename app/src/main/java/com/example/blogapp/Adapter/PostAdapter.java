package com.example.blogapp.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blogapp.HomeActivity;
import com.example.blogapp.Model.PostModel;
import com.example.blogapp.R;
import com.example.blogapp.SinglePostActivity;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder> {
    Context context;
    List<PostModel> postModelList;

    public PostAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public PostAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_post , parent , false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.MyHolder holder, int position) {
        String title = postModelList.get(position).getpTitle();
        String description = postModelList.get(position).getpDescription();
        String image = postModelList.get(position).getpImage();

        holder.postTitle.setText(title);
        holder.postDescription.setText(description);

        Glide.with(context).load(image).into(holder.postImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    String postTitle = postModelList.get(adapterPosition).getpTitle();
                    String postDescription = postModelList.get(adapterPosition).getpDescription();
                    String postImage = postModelList.get(adapterPosition).getpImage();

                    Intent intent = new Intent(context, SinglePostActivity.class);
                    intent.putExtra("postTitle", postTitle);
                    intent.putExtra("postDescription", postDescription);
                    intent.putExtra("postImage", postImage);
                    context.startActivity(intent);
                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView postImage;
        TextView postTitle , postDescription;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.postImage);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription = itemView.findViewById(R.id.postDescription);

        }
    }
}
