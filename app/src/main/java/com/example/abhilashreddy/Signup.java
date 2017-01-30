package com.example.abhilashreddy.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity implements View.OnClickListener{
    private EditText sedittextemail;
    private EditText spassword;
    private EditText sconfirmpassword;
    private Button signup;
    private TextView signin;
    private FirebaseAuth firesignup;
    private ProgressDialog signupdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        sedittextemail=(EditText)findViewById(R.id.etEmail);
        spassword=(EditText)findViewById(R.id.etUserName);
        sconfirmpassword=(EditText)findViewById(R.id.etPass);
        signup=(Button)findViewById(R.id.btnSingup);
        signin=(TextView)findViewById(R.id.sin);
        signup.setOnClickListener(this);
        signin.setOnClickListener(this);
        firesignup=FirebaseAuth.getInstance();
        signupdialog=new ProgressDialog(Signup.this);
    }

    @Override
    public void onClick(View view) {
        if(view==signup){
            register();
        }
        if(view==signin){
            Intent pass=new Intent(Signup.this,MainActivity.class);
            startActivity(pass);
        }
    }
    public void register(){
        String getemail=sedittextemail.getText().toString();
        String getpassword=spassword.getText().toString();
        String getconfirmpassword=sconfirmpassword.getText().toString();
        if(TextUtils.isEmpty(getemail)&&TextUtils.isEmpty(getpassword)&&TextUtils.isEmpty(getconfirmpassword)){
            Toast.makeText(Signup.this,"Fields shouldn't be empty!",Toast.LENGTH_SHORT).show();
            return;

        }
        if(getpassword.length()<6&&getconfirmpassword.length()<6){
            Toast.makeText(this,"Password is too short",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!getconfirmpassword.equals(getpassword)){
            Toast.makeText(Signup.this,"Passwords doesn't match!",Toast.LENGTH_SHORT).show();
            return;
        }
        signupdialog.setMessage("Signing Up...");
        signupdialog.show();

      firesignup.createUserWithEmailAndPassword(getemail,getpassword)
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isComplete()){
                          signupdialog.dismiss();
                          Toast.makeText(Signup.this,"User created Successfully",Toast.LENGTH_SHORT).show();
                          Intent pass=new Intent(Signup.this,MainActivity.class);
                          startActivity(pass);
                      }
                  }
              }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              signupdialog.dismiss();
              e.printStackTrace();

          }
      });
    }
}
