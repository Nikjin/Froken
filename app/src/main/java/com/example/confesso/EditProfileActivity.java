package com.example.confesso;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.Model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {


    ImageView back,image_profile,image_cover;
    Button save,edit;
    TextView tv_change,username,anonymous,tv_delete;

    EditText fullname,bio,phone,gender,location,mail;

    FirebaseUser firebaseUser;

    FirebaseStorage mStorage;
    private Uri mImageUri,mCoverUri;
    private StorageTask uploadTask;
    StorageReference storageRef,storageRef1;
    private Uri filePath;
    String str_username,str_anonymous;

    private ValueEventListener mDbListener;
    private DatabaseReference reference;

    private final int PICK_IMAGE_REQUEST = 71;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        back= findViewById(R.id.back);
        image_profile= findViewById(R.id.image_profile);
        image_cover= findViewById(R.id.image_cover);
        save= findViewById(R.id.butsave);
        tv_change= findViewById(R.id.tv_change);
        tv_delete= findViewById(R.id.tv_delete);

        fullname= findViewById(R.id.fullname);
        username= findViewById(R.id.username);
        bio= findViewById(R.id.bio);
        anonymous= findViewById(R.id.anonymous);
        phone= findViewById(R.id.phone);
        gender= findViewById(R.id.gender);
        location= findViewById(R.id.location);
        mail= findViewById(R.id.mail);
        edit= findViewById(R.id.edit);


        mStorage= FirebaseStorage.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        storageRef1 = FirebaseStorage.getInstance().getReference("coveruploads");



        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        mDbListener= reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullname.setText(user.getFullname());
                username.setText(user.getUsername());
                bio.setText(user.getBio());
                anonymous.setText(user.getAnonymous());
                gender.setText(user.getGender());
                location.setText(user.getCountry());
                phone.setText(user.getPhone());
                mail.setText(user.getMail());

                Glide.with(getApplicationContext()).applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.edit_cover)
                        .fallback(R.drawable.edit_cover)
                        .fitCenter()).load(user.getImageurl()).into(image_profile);

                Glide.with(getApplicationContext()).applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.edit_cover)
                        .fallback(R.drawable.edit_cover)
                        .fitCenter())
                        .load(user.getCoverurl()).into(image_cover);






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

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(EditProfileActivity.this);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chooseImage();




            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog =new AlertDialog.Builder(EditProfileActivity.this).create();
                alertDialog.setTitle("Do you want to delete?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                                HashMap<String, Object>hashMap = new HashMap<>();
                                hashMap.put("imageurl","");

                                reference.updateChildren(hashMap);

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);


                                        StorageReference deleteFile = mStorage.getReferenceFromUrl(user.getImageurl());
                                        deleteFile.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "Image removed", Toast.LENGTH_SHORT).show();


                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                dialog.dismiss();
                            }
                        });

                alertDialog.show();

            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this,EditUsernameActivity.class));

            }
        });


       anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this,EditAnonymousnameActivity.class));

            }
        });





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                closeKeyboard();

                final ProgressDialog pd = new ProgressDialog(EditProfileActivity.this);
                pd.setMessage("Uploading");
                pd.show();

                updateProfile(fullname.getText().toString()
                ,bio.getText().toString(),gender.getText().toString(),phone.getText().toString()
                        ,mail.getText().toString(),location.getText().toString());





                uploadCover();
                uploadImage();

                pd.dismiss();

                finish();

            }


        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (reference!=null)
        {
            reference.removeEventListener(mDbListener);
        }
    }

    private void updateProfile(String fullname, String bio, String gender, String phone, String mail, String location) {

        /* final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show(); */

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("fullname",fullname);
        hashMap.put("bio",bio);
        hashMap.put("gender",gender);
        hashMap.put("phone",phone);
        hashMap.put("mail",mail);
        hashMap.put("country",location);

        reference.updateChildren(hashMap);

    //pd.dismiss();

    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private String getFileExtension1(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private void uploadImage()
    {
       /* final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show(); */

        if (mImageUri!= null)
        {
            final StorageReference filereference = storageRef.child(System.currentTimeMillis()
            +"."+getFileExtension(mImageUri));

            uploadTask = filereference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful())
                {
                    throw  task.getException();
                }
                return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                        HashMap<String, Object>hashMap = new HashMap<>();
                        hashMap.put("imageurl",""+myUrl);

                        reference.updateChildren(hashMap);
                        //pd.dismiss();
                    }
                    else
                    {
                        Toast.makeText(EditProfileActivity.this,"Failed",Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }
        else
        {
            //pd.dismiss();

            //Toast.makeText(this,"No Image Selected/Changed",Toast.LENGTH_SHORT).show();

        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadCover()
    {
        /*final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show(); */

        if (mCoverUri!= null)
        {
            final StorageReference filereference = storageRef1.child(System.currentTimeMillis()
                    +"."+getFileExtension1(mCoverUri));

            uploadTask = filereference.putFile(mCoverUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw  task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                        HashMap<String, Object>hashMap = new HashMap<>();
                        hashMap.put("coverurl",""+myUrl);

                        reference.updateChildren(hashMap);
                        //pd.dismiss();
                    }
                    else
                    {
                        Toast.makeText(EditProfileActivity.this,"Failed",Toast.LENGTH_SHORT).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }
        else
        {
            //pd.dismiss();

           // Toast.makeText(this,"No Cover Selected/Changed",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                image_profile.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }




        }
        else if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            mCoverUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mCoverUri);
                image_cover.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        else
        {
            Toast.makeText(this,"Something gone wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }





}
