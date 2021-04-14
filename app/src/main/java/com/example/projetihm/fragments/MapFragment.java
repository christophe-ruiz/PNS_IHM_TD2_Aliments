package com.example.projetihm.fragments;

import android.Manifest;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetihm.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#build} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

	private MapView map;

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
		requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
		GeoPoint startPoint = new GeoPoint(43.65, 7.00517);

		IMapController mc = map.getController();
		mc.setCenter(startPoint);
		mc.setZoom(15.0);
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