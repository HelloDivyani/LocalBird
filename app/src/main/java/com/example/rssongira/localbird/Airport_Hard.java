package com.example.rssongira.localbird;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

/**
 * Created by RSSongira on 4/7/2017.
 */
public class Airport_Hard extends FragmentActivity implements OnMapReadyCallback {
    TextView name;
    TextView place;
    GoogleMap myGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(googlePlayServiceAvailable())
        {
            //Toast.makeText(this, "Perfect", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.placesdetails_list);

            intitMap();

        }
        else
        {
            // No google Maps
            Toast.makeText(Airport_Hard.this, "Sorry Map Service Unavailable", Toast.LENGTH_SHORT).show();

        }
        name  = (TextView)findViewById(R.id.name);
        name.setText("Surat Domestic Airport");
        place = (TextView)findViewById(R.id.place_id);
        place.setText("Dumas Road,near Magdalla,Surat,Gujarat 395007");


}
    public void GPSIntent(View v)
    {
       // Toast.makeText(Airport_Hard.this," GPS CLICK Lat :21.1206+"Long : "+pln,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=21.1206,72.7424"));
        startActivity(intent);

    }

    private void intitMap() {
        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                myGoogleMap = googleMap; // here you set your Google map
                //  String tlag = b9.getString("lat");
                // String tlng = b9.getString("lng");
               /* googleMap.addMarker(new MarkerOptions().position(TIMES_SQUARE)
                        .title("Lets Check").snippet("Race Start: 9:00 AM CST")
                        .draggable(true));**/
                //Toast.makeText(Airport_Hard.this,"On Map Reday Lat :"+pl+"Long : "+,Toast.LENGTH_SHORT).show();
                LatLng myMarker = new LatLng(21.1206,72.7424);
                myGoogleMap.addMarker(new MarkerOptions().position(myMarker)
                        .title("Marker"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(myMarker));



                goToLocationZoom(21.1206,72.7424,15);

                // here you do the rest of your calculations with your map
            }
        });

        // This method automatically initializes map system system and view
    }

    private void goToLocationZoom(double lat, double lg,float zoom)
    {
        LatLng ll = new LatLng(lat,lg);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        myGoogleMap.moveCamera(update);
        // Camera Update defines Camera move Used using CameraUpdateFactory

    }

    private boolean googlePlayServiceAvailable() {
        GoogleApiAvailability api= GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS)
        {
            return true;
        }
        else if(api.isUserResolvableError(isAvailable))
        {
            Dialog dialog = api.getErrorDialog(this,isAvailable,0);
            // 0 is request Code
            dialog.show();

        }
        else
        {
            Toast.makeText(this,"Cannot play services",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
    }
}