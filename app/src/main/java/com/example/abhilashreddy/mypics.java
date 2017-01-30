package com.example.abhilashreddy.firebase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.PendingIntent.getActivity;

public class mypics extends AppCompatActivity {
    public String imgurl;
    //private StorageReference mypicsref;
    //public ImageView image;
    //private Firebase imglistview;
    public ListView listView;
    objects objclass = new objects();
    public TextView newnametext;
    public TextView newdatetext;
    public objects o;
    //public AdapterView<?> adapterView;
   // public static ArrayList<objects> gpsdataarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypics);
        Firebase.setAndroidContext(mypics.this);

        listView = (ListView) findViewById(R.id.listview);
        final DatabaseReference imglistview = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-6ff34.firebaseio.com/root");
        final FirebaseListAdapter<objects> firebaseListAdapter = new FirebaseListAdapter<objects>(this, objects.class, R.layout.picslayout, imglistview) {
            @Override
            public void populateView(View v, objects model, int position) {
//                TextView t=(TextView) v.findViewById(R.id.txt1);
//                //double x=(double)model.getLat();
//                String x=Double.toString(model.getLongi());
//               t.setText(x);
                //imgurl=model;
                //imgurl=listView.getItem(position);
                newnametext=(TextView)v.findViewById(R.id.newnameid);
                newdatetext=(TextView)v.findViewById(R.id.newdateid);
                Log.e("this",""+model.getValuetext());
                Log.e("this",""+model.getDateitem());


                newnametext.setText("Name : "+model.getDateitem());
                newdatetext.setText("Date : "+model.getValuetext());

                ImageView pimg = (ImageView) v.findViewById(R.id.finalimg);

//             image=(ImageView)v.findViewById(R.id.img);
                Picasso.with(mypics.this).load(model.getUrl()).fit().centerCrop().into(pimg);
                //Toast.makeText(mypics.this, model.getLat()+" , "+model.getLongi(), Toast.LENGTH_SHORT).show();
//                gpsdataarray = new ArrayList<objects>();
//                for (int x = 0; x < listView.getCount(); x++) {
//                    o = (objects) listView.getItemAtPosition(x);
//                    gpsdataarray.add(o);
//                    Log.e("mypics","LAT: "+o.getLat()+" Long: "+o.getLongi());
//                }

            }
        };
        listView.setAdapter(firebaseListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(mypics.this,"position"+adapterView.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                // objects o=adapterView.getItemAtPosition()
                o = (objects) adapterView.getItemAtPosition(i);
                //for(int x=0;i<)
                Log.e("mypics", "" + imglistview);
                //Log.e("mypics",""+firebaseListAdapter);
                Log.e("mypics", "" + adapterView);
                Log.e("mypics", "" + i);
                Log.e("mypics", "" + listView.getAdapter());
                Log.e("mypics", "" + listView.getCount());

//
                final CharSequence[] items = {
                        "View Image", "View Location",
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mypics.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            if(o.getUrl().equals("null")){
                                Toast.makeText(mypics.this,"No image to view",Toast.LENGTH_SHORT).show();
                            }
                            else{
                            Intent fullimg = new Intent(mypics.this, fullimage.class);
//                            fullimg.putExtra("latitude",o.getLat());
//                            fullimg.putExtra("longitude",o.getLongi());
                            Log.e("mypics", "Lat " + o.getLat() + " Long" + o.getLongi());
                            fullimg.putExtra("downloadurl", o.getUrl());
                            startActivity(fullimg);}// Do something with the selection
                        }
                        if (item == 1) {

//                            for (int x=0;x<listView.getCount();x++){
//                                o = (objects)adapterView.getItemAtPosition(x);
                            Intent mapsintent = new Intent(mypics.this, MapsActivity.class);
                            mapsintent.putExtra("latitude", o.getLat());
                            mapsintent.putExtra("longitude", o.getLongi());
                            mapsintent.putExtra("test","tests");
                            startActivity(mapsintent);
                        }

                        //mDoneButton.setText(items[item]);
                        //Toast.makeText(cont,item,Toast.LENGTH_SHORT);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }

        });

//        for (int x=0;x<listView.getCount();x++){
//            //AdapterView adapterView;
//            o = (objects) adapterView.getItemAtPosition(x);
//            Log.e("mypics","LAT: "+o.getLat()+" Long: "+o.getLongi());
//        }





    }

//    public ArrayList<objects> getmyarray() {
//        return gpsdataarray;
//    }

}




