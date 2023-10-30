package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    EditText emailedited;
    Button btnDelete,btnSave;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        btnDelete= findViewById(R.id.delete);
        btnSave = findViewById(R.id.save);
        emailedited= findViewById(R.id.editemail);
        emailedited.setText(email);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedEmail = emailedited.getText().toString().trim();
                FirebaseUser user = auth.getCurrentUser();
                user.updateEmail(editedEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ProfileActivity.this, "Email update link sent. Please check your new email for verification.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                                    }else{
                                        Toast.makeText(ProfileActivity.this, "Failed to send verification email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(ProfileActivity.this, "Failed to update email: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = auth.getCurrentUser();
                if (user!=null){
                    user.delete().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            deleteUserData();
                            Toast.makeText(ProfileActivity.this, "Profile deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileActivity.this,RegisterActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(ProfileActivity.this, "User deletion failed.", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            }

        });
    }



    private void deleteUserData() {
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            reference.child(user.getUid()).removeValue();
        }
    }



}