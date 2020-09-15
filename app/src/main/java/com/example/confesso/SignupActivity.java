package com.example.confesso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.ybs.passwordstrengthmeter.PasswordStrength;

import java.util.HashMap;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity implements TextWatcher {

    EditText fullname,email,password,cpassword;
    Button register;
    TextView txt_signin;
    private CircleImageView ProfileImage;
    final static int Gallery_Pick = 1;
    String str_username;



    private DatabaseReference UsersRef;
    private StorageReference UserProfileImageRef;

    String currentUserID;




    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;
    public static final String TAG = "MyActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

         fullname=findViewById(R.id.editname);
         email=findViewById(R.id.editemail);
         password=findViewById(R.id.editpass);
         cpassword=findViewById(R.id.editcpass);
        ProfileImage = findViewById(R.id.setup_profile_image);

       // firebaseUser = auth.getCurrentUser();
       // userid = firebaseUser.getUid();

        password.addTextChangedListener((TextWatcher) this);

        auth = FirebaseAuth.getInstance();
     //   currentUserID = auth.getCurrentUser().getUid();
       // UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        //UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");


        register=findViewById(R.id.butreg);
         txt_signin=findViewById(R.id.textsignin);

        Intent intent =getIntent();
        str_username = intent.getStringExtra("username");



         auth=FirebaseAuth.getInstance();
         txt_signin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(SignupActivity.this,SignInActivity.class));

             }
         });

         register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                  pd= new ProgressDialog(SignupActivity.this);
                  pd.setMessage("Please wait...");
                  pd.show();

                 String str_fullname= fullname.getText().toString();
                 String str_email= email.getText().toString();
                 String str_password= password.getText().toString();
                 String str_cpassword= cpassword.getText().toString();


                 if(TextUtils.isEmpty(str_fullname)|| TextUtils.isEmpty(str_email)||
                         TextUtils.isEmpty( str_password)|| TextUtils.isEmpty( str_cpassword))
                 {
                     pd.dismiss();

                     Toast.makeText(SignupActivity.this,"All fields are required!", Toast.LENGTH_SHORT).show();

                 }

                 else if(!isValidPassword(str_password))
                 {
                     pd.dismiss();

                     Toast.makeText(SignupActivity.this,"Password Not Valid", Toast.LENGTH_SHORT).show();

                 }

                 else if (!str_password.equals(str_cpassword))
                 {
                     pd.dismiss();
                     Toast.makeText(SignupActivity.this,"Your password do not match with confirm password..", Toast.LENGTH_SHORT).show();

                 }


                 else{

                     register(str_username,str_fullname,str_email,str_password);
                 }

             }
         });


       /* ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);
            }
        });

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("profileimage"))
                    {
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                       // Picasso.get().load(image).placeholder(R.drawable.signuplady).into(ProfileImage);
                        Picasso.get().load(image).fit().centerCrop()
                                .placeholder(R.drawable.signuplady)
                                .error(R.drawable.signuplady)
                                .into(ProfileImage);
                    }
                    else
                    {
                        Toast.makeText(SignupActivity.this, "Please select profile image first.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */



    }




    //@Override
  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null)
        {
            Uri ImageUri = data.getData();

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK)
            {
                pd.setTitle("Profile Image");
                pd.setMessage("Please wait, while we updating your profile image...");
                pd.show();
                pd.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();

                StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");

                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignupActivity.this, "Profile Image stored successfully to Firebase storage...", Toast.LENGTH_SHORT).show();

                            final String downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();

                            UsersRef.child("profileimage").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                Intent selfIntent = new Intent(SignupActivity.this, SignupActivity.class);
                                                startActivity(selfIntent);

                                                Toast.makeText(SignupActivity.this, "Profile Image stored to Firebase Database Successfully...", Toast.LENGTH_SHORT).show();
                                                //pd.dismiss();
                                            }
                                            else
                                            {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(SignupActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(this, "Error Occured: Image can not be cropped. Try Again.", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        }
    }*/







    private void register (final String username, final String fullname, final String email, final String password)
    {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        FirebaseUser firebaseuser = auth.getCurrentUser();
                                        String userid = firebaseuser.getUid();

                                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("id", userid);
                                        hashMap.put("username", username.toLowerCase());
                                        hashMap.put("fullname", fullname);
                                        hashMap.put("bio", "");
                                        hashMap.put("gender", "");
                                        hashMap.put("anonymous", "");
                                        hashMap.put("dob", "");
                                        hashMap.put("country", "");
                                        hashMap.put("imageurl","");
                                        hashMap.put("coverurl","");
                                        hashMap.put("mail",email);
                                        hashMap.put("phone","");




                                        //badmei referenceko use krke update krna hai hashmap
                                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    pd.dismiss();
                                                    Toast.makeText(SignupActivity.this,"You are authenticated successfully...Please verify your email id.", Toast.LENGTH_LONG).show();

                                                    Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });


                                    }
                                    else
                                    {
                                        Toast.makeText(SignupActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }


                                }
                            });

                        }
                        else if (!task.isSuccessful()) {

                            pd.dismiss();
                            Toast.makeText(SignupActivity.this,"There is a problem.."+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                            Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                        }
                        else
                        {
                            pd.dismiss();
                            Toast.makeText(SignupActivity.this,"You can't register with this email or password",Toast.LENGTH_SHORT).show();;

                        }

                    }
                });


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        updatePasswordStrengthView(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void updatePasswordStrengthView(String password) {

        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView strengthView = findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}





