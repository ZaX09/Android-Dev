package com.sti.cabby.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sti.cabby.R;


public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	
	MapView mMapView;
	private GoogleMap googleMap;
	private static final LatLng AMSTERDAM = new LatLng(52.37518, 4.895439);
	private static final LatLng PARIS = new LatLng(48.856132, 2.352448);
	private static final LatLng FRANKFURT = new LatLng(50.111772, 8.682632);
	private static final LatLng SR = new LatLng(14.648097, 121.073444);
	private static final LatLng BAHAY = new LatLng(14.700936, 121.041478);
	private LatLngBounds latlngBounds;
	private Button bNavigation;
	private Polyline newPolyline;
	private boolean isTravelingToParis = false;
	//private int width, height;

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
	    googleMap.moveCamera(CameraUpdateFactory //changed animate to move
	            .newCameraPosition(cameraPosition));

	    // Perform any camera updates here
	    
	    //enable Current Location button
	    googleMap.setMyLocationEnabled(true);
	    googleMap.getUiSettings().setZoomControlsEnabled(true);
	    googleMap.getUiSettings().setZoomGesturesEnabled(false);
	    
	    
	    //set marker by touch
	    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

	        @Override
	        public void onMapClick(LatLng point) {
	        	Marker markerDest= googleMap.addMarker(
	        			new MarkerOptions()
	        			.position(new LatLng(point.latitude, point.longitude))
	        			.title("Destination")
	        			.snippet("Testing Destination Marker"));
	        	
                /*MarkerOptions markerDest = new MarkerOptions().position( new LatLng(point.latitude, point.longitude))
                		.title("Destination");
                //googleMap.clear();*/
            Log.i("GEOLOC:", point.latitude+"---"+ point.longitude);  
            }
        });
	    //route button
	    bNavigation = (Button) v.findViewById(R.id.load_directions);
		bNavigation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!isTravelingToParis)
				{
					isTravelingToParis = true;
					findDirections( AMSTERDAM.latitude, AMSTERDAM.longitude,PARIS.latitude, PARIS.longitude, GMapV2Direction.MODE_DRIVING );
				}
				else
				{
					isTravelingToParis = false;
					findDirections( SR.latitude, SR.longitude, BAHAY.latitude, BAHAY.longitude, GMapV2Direction.MODE_DRIVING );
				}
			}
		});	
	    
	    //end view
	    return v;
	}
	//ROUTING
	public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
		PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);

		for(int i = 0 ; i < directionPoints.size() ; i++) 
		{          
			rectLine.add(directionPoints.get(i));
		}
		if (newPolyline != null)
		{
			newPolyline.remove();
		}
		newPolyline = googleMap.addPolyline(rectLine);
		if (isTravelingToParis)
		{
			latlngBounds = createLatLngBoundsObject(AMSTERDAM, PARIS);
	        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, 50));
		}
		else
		{
			latlngBounds = createLatLngBoundsObject(SR, BAHAY);
	        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, 100));
		}
		
	}
	
	private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
	{
		if (firstLocation != null && secondLocation != null)
		{
			LatLngBounds.Builder builder = new LatLngBounds.Builder();    
			builder.include(firstLocation).include(secondLocation);
			
			return builder.build();
		}
		return null;
	}
	
	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);
		
		GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
		asyncTask.execute(map);	
	}
	//ROUTING END
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
