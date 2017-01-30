package com.example.abhilashreddy.firebase;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.getDefault;

/**
 * Created by ABHILASH REDDY on 11/6/2016.
 */
public class latlongadapter extends ArrayAdapter<latlong> {

    public latlongadapter(Activity context, ArrayList<latlong> d) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, d);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.databasearraylayout, parent, false);
        }
        latlong val = getItem(position);
        TextView lattextview=(TextView)listItemView.findViewById(R.id.latid);
        lattextview.setText("Location : "+getCompleteAddressString(val.getLocallat(),val.getLocallongi()));
//        TextView longtextview=(TextView)listItemView.findViewById(R.id.longid);
//        longtextview.setText("Longitude : "+val.getLocallongi());
        TextView timestamp=(TextView)listItemView.findViewById(R.id.timestamp);
        timestamp.setText("Timestamp : "+ val.getTimestamp());
        return  listItemView;
    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                //Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
               // Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
           // Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
}
