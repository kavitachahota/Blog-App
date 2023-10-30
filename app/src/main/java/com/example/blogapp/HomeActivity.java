package com.example.blogapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.blogapp.Adapter.PostAdapter;
import com.example.blogapp.Model.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth auth;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    List<PostModel> postModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);//to show the latest post first

        recyclerView.setLayoutManager(linearLayoutManager);
        postModelList = new ArrayList<>();

        loadPosts();
    }

    private void loadPosts(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModelList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    PostModel postModel = ds.getValue(PostModel.class);
                    postModelList.add(postModel);
                    postAdapter = new PostAdapter(HomeActivity.this,postModelList);

                    recyclerView.setAdapter(postAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            auth.signOut();
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
        }
        if(item.getItemId() == R.id.action_add_post){
            startActivity(new Intent(HomeActivity.this , AddPostActivity.class));

        }
        if(item.getItemId()==R.id.action_profile){
            Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
            profileIntent.putExtra("email", auth.getCurrentUser().getEmail());
            startActivity(profileIntent);
        }
        return super.onOptionsItemSelected(item);
    }


}