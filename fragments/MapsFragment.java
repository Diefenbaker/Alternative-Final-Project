package ie.wit.explorewaterford.fragments;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ie.wit.explorewaterford.R;
import ie.wit.explorewaterford.models.Trips;

import static ie.wit.explorewaterford.main.MyTrip.showOnMapString;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    MapView mapView;
    private static final String MAP_BUNDLE_KEY = "MapBundleKey";
    static DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    public MapsFragment() {
        //Required empty constructor.
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_maps_fragment, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);

        Bundle mapBundle = null;
        if(savedInstanceState != null){
            mapBundle = savedInstanceState.getBundle(MAP_BUNDLE_KEY);
        }
        mapView.onCreate(mapBundle);
        mapView.getMapAsync(this);

        return view;

//        setContentView(R.layout.activity_maps_fragment);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView planTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        planTitle.setText("Activity Locations");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap mGoogleMap) {
        googleMap = mGoogleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true); //controls appear when an icon is selected

        //checking the String in MyTrip class, if it's empty add markers for all trips, if not add marker for trip in String
        if(showOnMapString.equals("")){
            //getting co-ordinates from all Trips stored in Firebase collections, adding markers for each
            Query getCoords = myDatabase.child("activities");
            getCoords.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot category : dataSnapshot.getChildren()) { //getting categories from activities collection
                            for (DataSnapshot trip : category.getChildren()) { //getting trips from categories
                                Trips thisTrip = trip.getValue(Trips.class);
                                LatLng thisLatLng = new LatLng(Double.parseDouble(thisTrip.getLatitude()),
                                        Double.parseDouble(thisTrip.getLongitude()));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(thisLatLng)); //centering camera on co-ordinates
                                googleMap.addMarker(new MarkerOptions()
                                        .position(thisLatLng) //setting co-ordinates of trip
                                        .title(thisTrip.getName())); //setting title on icon
                            }
                        }
                        LatLng latLng = new LatLng(52.262091,-7.113375); //co-ordinates for Waterford City
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng)); //centering camera on co-ordinates
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14)); //zooming camera in on city
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            Query getOneTrip = myDatabase.child("activities");
            getOneTrip.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot category : dataSnapshot.getChildren()) { //getting categories from activities collection
                            for (DataSnapshot trip : category.getChildren()) { //getting trips from categories
                                Trips thisTrip = trip.getValue(Trips.class);
                                if(thisTrip.getName().equals(showOnMapString)) { //if the name in this trip matches the trip name user has selected
                                    LatLng thisLatLng = new LatLng(Double.parseDouble(thisTrip.getLatitude()),
                                            Double.parseDouble(thisTrip.getLongitude()));
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(thisLatLng) //setting co-ordinates of trip
                                            .title(thisTrip.getName())); //setting title on icon
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(thisLatLng)); //centering camera on co-ordinates
                                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(16)); //zooming camera in on city
                                    showOnMapString = ""; //resetting this String so next time user visits screen all markers are added
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
