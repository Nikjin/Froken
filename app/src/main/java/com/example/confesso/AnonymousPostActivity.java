package com.example.confesso;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.confesso.Model.User;
import com.example.confesso.Notifications.Token;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hendraanggrian.appcompat.socialview.Hashtag;
import com.hendraanggrian.appcompat.socialview.Mention;
import com.hendraanggrian.appcompat.widget.MentionArrayAdapter;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;
import com.hendraanggrian.appcompat.widget.SocialView;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnonymousPostActivity extends AppCompatActivity {

    private Spinner spinner;
    TextView anonymous;
    public static final String TAG = "MyActivity";
    DatabaseReference reference;
    TextInputLayout title;
    SocialAutoCompleteTextView description;
    Button share;
    ImageView addphoto,cprofile;
    ImageView back;
    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;
    FirebaseAuth auth;
    private String item;
    private String saveCurrentDate, saveCurrentTime;
    int pos;

    String anonymouspostid;
    List<User> userList;
    private ArrayAdapter<Hashtag> defaultHashtagAdapter;
    private ArrayAdapter<Mention> defaultMentionAdapter;

    private FirebaseUser firebaseUser;

    String CurrentUsername;



    Integer [] imageIds= {
            R.drawable.froken1,
            R.drawable.froken2,
            R.drawable.froken3,
            R.drawable.froken4,
            R.drawable.froken5,
            R.drawable.froken6,
            R.drawable.froken7,
            R.drawable.froken8,
            R.drawable.froken9,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_post);

        title = findViewById(R.id.edittitle);
        description = findViewById(R.id.desc);
        addphoto = findViewById(R.id.plus);

        share = findViewById(R.id.butshare);
        back = findViewById(R.id.arr);
        cprofile = findViewById(R.id.cprofile);



        anonymous = findViewById(R.id.name);
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("posts");


        userList = new ArrayList<>();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


        FirebaseUser firebaseuser = auth.getCurrentUser();
        String userid = firebaseuser.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                String name = dataSnapshot.child("anonymous").getValue().toString();
                anonymous.setText(name);
                CurrentUsername =  dataSnapshot.child("anonymous").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AnonymousPostActivity.this,AnonymousPicSelecter.class));


            }
        });

        socialDesc();

        Intent i = getIntent();
        pos = i.getIntExtra("pos",0);
        cprofile.setImageResource(imageIds[pos]);


        spinner = findViewById(R.id.spinner);

        List<String> categories = new ArrayList<>();
        categories.add(0, "Choose..");
        categories.add("Education/News");
        categories.add("Fashion/Beauty");
        categories.add("Food");
        categories.add("Fiction/Fantasy");
        categories.add("Humour/Fun");
        categories.add("Music/Dance");
        categories.add("Movies/TV shows");
        categories.add("Photography/Videography");
        categories.add("Science/Tech");
        categories.add("Stories/Confessions");
        categories.add("Sports/Fitness");
        categories.add("Travel");
        categories.add("Thoughts/Quotes");
        categories.add("Others");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Choose..")) {

                    item = "Choose..";
                    //do nothing
                } else {
                    item = parent.getItemAtPosition(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnonymousPostActivity.this,HomeActivity.class));
                finish();
            }
        });




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateTitle()) {
                    return;
                }
                uploadImage();

            }
        });

        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .start(AnonymousPostActivity.this);


            }
        });





    }

    private String getFileExtension (Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private boolean validateTitle() {
        String titleinput = title.getEditText().getText().toString().trim();
        if (titleinput.isEmpty()) {
            title.setError("Field can't be empty");
            return false;
        } else {
            title.setError(null);
            return true;
        }
    }


    private void uploadImage()
    {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();

        if (imageUri !=null && !item.equals("Choose.."))
        {
            final StorageReference filerefrence = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = filerefrence.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return filerefrence.getDownloadUrl();
                }


            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if ((task.isSuccessful()))
                    {
                        Uri downloadUri = task.getResult();
                        myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Anonymous Posts");
                        anonymouspostid = reference.push().getKey();

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("postid",anonymouspostid);
                        hashMap.put("postimage",myUrl);
                        hashMap.put("title",title.getEditText().getText().toString().trim());
                        hashMap.put("description",description.getText().toString());
                        hashMap.put("category",item);
                        hashMap.put("publisher",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        hashMap.put("date", saveCurrentDate);
                        hashMap.put("time", saveCurrentTime);
                        hashMap.put("publishername", anonymous.getText().toString());
                        hashMap.put("picpos", pos);



                        reference.child(anonymouspostid).setValue(hashMap);

                        final List<String> mention = new ArrayList<String>(description.getMentions());


                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users");
                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    User user=snapshot.getValue(User.class);
                                    for (String username : mention) {

                                        if (user.getUsername().equals(username)) {
                                            String userid = user.getId();
                                            addNotifications(userid, anonymouspostid);

                                        }

                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        closeKeyboard();
                        progressDialog.dismiss();


                        startActivity(new Intent(AnonymousPostActivity.this,HomeActivity.class));
                        finish();

                    }else
                    {
                        closeKeyboard();

                        Toast.makeText(AnonymousPostActivity.this,"Failed",Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AnonymousPostActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            closeKeyboard();
            progressDialog.dismiss();


            Toast.makeText(this,"No Image Selected! Or No Category Selected!",Toast.LENGTH_LONG).show();

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            addphoto.setImageURI(imageUri);

        }
        else
        {
            Toast.makeText(this,"Something gone wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void socialDesc()
    {
        defaultMentionAdapter = new MentionArrayAdapter<>(this);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if (!snapshot.child("imageurl").getValue().toString().isEmpty()){
                        defaultMentionAdapter.add(new Mention(snapshot.child("username").getValue().toString(),snapshot.child("fullname")
                                .getValue().toString(),snapshot.child("imageurl").getValue().toString()));
                    }

                    else {
                        defaultMentionAdapter.add(new Mention(snapshot.child("username").getValue().toString(),snapshot.child("fullname")
                                .getValue().toString()));
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        description.setMentionAdapter(defaultMentionAdapter);


        description.setOnMentionClickListener(new SocialView.OnClickListener() {
            @Override
            public void onClick(@NonNull SocialView view, @NonNull CharSequence text) {
                Log.d("mention", text.toString());
                Toast.makeText(getApplicationContext(),"clicked:"+text,Toast.LENGTH_SHORT).show();

            }
        });





    }
    private void addNotifications(String userid , String postid)
    {
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("userid", firebaseUser.getUid());
        hashMap.put("text", "mentioned you in their post");
        hashMap.put("postid", postid);
        hashMap.put("ispost", false);
        hashMap.put("isapost", true);
        hashMap.put("time", saveCurrentTime);

        reference.push().setValue(hashMap);

        prepareNotifications(""+postid,
                ""+CurrentUsername + " (Anonymous)",
                "mentioned you in their anonymous post",
                "AnonymousMentionNotification",
                ""+userid,
                ""+myUrl);

    }

    private void prepareNotifications(final String pId, final String title, final String description, final String notifcationType, final String hisUid, final String pImage)
    {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Token token = ds.getValue(Token.class);
                    String NOTIFICATION_TITLE = title;
                    String NOTIFICATION_MESSAGE = description;
                    String NOTIFICATION_TYPE = notifcationType;

                    //prepare json what to send, where to send
                    JSONObject notificationJo = new JSONObject();
                    JSONObject notificationBodyJo= new JSONObject();

                    try {
                        notificationBodyJo.put("notificationType",NOTIFICATION_TYPE);
                        notificationBodyJo.put("sender",firebaseUser.getUid());
                        notificationBodyJo.put("pId",pId);
                        notificationBodyJo.put("pTitle" ,NOTIFICATION_TITLE);
                        notificationBodyJo.put("pDescription",NOTIFICATION_MESSAGE);
                        notificationBodyJo.put("hisUid",hisUid);
                        notificationBodyJo.put("pImage",pImage);





                        notificationJo.put("to", token.getToken());
                        notificationJo.put("data", notificationBodyJo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sendNotification(notificationJo);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void sendNotification(JSONObject notificationJo)
    {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", notificationJo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("FCM_RESPONSE","onResponse" + response.toString() );



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(AnonymousPostActivity.this,""+ error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String>headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization","key=AAAAse1rt0M:APA91bHMzH6AUIqM40WH6zWIiUjd_ruUmzwtSZ2S5-YFx230PyM6awlU-UTwnE2slGPrkF5Jc_v_QPUg_3-pPXWGmW3QCeYBxxk5Osj_aI1Oo_oXmfTaRRXSF0gNNe0Ay4eo4bxGEXxZ");

                return headers;
            }
        };

        //enqeue the volley
        Volley.newRequestQueue(AnonymousPostActivity.this).add(jsonObjectRequest);

    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
