package com.example.projetihm.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.projetihm.R;
import com.example.projetihm.models.ProducerMarkerStyler;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#build} factory method to

 =) */
public class MapFragment extends Fragment implements MapEventsReceiver, LocationListener {

	private MapView map;
	private MyLocationNewOverlay myLocation;

	private LocationManager locationManager;
	private GeoPoint currentLocation;

	public MapFragment() {
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *.
	 * @return A new instance of fragment MapFragment.
	 */
	public static MapFragment build() {
		MapFragment fragment = new MapFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Configuration.getInstance().load(requireActivity().getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_map, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		map = requireView().findViewById(R.id.map);
		map.setTileSource(TileSourceFactory.MAPNIK);
		map.setMultiTouchControls(true);
		map.setTilesScaledToDpi(true);

		requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

		KmlDocument kmlDocument = new KmlDocument();
		try {
			InputStream is = getContext().getAssets().open("carnet-producteurs.geojson.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String json = new String(buffer, "UTF-8");
			kmlDocument.parseGeoJSON(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

		FolderOverlay kmlOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map, null, new ProducerMarkerStyler(getContext(), map), kmlDocument);
		map.getOverlays().add(0, new MapEventsOverlay(this));
		map.getOverlays().add(kmlOverlay);

		initCurrentLocation();
		GeoPoint startPoint = currentLocation == null ? new GeoPoint(50.633333, 3.066667) : currentLocation;

		IMapController mc = map.getController();
		mc.setCenter(startPoint);
		mc.setZoom(10.0);
		map.invalidate();
	}

	@Override
	public void onPause() {
		super.onPause();
		map.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		map.onResume();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		map = null;
	}

	@Override
	public boolean singleTapConfirmedHelper(GeoPoint p) {
		InfoWindow.closeAllInfoWindowsOn(map);
		return true;
	}

	@Override
	public boolean longPressHelper(GeoPoint p) {
		return false;
	}

	private void initCurrentLocation() {
		if (ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			requestPermissions(new String [] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
		}
		try {

			locationManager = (LocationManager) requireActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, this);

			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			Log.d("location", location.toString());
			System.out.println("lcoation: " + location);
			if( location != null ) {
				currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
				if (myLocation == null) {
					myLocation = new MyLocationNewOverlay(new GpsMyLocationProvider(requireActivity().getApplicationContext()),map);
					myLocation.enableMyLocation();
					map.getOverlays().add(myLocation);
					locationManager.removeUpdates(this);
				}
			}
		}
		catch (Exception ex) {
			Log.d("location", "Affinage de la position impossible");
			Toast.makeText(requireActivity().getApplicationContext(), "Affinage de la position impossible", Toast.LENGTH_SHORT);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		Toast.makeText(requireActivity().getApplicationContext(), "La localisation est nécessaire pour vous repérer sur la carte", Toast.LENGTH_LONG);
	}

	@Override
	public void onLocationChanged(@NonNull Location location) {
		currentLocation = new GeoPoint(location);
	}

	@Override
	public void onProviderEnabled(@NonNull String provider) {

	}

	@Override
	public void onProviderDisabled(@NonNull String provider) {

	}
}