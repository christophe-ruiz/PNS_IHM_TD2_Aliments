package com.example.projetihm.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Criteria;
import android.location.LocationProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetihm.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GpsActivity extends AppCompatActivity {
    LocationManager locationManager = null;
    private String fournisseur;
    private TextView latitude;
    private TextView longitude;
    private TextView Adresse;

    Geocoder geocoder = new Geocoder(this, Locale.getDefault());

    LocationListener ecouteurGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location localisation)
        {
            Toast.makeText(getApplicationContext(), fournisseur + " localisation", Toast.LENGTH_SHORT).show();

            Log.d("GPS", "localisation : " + localisation.toString());
            String coordonnees = String.format("Latitude : %f - Longitude : %f\n", localisation.getLatitude(), localisation.getLongitude());
            Log.d("GPS", coordonnees);
            String autres = String.format("Vitesse : %f - Altitude : %f - Cap : %f\n", localisation.getSpeed(), localisation.getAltitude(), localisation.getBearing());
            Log.d("GPS", autres);
            //String timestamp = String.format("Timestamp : %d\n", localisation.getTime());
            //Log.d("GPS", "timestamp : " + timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date(localisation.getTime());
            Log.d("GPS", sdf.format(date));

            String strLatitude = String.format("Latitude : %f", localisation.getLatitude());
            String strLongitude = String.format("Longitude : %f", localisation.getLongitude());
            latitude.setText(strLatitude);
            longitude.setText(strLongitude);

            List<Address> adresses = null;
            try
            {
                adresses = geocoder.getFromLocation(localisation.getLatitude(), localisation.getLongitude(), 1);
            }
            catch (IOException ioException)
            {
                Log.e("GPS", "erreur", ioException);
            } catch (IllegalArgumentException illegalArgumentException)
            {
                Log.e("GPS", "erreur " + coordonnees, illegalArgumentException);
            }

            if (adresses == null || adresses.size()  == 0)
            {
                Log.e("GPS", "erreur aucune adresse !");
            } else {
                Address adresse = adresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<String>();

                String strAdresse = adresse.getAddressLine(0) + ", " + adresse.getLocality();
                Log.d("GPS", "adresse : " + strAdresse);

                for(int i = 0; i <= adresse.getMaxAddressLineIndex(); i++)
                {
                    addressFragments.add(adresse.getAddressLine(i));
                }
                Log.d("GPS", TextUtils.join(System.getProperty("line.separator"), addressFragments));
                Adresse.setText(TextUtils.join(System.getProperty("line.separator"), addressFragments));
            }
        }

        @Override
        public void onProviderDisabled(String fournisseur)
        {
            Toast.makeText(getApplicationContext(), fournisseur + " désactivé !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String fournisseur)
        {
            Toast.makeText(getApplicationContext(), fournisseur + " activé !", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String fournisseur, int status, Bundle extras)
        {
            switch(status)
            {
                case LocationProvider.AVAILABLE:
                    Toast.makeText(getApplicationContext(), fournisseur + " état disponible", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(getApplicationContext(), fournisseur + " état indisponible", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(getApplicationContext(), fournisseur + " état temporairement indisponible", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), fournisseur + " état : " + status, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_activity);

        latitude = (TextView) findViewById(R.id.textViewLatitude);
        longitude = (TextView) findViewById(R.id.textViewLongitude);
        Adresse = (TextView) findViewById(R.id.textViewAdresse);

        Log.d("GPS", "onCreate");

        initialiserLocalisation();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        arreterLocalisation();
    }

    private void initialiserLocalisation()
    {
        if (locationManager == null)
        {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteres = new Criteria();

            // la précision  : (ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision)
            criteres.setAccuracy(Criteria.ACCURACY_FINE);

            // l'altitude
            criteres.setAltitudeRequired(true);

            // la direction
            criteres.setBearingRequired(true);

            // la vitesse
            criteres.setSpeedRequired(true);

            // la consommation d'énergie demandée
            criteres.setCostAllowed(true);
            //criteres.setPowerRequirement(Criteria.POWER_HIGH);
            criteres.setPowerRequirement(Criteria.POWER_MEDIUM);

            fournisseur = locationManager.getBestProvider(criteres, true);
            Log.d("GPS", "fournisseur : " + fournisseur);
        }

        if (fournisseur != null)
        {
            // dernière position connue
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                Log.d("GPS", "no permissions !");
                return;
            }

            Location localisation = locationManager.getLastKnownLocation(fournisseur);
            if(localisation != null)
            {
                // on notifie la localisation
                ecouteurGPS.onLocationChanged(localisation);
            }

            // on configure la mise à jour automatique : au moins 10 mètres et 15 secondes
            locationManager.requestLocationUpdates(fournisseur, 15000, 10, ecouteurGPS);
        }
    }

    private void arreterLocalisation()
    {
        if(locationManager != null)
        {
            locationManager.removeUpdates(ecouteurGPS);
            ecouteurGPS = null;
        }
    }
}