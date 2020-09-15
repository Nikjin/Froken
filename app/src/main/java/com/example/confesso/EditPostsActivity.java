package com.example.confesso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.confesso.Model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class EditPostsActivity extends AppCompatActivity {

    MaterialEditText title,description;
    ImageView post_image,back;
    TextView done;
    String postid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posts);

        title =findViewById(R.id.title);
        description =findViewById(R.id.description);
        post_image =findViewById(R.id.post_image);
        done =findViewById(R.id.done);
        back=findViewById(R.id.back);



        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);

                title.setText(post.getTitle());
                description.setText(post.getDescription());
                Glide.with(getApplicationContext()).load(post.getPostimage()).into(post_image);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeKeyboard();
                updatePost(title.getText().toString(),description.getText().toString());

                finish();

            }


        });






    }

    private void updatePost(String title, String description)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title",title);
        hashMap.put("description",description);

        reference.updateChildren(hashMap);


    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
