package com.example.abhilashreddy.firebase;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class camera extends AppCompatActivity implements View.OnClickListener {
    public final static int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION =1 ;
    //mypics arrayinstance=new mypics();
    public static ArrayList<objects> updatedarray;
    public Button camera;
    public int startvalue=0;
    //public Uri uri;
    public String finalx;
    public String item;
    public String myurl = "url";
    public ImageView myimg;
    public double latitude;
    public double longitude;
    public Spinner category;
    public Spinner rarespinner;
    public EditText note;
    public RadioButton catradio;public RadioButton rareradio;public  RadioButton exoticradio;
    public Button mymaps;
    public objects obj;
    public MarshMallowPermission marshMallowPermission;
    NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this);
    private Button mypics;
    private StorageReference firebaseStorage;
    private ProgressDialog progressDialog;
    public Firebase imgref;
    public String lastChildKey;
    public int count=-1;
    public int num=0;
    public Button notebutton;
    public dbhelper mdbhelper;
    public Button recordlistbutton;
    public Bundle extras;
    public Uri reusedata;
    public Uri preinsertedUri;
    public int row;public String x1;public String x2;public String x3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Firebase.setAndroidContext(camera.this);
        mdbhelper=new dbhelper(this);
       // rareradio=(RadioButton)

//        if(!com.google.firebase.FirebaseApp.getApps(camera.this).isEmpty()){
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        }
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        camera = (Button) findViewById(R.id.cam);
        camera.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        myimg = (ImageView) findViewById(R.id.viewimg);
        notebutton=(Button)findViewById(R.id.record);
        recordlistbutton=(Button)findViewById(R.id.recordlist);
        recordlistbutton.setOnClickListener(this);
        notebutton.setOnClickListener(this);
        imgref = new Firebase("https://shoppinglist-6ff34.firebaseio.com/root");

        imgref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updatedarray = new ArrayList<objects>();//Declaration of arraylist
                for (DataSnapshot savedobjects : dataSnapshot.getChildren()) {
                    obj = savedobjects.getValue(objects.class);//why to declare the class inside
                    updatedarray.add(obj);
                }
                count++;
                Log.e("camera.this","size"+ updatedarray.size()+" "+ dataSnapshot.getChildrenCount());
                if(count==6){
                    builder.setTicker("Ticker Title");
                    builder.setContentTitle("Missouri Mammal Tracker");
                    builder.setContentText("You got  new picture to view");
                    builder.setSmallIcon(R.drawable.ic_launcher);
                    //.setContentIntent(pIntent);
                    Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    builder.setSound(notificationSound);
                    Notification noti = builder.build();
                    PendingIntent contentIntent = PendingIntent.getActivity(camera.this, 0,
                            new Intent(camera.this, notificationactivity.class), PendingIntent.FLAG_ONE_SHOT);


                    builder.setContentIntent(contentIntent);
                    noti.flags = Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(123, noti);
                    Log.e("camera","working "+count);
                    count=0;
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


//
        mypics = (Button) findViewById(R.id.mypics);
        mypics.setOnClickListener(this);
        //gps=new gpscoordinates(camera.this);
        //gps.l=(Button)findViewById(R.id.cam);
        GPSTracker gps = new GPSTracker(camera.this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        mymaps = (Button) findViewById(R.id.mymaps);
        mymaps.setOnClickListener(this);
        marshMallowPermission = new MarshMallowPermission(this);
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION );
        } else {
            preinsertedUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
            Log.e("Camera URI", preinsertedUri.toString());
        }

    }

    @Override
    public void onClick(View view) {

        if (view == camera) {

            if (!marshMallowPermission.checkPermissionForCamera()) {
                marshMallowPermission.requestPermissionForCamera();
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Uri.withAppendedPath(mLocationForPhotos, targetFilename));
                //if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 1001);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 600);
                intent.putExtra("outputY", 600);
                intent.putExtra("scale", true);
            }

        }
        if(view==notebutton){
           // SQLiteDatabase ldb=mdbhelper.getReadableDatabase();
            if(mdbhelper.getarraydata().size()==0){
                row=0;
            }
            else{
               latlong olatlomg= mdbhelper.getarraydata().get(mdbhelper.getarraydata().size()-1);
                row=olatlomg.getUnique()+1;

            }
            SQLiteDatabase db=mdbhelper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(dbhelper.column_lat,latitude);
            contentValues.put(dbhelper.column_long,longitude);
            contentValues.put(dbhelper.column_id,row);
            contentValues.put(dbhelper.column_time,DateFormat.format("dd-MM-yy", Calendar.getInstance().getTime()).toString());
            db.insert(dbhelper.table_name,null,contentValues);
            Toast.makeText(this,"Location Saved",Toast.LENGTH_SHORT).show();
        }
        if (view == mypics) {
            Intent mypicsintent = new Intent(camera.this, mypics.class);
            startActivity(mypicsintent);
        }
        if (view == mymaps) {
            if (!marshMallowPermission.checkPermissionForCoarseLocation()) {
                marshMallowPermission.requestPermissionForCoarseLocation();
            } else if (!marshMallowPermission.checkPermissionForFineLocation()) {
                marshMallowPermission.requestPermissionForFineLocation();
            } else {
                startActivity(new Intent(camera.this, finalMapsActivity.class));
            }
//
        }
        if (view==recordlistbutton){
            if(mdbhelper.getarraydata().size()==0){
                Toast.makeText(this,"You have no Saved items",Toast.LENGTH_SHORT).show();
            }
            else{
                startActivity(new Intent(camera.this,databaselist.class));
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            if ((data != null)) {
                final Intent i = new Intent(this, camera.class);
                if(data.getData() != null) {
                    i.putExtra("mydata", data.getData());
                   // Log.d("Camera Reusedata: ", reusedata.toString() );
                    reusedata = data.getData();
                }
                else{
                    Log.d("Camera Reusedata null", "Reuse data is null");
                    i.putExtra("mydata", preinsertedUri);
                    reusedata = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());

                }

                 extras = data.getExtras();
                reusablecode(latitude,longitude);
//                final Dialog newdialog = new Dialog(this);
//                newdialog.setContentView(R.layout.updatedlayout);
//                newdialog.setTitle("Enter Details");
//
////            //TextView exotic=(TextView) newdialog.findViewById(R.id.exotic);
//                note = (EditText) newdialog.findViewById(R.id.exoticedittext);
////            TextView cattext=(TextView) newdialog.findViewById(R.id.categorytext);
//                category = (Spinner) newdialog.findViewById(R.id.categoryspinner);
//                catradio = (RadioButton) newdialog.findViewById(R.id.categoryradiobutton);
////
////                if(category.)
////            TextView raretext=(TextView)newdialog.findViewById(R.id.raretext);
//                rarespinner = (Spinner) newdialog.findViewById(R.id.rare);
//                if (catradio.isChecked()) {
//                    category.setVisibility(View.VISIBLE);
//                    rarespinner.setVisibility(View.GONE);
//                    note.setVisibility(View.GONE);
//                }
//                Button save = (Button) newdialog.findViewById(R.id.save1);
//                final ImageView cropedimage = (ImageView) newdialog.findViewById(R.id.imageview);
//                // Bundle extras = data.getExtras();
//                String[] myarray = {"Raccon", "Virginia opossum", "Deer", "Woodchuck", "Armadilo", "Coyote", "Skunk"};
//                ArrayAdapter<CharSequence> spinneradapter = new ArrayAdapter<CharSequence>(camera.this, android.R.layout.simple_spinner_dropdown_item, myarray);
//                spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                category.setAdapter(spinneradapter);
//                String[] rarearray = {"Bobcats", "Mountain Lions", "Black Bears", "Foxes", "Wolves", "Beavers", "Minks", "Moles", "Voles", "Badgets", "Otters", "Shrews"};
//                ArrayAdapter<CharSequence> rarespinneradapter = new ArrayAdapter<CharSequence>(camera.this, android.R.layout.simple_spinner_dropdown_item, rarearray);
//                spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                rarespinner.setAdapter(rarespinneradapter);
//                final Bitmap Image;
//                final ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                if (extras != null) {
//                    Image = extras.getParcelable("data");
//                    // convert bitmap to byte
//
//                    Image.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    cropedimage.setImageBitmap(Image);
//                }
//                newdialog.show();
//                save.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //uri = data.getData();
//                        // Uri x=i.getParcelableExtra("data");
//                        // if (data.getData() != null) {
//                        final String text = note.getText().toString();
//                        final String item = category.getSelectedItem().toString();
////
//                        progressDialog.setMessage("Uploading...");
//                        progressDialog.show();
//                        StorageReference storageReference = firebaseStorage.child("photos").child(data.getData().getLastPathSegment());
//                        storageReference.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                progressDialog.dismiss();
//                                Toast.makeText(camera.this, "uploading finished", Toast.LENGTH_SHORT).show();
//                                final Uri download = taskSnapshot.getDownloadUrl();
//                                Firebase child1 = imgref.push();
////
//                                child1.child(myurl).setValue(download.toString());
//
//                                // child1.child("new").setValue("new");
//                                child1.child("lat").setValue(latitude);
//                                //child1.child("url").setValue(download.toString());
//                                child1.child("longi").setValue(longitude);
//                                child1.child("text").setValue(text);
//                                child1.child("item").setValue(item);
//
//
//
//                                //imgref.push().setValue(download.toString());//adds downloadurl to DB
//                                Picasso.with(camera.this).load(download).fit().centerCrop().into(myimg);
//
//                            }
//                        });
//                        newdialog.dismiss();
//                    }
//                });
////

            }
        }
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
    public  void reusablecode(final Double x, final Double y){
        final Dialog newdialog = new Dialog(this);
        newdialog.setContentView(R.layout.updatedlayout);
        newdialog.setTitle("Enter Details");

//            //TextView exotic=(TextView) newdialog.findViewById(R.id.exotic);
        note = (EditText) newdialog.findViewById(R.id.exoticedittext);
//            TextView cattext=(TextView) newdialog.findViewById(R.id.categorytext);
        category = (Spinner) newdialog.findViewById(R.id.categoryspinner);
        catradio = (RadioButton) newdialog.findViewById(R.id.categoryradiobutton);
     rareradio=(RadioButton)newdialog.findViewById(R.id.rareradiobutton);
        exoticradio=(RadioButton)newdialog.findViewById(R.id.exoticradiobutton);

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
        ArrayAdapter<CharSequence> spinneradapter = new ArrayAdapter<CharSequence>(camera.this, android.R.layout.simple_spinner_dropdown_item, myarray);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(spinneradapter);
        String[] rarearray = {"Bobcats", "Mountain Lions", "Black Bears", "Foxes", "Wolves", "Beavers", "Minks", "Moles", "Voles", "Badgets", "Otters", "Shrews"};
        ArrayAdapter<CharSequence> rarespinneradapter = new ArrayAdapter<CharSequence>(camera.this, android.R.layout.simple_spinner_dropdown_item, rarearray);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rarespinner.setAdapter(rarespinneradapter);
        final Bitmap Image;
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (extras != null) {
            Image = extras.getParcelable("data");
            // convert bitmap to byte

            Image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            cropedimage.setImageBitmap(Image);
        }
        newdialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //uri = data.getData();
                // Uri x=i.getParcelableExtra("data");
                // if (data.getData() != null) {
                 x1=catradio.getText().toString();
                x2=rareradio.getText().toString();
                x3=exoticradio.getText().toString();
                if(catradio.isChecked()){
                    finalx=x1;
                }else if(rareradio.isChecked()){finalx=x2;}
                else{finalx=x3;}

                final String val1 = note.getText().toString();
                final String val2 = category.getSelectedItem().toString();
                final String  val3= rarespinner.getSelectedItem().toString();
//                if(!val1.isEmpty()){
//                    item=val1;
//                }
//                if(!val2.isEmpty()){
//                    item=val2;
//                }
//                if(!val3.isEmpty()){
//                    item=val3;
//                }
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
                StorageReference storageReference = firebaseStorage.child("photos").child(reusedata.getLastPathSegment());
                storageReference.putFile(reusedata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(camera.this, "uploading finished", Toast.LENGTH_SHORT).show();
                        final Uri download = taskSnapshot.getDownloadUrl();
                        Firebase child1 = imgref.push();
//
                        child1.child(myurl).setValue(download.toString());

                        // child1.child("new").setValue("new");
                        child1.child("lat").setValue(x);
                        //child1.child("url").setValue(download.toString());
                        child1.child("longi").setValue(y);
                        child1.child("valuetext").setValue(DateFormat.format("dd-MM-yy", Calendar.getInstance().getTime()).toString());
                        child1.child("dateitem").setValue(item);
                        child1.child("selectedstring").setValue(finalx);



                        //imgref.push().setValue(download.toString());//adds downloadurl to DB
                        //Picasso.with(camera.this).load(download).fit().centerCrop().into(myimg);

                    }
                });
                newdialog.dismiss();
            }
        });
//
    }
}