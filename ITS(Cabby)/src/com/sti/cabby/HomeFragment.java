package com.sti.cabby;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	
	MapView mMapView;
	private GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    // inflate and return the layout
	    View v = inflater.inflate(R.layout.fragment_home, container,
	            false);
	    mMapView = (MapView) v.findViewById(R.id.mapView);
	    mMapView.onCreate(savedInstanceState);

	    mMapView.onResume();// needed to get the map to display immediately

	    try {
	        MapsInitializer.initialize(getActivity().getApplicationContext());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    googleMap = mMapView.getMap();
	    // latitude and longitude
	    double latitude = 14.700936;
	    double longitude = 121.041478;

	    // set static marker
	    MarkerOptions marker = new MarkerOptions().position(
	            new LatLng(latitude, longitude)).title("Dev Crib");

	    // Changing marker icon
	    marker.icon(BitmapDescriptorFactory
	            .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

	    // adding marker
	    googleMap.addMarker(marker);
	    CameraPosition cameraPosition = new CameraPosition.Builder()
	            .target(new LatLng(14.700936, 121.041478)).zoom(12).build();
	    googleMap.animateCamera(CameraUpdateFactory
	            .newCameraPosition(cameraPosition));

	    // Perform any camera updates here
	    
	    //enable Current Location button
	    googleMap.setMyLocationEnabled(true);
	    
	    //set marker by touch
	    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

	        @Override
	        public void onMapClick(LatLng point) {
                MarkerOptions marker = new MarkerOptions().position( new LatLng(point.latitude, point.longitude))
                		.title("Destination");
                googleMap.clear();
                googleMap.addMarker(marker);
            System.out.println(point.latitude+"---"+ point.longitude);  
            }
        });
	    
	    //end view
	    return v;
	}

	@Override
	public void onResume() {
	    super.onResume();
	    mMapView.onResume();
	}

	@Override
	public void onPause() {
	    super.onPause();
	    mMapView.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    mMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
	    super.onLowMemory();
	    mMapView.onLowMemory();
	}
}
