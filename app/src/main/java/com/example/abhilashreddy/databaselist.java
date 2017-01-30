package com.example.abhilashreddy.firebase;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class databaselist extends AppCompatActivity {
public ListView databaselistview;
    public camera c;
    public String item;
//    public TextView lattextview;
//    public TextView longtextview;
//    public TextView timestamptextview;
    dbhelper ddbhelper=new dbhelper(this);
    private RadioButton rareradio;private  RadioButton exoticradio;
    private ProgressDialog progressDialog;
    public Firebase path;
    private String finalvalx;
    private RadioButton catradio;
    private EditText note;
    private Spinner category;
    private Spinner rarespinner;
    private String x1;private String x2;private String x3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databaselist);
        databaselistview=(ListView)findViewById(R.id.databselistview);
        Firebase.setAndroidContext(databaselist.this);
        final SQLiteDatabase del=ddbhelper.getWritableDatabase();
        c=new camera(); progressDialog = new ProgressDialog(this);
        path = new Firebase("https://shoppinglist-6ff34.firebaseio.com/root");
//        lattextview=(TextView)findViewById(R.id.latid);
//        longtextview=(TextView)findViewById(R.id.longid);
//        timestamptextview=(TextView)findViewById(R.id.timestamp);
//        ArrayAdapter<latlong> databaselistadapter=new ArrayAdapter<latlong>(this,R.layout.databasearraylayout,ddbhelper.getarraydata());
//         databaselistview.setAdapter(databaselistadapter);
        latlongadapter localadapter=new latlongadapter(this,ddbhelper.getarraydata());
        databaselistview.setAdapter(localadapter);
        databaselistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(databaselist.this,"clicked",Toast.LENGTH_SHORT).show();

                final latlong dobj= (latlong) adapterView.getItemAtPosition(i);
                if(c!=null){
                    Log.e("this","it has something");
                }
                CharSequence[] options={"Upload","Delete"};
                final AlertDialog.Builder newbuilder=new AlertDialog.Builder(databaselist.this);
                newbuilder.setTitle("Make your selection");
                newbuilder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   if(which==0){
                       final Dialog newdialog = new Dialog(databaselist.this);
                       newdialog.setContentView(R.layout.updatedlayout);
                       newdialog.setTitle("Enter Details");

//            //TextView exotic=(TextView) newdialog.findViewById(R.id.exotic);
                       note = (EditText) newdialog.findViewById(R.id.exoticedittext);
//            TextView cattext=(TextView) newdialog.findViewById(R.id.categorytext);
                       category = (Spinner) newdialog.findViewById(R.id.categoryspinner);
                       catradio = (RadioButton) newdialog.findViewById(R.id.categoryradiobutton);
                       rareradio=(RadioButton)newdialog.findViewById(R.id.rareradiobutton);
                       exoticradio=(RadioButton)newdialog.findViewById(R.id.exoticradiobutton);
//
//                if(category.)
//            TextView raretext=(TextView)newdialog.findViewById(R.id.raretext);
                       rarespinner = (Spinner) newdialog.findViewById(R.id.rare);
                       if (catradio.isChecked()) {
                           category.setVisibility(View.VISIBLE);
                           rarespinner.setVisibility(View.GONE);
                           note.setVisibility(View.GONE);
                       }
                       Button save = (Button) newdialog.findViewById(R.id.save1);
                       final ImageView cropedimage = (ImageView) newdialog.findViewById(R.id.imageview);
                       // Bundle extras = data.getExtras();
                       String[] myarray = {"Raccon", "Virginia opossum", "Deer", "Woodchuck", "Armadilo", "Coyote", "Skunk"};
                       ArrayAdapter<CharSequence> spinneradapter = new ArrayAdapter<CharSequence>(databaselist.this, android.R.layout.simple_spinner_dropdown_item, myarray);
                       spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       category.setAdapter(spinneradapter);
                       String[] rarearray = {"Bobcats", "Mountain Lions", "Black Bears", "Foxes", "Wolves", "Beavers", "Minks", "Moles", "Voles", "Badgets", "Otters", "Shrews"};
                       ArrayAdapter<CharSequence> rarespinneradapter = new ArrayAdapter<CharSequence>(databaselist.this, android.R.layout.simple_spinner_dropdown_item, rarearray);
                       spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       rarespinner.setAdapter(rarespinneradapter);
//
                       newdialog.show();
                       save.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               x1=catradio.getText().toString();
                               x2=rareradio.getText().toString();
                               x3=exoticradio.getText().toString();
                               if(catradio.isChecked()){
                                   finalvalx=x1;
                               }else if(rareradio.isChecked()){finalvalx=x2;}else{finalvalx=x3;}

                               final String val1 = note.getText().toString();
                               final String val2 = category.getSelectedItem().toString();
                               final String  val3= rarespinner.getSelectedItem().toString();
//                        if(!val1.isEmpty()){
//                            item=val1;
//                        }
//                        if(!val2.isEmpty()){
//                            item=val2;
//                        }
//                        if(!val3.isEmpty()){
//                            item=val3;
//                        }
//
                               if(catradio.isChecked()){
                                   item=val2;
                               }
                               else if(rareradio.isChecked()){
                                   item=val3;
                               }
                               else {
                                   item=val1;
                               }
                               progressDialog.setMessage("Uploading...");
                               progressDialog.show();
                               progressDialog.dismiss();
                               Toast.makeText(databaselist.this, "uploading finished", Toast.LENGTH_SHORT).show();
                               //final Uri download = taskSnapshot.getDownloadUrl();
                               Firebase child1 = path.push();
//
                               child1.child(c.myurl).setValue("null");

                               // child1.child("new").setValue("new");
                               child1.child("lat").setValue(dobj.getLocallat());
                               //child1.child("url").setValue(download.toString());
                               child1.child("longi").setValue(dobj.getLocallongi());
                               child1.child("valuetext").setValue(dobj.getTimestamp());
                               child1.child("dateitem").setValue(item);
                               child1.child("selectedstring").setValue(finalvalx);

//                        StorageReference storageReference = firebaseStorage.child("photos").child(reusedata.getLastPathSegment());
//                        storageReference.putFile(reusedata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                progressDialog.dismiss();
//                                Toast.makeText(databaselist.this, "uploading finished", Toast.LENGTH_SHORT).show();
//                                final Uri download = taskSnapshot.getDownloadUrl();
//                                Firebase child1 = c.imgref.push();
////
//                                child1.child(c.myurl).setValue(download.toString());
//
//                                // child1.child("new").setValue("new");
//                                child1.child("lat").setValue(dobj.getLocallat());
//                                //child1.child("url").setValue(download.toString());
//                                child1.child("longi").setValue(dobj.getLocallongi());
//                                child1.child("text").setValue(text);
//                                child1.child("item").setValue(item);
//
//
//
//                                //imgref.push().setValue(download.toString());//adds downloadurl to DB
//                                //Picasso.with(camera.this).load(download).fit().centerCrop().into(myimg);
//
//                            }
//                        });
                               newdialog.dismiss();
                               del.delete(dbhelper.table_name,dbhelper.column_id+" = "+dobj.getUnique(),null);
                           }
                       });
                       //c.reusablecode(dobj.getLocallat(),dobj.getLocallongi());
                       Log.e("this",""+dobj.getUnique());



                   }
                        if (which==1){
                            del.delete(dbhelper.table_name,dbhelper.column_id+" = "+dobj.getUnique(),null);

                        }
                    }
                   });
                AlertDialog alerts = newbuilder.create();
                alerts.show();

                    }
                });

    }
    public void radiobuttonselection(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.categoryradiobutton:
                if (checked)
                    category.setVisibility(View.VISIBLE);
                rarespinner.setVisibility(View.GONE);
                note.setVisibility(View.GONE);
                Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rareradiobutton:
                if (checked)
                    category.setVisibility(View.GONE);
                rarespinner.setVisibility(View.VISIBLE);
                note.setVisibility(View.GONE);
                Toast.makeText(this, "working2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exoticradiobutton:
                if (checked)
                    category.setVisibility(View.GONE);
                rarespinner.setVisibility(View.GONE);
                note.setVisibility(View.VISIBLE);
                Toast.makeText(this, "working3", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
