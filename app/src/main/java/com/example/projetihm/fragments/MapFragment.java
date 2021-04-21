package com.example.projetihm.fragments;

import android.Manifest;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projetihm.R;
import com.example.projetihm.models.ProducerMarkerStyler;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFolder;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#build} factory method to

=) */
public class MapFragment extends Fragment {

	private MapView map;
	private MyLocationNewOverlay myLocation;
	private ProducerMarkerStyler pms = new ProducerMarkerStyler(getContext());

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

		requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

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

		FolderOverlay kmlOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(map, null, new ProducerMarkerStyler(getContext()), kmlDocument);
		map.getOverlays().add(kmlOverlay);


		GeoPoint startPoint = new GeoPoint(50.633333, 3.066667);
		IMapController mc = map.getController();
		mc.setCenter(startPoint);
		mc.setZoom(20.0);
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
}