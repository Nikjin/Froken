package com.example.confesso;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ybs.passwordstrengthmeter.PasswordStrength;

import java.util.regex.Pattern;

public class ChangePassActivity extends AppCompatActivity implements TextWatcher {

    EditText password,cpassword,oldpassword;
    Button butdone;
    ImageView back;

    ProgressDialog pd;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        password=findViewById(R.id.editpass);
        oldpassword=findViewById(R.id.editoldpass);

        cpassword=findViewById(R.id.editcpass);
        butdone=findViewById(R.id.butdone);
        back=findViewById(R.id.back);

        password.addTextChangedListener((TextWatcher) this);

        auth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        butdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd= new ProgressDialog(ChangePassActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                final String str_password= password.getText().toString();
                String str_cpassword= cpassword.getText().toString();
                String str_oldpassword= oldpassword.getText().toString();



                if(TextUtils.isEmpty(str_password)|| TextUtils.isEmpty( str_cpassword)|| TextUtils.isEmpty( str_oldpassword))
                {
                    pd.dismiss();

                    Toast.makeText(ChangePassActivity.this,"All fields are required!", Toast.LENGTH_SHORT).show();

                }

                else if(!isValidPassword(str_password))
                {
                    pd.dismiss();

                    Toast.makeText(ChangePassActivity.this,"Password Not Valid", Toast.LENGTH_SHORT).show();

                }

                else if (!str_password.equals(str_cpassword))
                {
                    pd.dismiss();
                    Toast.makeText(ChangePassActivity.this,"Your password do not match with confirm password..", Toast.LENGTH_SHORT).show();

                }


                else{

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),str_oldpassword);


                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                user.updatePassword(str_password)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    pd.dismiss();
                                                    Toast.makeText(getApplicationContext(),"Your Password has been changed",Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }

                                                else {
                                                    pd.dismiss();

                                                    Toast.makeText(getApplicationContext(),"Password could not be changed",Toast.LENGTH_SHORT).show();


                                                }
                                            }
                                        });

                            }

                            else
                            {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Enter correct current password",Toast.LENGTH_SHORT).show();



                            }
                        }
                    });

                }

            }
        });
    }


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
