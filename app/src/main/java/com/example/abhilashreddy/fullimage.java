package com.example.abhilashreddy.firebase;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class fullimage extends AppCompatActivity {
    public String showimgurl;
    public ImageView newfullimage;
    public ProgressDialog p;
    public int i;
//    public double mylatitude;
//    public double mylongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);
        p=new ProgressDialog(fullimage.this);
        p.setMessage("Loading...");
        p.show();
        //Log.v("fullimage",p.setMessage("Loading..."))
//        mylatitude=getIntent().getExtras().getDouble("latitude");
//        mylongitude=getIntent().getExtras().getDouble("longitude");
//        Log.e("fullimage","Lat: "+mylatitude+" Long: "+mylongitude);
         showimgurl=getIntent().getExtras().getString("downloadurl");
        newfullimage=(ImageView)findViewById(R.id.fullimg);
        Picasso.with(fullimage.this).load(showimgurl).fit().centerCrop().into(newfullimage);
//


    }

}
