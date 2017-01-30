package com.example.abhilashreddy.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextemail;
    private EditText editTextpass;
    private Button signupbtn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private CheckBox checkBox;
    private String key="pre";
    private boolean val;
    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(!com.google.firebase.FirebaseApp.getApps(this).isEmpty()){
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        }
        editTextemail=(EditText)findViewById(R.id.edittextemail);
        editTextpass=(EditText)findViewById(R.id.editTextpass);
        signupbtn=(Button)findViewById(R.id.button);
        editTextpass.setOnClickListener(this);
        editTextemail.setOnClickListener(this);
        signupbtn.setOnClickListener(this);
        String email=editTextemail.getText().toString();
        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();
        SharedPreferences restoresp=getSharedPreferences(key,MODE_PRIVATE);
        String getemail=restoresp.getString("email","");
        String getpass=restoresp.getString("password","");
        //if(checkBox.isChecked()==val){
        editTextemail.setText(getemail);
        editTextpass.setText(getpass);
        //}


        
    }
    public void register(){

        String email=editTextemail.getText().toString();
        String password=editTextpass.getText().toString();
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        val=checkBox.isChecked();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"email field is empty",Toast.LENGTH_SHORT).show();
            return ;//Toast.makeText(this,"email field is empty",Toast.LENGTH_SHORT).show();;
        }
        if(password.length()<6){
            Toast.makeText(this,"Password is too short",Toast.LENGTH_SHORT).show();
            return;
        }
        if(val==true){
       sp=getSharedPreferences(key,MODE_PRIVATE);
        SharedPreferences.Editor edit=sp.edit();
        edit.putString("email",email);
        edit.putString("password",password);
        edit.commit();}
        progressDialog.setMessage("Logging in....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent=new Intent(MainActivity.this,camera.class);
                        startActivity(intent);


                }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Unsucessfull login",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==signupbtn){
            register();

        }
    }
}
