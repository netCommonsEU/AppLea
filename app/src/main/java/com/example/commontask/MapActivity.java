package com.example.commontask;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.commontask.model.Place;
import com.example.commontask.model.PlaceInfo;
import com.example.commontask.ui.ProfileActivity;
import com.example.commontask.utils.Constants;
import com.example.commontask.utils.EmailEncoding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MapActivity extends FragmentActivity implements DetailsFragmentView,OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, HorizontalRecyclerViewScrollListener.OnItemCoverListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Ο χάρτης είναι έτοιμος!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().isMyLocationButtonEnabled();

            String currentUserEmail1 = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
            DatabaseReference mCurrentUserDatabaseReference1 = mFirebaseDatabase
                    .getReference().child(Constants.LOCATIONS
                            + "/" + currentUserEmail1);

            temp = new ArrayList<Place>();

            mCurrentUserDatabaseReference1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Double latitude = (Double) ((HashMap) dataSnapshot.getValue()).get("latitude");
                    Double longitude = (Double) ((HashMap) dataSnapshot.getValue()).get("longitude");
                    String place_name = ((HashMap) dataSnapshot.getValue()).get("place_name").toString();
                    LatLng newlocation = new LatLng(latitude, longitude);

                    temp.add(new Place(latitude,longitude));

                    int height = 150;
                    int width = 150;
                    BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.field_map);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    mMap.addMarker(new MarkerOptions().position(newlocation).title(place_name).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    int height = 150;
                    int width = 150;
                    BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.field_map);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    marker1 = new MarkerOptions().position(
                            new LatLng(point.latitude, point.longitude)).icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    mMap.addMarker(marker1);
                    latitude = point.latitude;
                    longitude = point.longitude;
                    marker1.title("");
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker1.getPosition(), 15));
                }
            });

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    if (marker.getTitle() != null) {
                        marker.getTitle();

                        final LatLng markerPosition = marker.getPosition();
                        int selected_marker = -1;
                        for (int i = 0; i < temp.size(); i++) {
                            if (markerPosition.latitude == temp.get(i).getLatitude() && markerPosition.longitude == temp.get(i).getLongitude()) {
                                selected_marker = i;
                            }
                        }
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(markerPosition).zoom(12).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        mAdapter.notifyDataSetChanged();
                        messages.smoothScrollToPosition(selected_marker);

                        marker.showInfoWindow();

                    }

                    else {
                        EditarMark editarMark = EditarMark.newInstance(marker.getTitle(), marker.getTitle(), marker.getTitle(),marker.getTitle());
                        editarMark.show(getSupportFragmentManager(), "dialog");
                    }

                    return false;
                }
            });



        }


    }


    private static final String TAG = "MapActivity";
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;
    final static int REQUEST_LOCATION = 199;
    String locationAddress;
    GPSTracker gps;
    String key;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    String str1;
    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps, mInfo, mPlacePicker;
    private DatabaseReference mDatabase;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private Marker mMarker;
    private Double latitude, longitude;
    private String currentUserEmail, strplace, strfield, strkind,strtype;
    List<Address> addresses;
    Geocoder geocoder;
    Button button;
    MarkerOptions marker1;
    FirebaseRecyclerAdapter<Place, PlaceHolder> mAdapter;
    RecyclerView messages;
    LinearLayoutManager mLayoutManager;
     LatLng latLng;
     PulseOverlayLayout mapOverlayLayout;
     ArrayList<Place> temp;
     String name,numbers_field,kind,id_place_current,type;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
     boolean bfirst=true;

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {

                drawMarker(location);

            }
            else {

            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    private LocationManager mLocationManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1);

      //  mGps = (ImageView) findViewById(R.id.ic_gps);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
        key = mDatabase.child("posts").push().getKey();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaDecodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        messages = (RecyclerView) findViewById(R.id.messages_list);
        messages.setLayoutManager(mLayoutManager);
        mapOverlayLayout= (PulseOverlayLayout) findViewById(R.id.mapOverlayLayout);


        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(MapActivity.this);
        boolean dialog_status = prefs
                .getBoolean("dialog_status", false);//get the status of the dialog from preferences, if false you ,ust show the dialog
        if (!dialog_status) {
            View content = getLayoutInflater().inflate(
                    R.layout.dialog_content, null); // inflate the content of the dialog
            final CheckBox userCheck = (CheckBox) content //the checkbox from that view
                    .findViewById(R.id.check_box1);
            //build the dialog
            new AlertDialog.Builder(MapActivity.this)
                    .setTitle("Πατήστε στον χάρτη ώστε να τοποθετήσετε το χωράφι σας!")
                    .setView(content)
                    .setPositiveButton("Εντάξει",
                            new DialogInterface.OnClickListener() {

                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    //find our if the user checked the checkbox and put true in the preferences so we don't show the dialog again
                                    SharedPreferences prefs = PreferenceManager
                                            .getDefaultSharedPreferences(MapActivity.this);
                                    SharedPreferences.Editor editor = prefs
                                            .edit();
                                    editor.putBoolean("dialog_status",
                                            userCheck.isChecked());
                                    editor.commit();
                                    dialog.dismiss(); //end the dialog.
                                }
                            })
                    .setNegativeButton("Ακύρωση",
                            new DialogInterface.OnClickListener() {

                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    //find our if the user checked the checkbox and put true in the preferences so we don't show the dialog again
                                    SharedPreferences prefs = PreferenceManager
                                            .getDefaultSharedPreferences(MapActivity.this);
                                    SharedPreferences.Editor editor = prefs
                                            .edit();
                                    editor.putBoolean("dialog_status",
                                            userCheck.isChecked());
                                    editor.commit();
                                    dialog.dismiss();

                                }
                            }).show();
            }


        RadioGroup rgViews = (RadioGroup) findViewById(R.id.rg_views);

        rgViews.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_normal) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                } else if (checkedId == R.id.rb_satellite) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else if (checkedId == R.id.rb_terrain) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        });

        String currentUserEmail1 = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        final DatabaseReference mCurrentUserDatabaseReference1 = mFirebaseDatabase
                .getReference().child(Constants.LOCATIONS
                        + "/" + currentUserEmail1);
        final Query querying = mCurrentUserDatabaseReference1;

        querying.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() == null) {
                    return;
                }
                mAdapter = new FirebaseRecyclerAdapter<Place, PlaceHolder>(Place.class,
                        R.layout.layout_map_fields,
                        PlaceHolder.class,
                        querying) {
                    @Override
                    public void populateViewHolder(PlaceHolder holder, Place place, int position) {
                        holder.setName(place.getPlace_name());
                        holder.setNamePost(place.getNumbers_field());
                        holder.setmNameFieldPostKInd(place.getKind());
                        holder.setmNameFieldPostType(place.getType());

                        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(buttonClick);
                                //creating a popup menu
                     PopupMenu popup = new PopupMenu(getApplicationContext(), holder.buttonViewOption);
                                //inflating menu from xml resource
                    popup.inflate(R.menu.options_menu);
                                //adding click listener
                     popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.menu1:


                    final Query querying1 = mCurrentUserDatabaseReference1;

                       querying1.addListenerForSingleValueEvent(new ValueEventListener() {

                                             @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                     for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                              String userTestName = ((HashMap) dataSnapshot1.getValue()).get("place_name").toString();

                      if(userTestName.equalsIgnoreCase(place.getPlace_name())){

                               name=((HashMap) dataSnapshot1.getValue()).get("place_name").toString();
                               numbers_field=((HashMap) dataSnapshot1.getValue()).get("numbers_field").toString();
                               kind=((HashMap) dataSnapshot1.getValue()).get("kind").toString();
                               id_place_current=((HashMap) dataSnapshot1.getValue()).get("id").toString();
                                   type=((HashMap) dataSnapshot1.getValue()).get("type").toString();

                                      }

                                }


                           Intent intent = new Intent(getApplicationContext(),MapUpdate.class);

                                         intent.putExtra("place_name", name);
                                         intent.putExtra("numbers_field",numbers_field);
                                         intent.putExtra("kind",kind);
                                         intent.putExtra("id",id_place_current);
                                                 intent.putExtra("type",type);
                                          startActivity(intent);

                                     }


                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                            }
                                       });


                                                break;


                                            case R.id.menu2:

                    final Query querying = mCurrentUserDatabaseReference1;

                           querying.addListenerForSingleValueEvent(new ValueEventListener() {

                                                    @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {

                       for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                     String userTestName = ((HashMap) dataSnapshot1.getValue()).get("place_name").toString();

                            if(userTestName.equalsIgnoreCase(place.getPlace_name())){


                                       dataSnapshot1.getRef().removeValue();


                                                      }

                                               }


                                    }

                                 @Override
                          public void onCancelled(DatabaseError databaseError) {

                                        }

                                     });

                                   break;

                                        }
                                        return false;
                                    }
                                });
                                //displaying the popup
                                popup.show();

                            }
                        });
                    }

                };

             messages.setAdapter(mAdapter);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getLocationPermission();

        checkPermission();

        if(bfirst){
            getCurrentLocation();
            bfirst=false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(MapActivity.this,
                    Manifest.permission.CAMERA);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "granted");
                callLocation();
            } else {
                ActivityCompat.requestPermissions(MapActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            Log.e("MainActivity", "Lower Than MarshMallow");
            callLocation();
        }

        // Todo Turn On GPS Automatically

        if (!hasGPSDevice(MapActivity.this)) {
            Toast.makeText(MapActivity.this, "Gps not Supported", Toast.LENGTH_SHORT).show();
        }
        final LocationManager manager = (LocationManager) MapActivity.this.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(MapActivity.this)) {
            enableLoc();
        }

        else {

        }



    }


    @Override
    public void onStart() {
        super.onStart();

    }


    private void callLocation() {

        gps = new GPSTracker(MapActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new GeocoderHandler());

        } else {

            enableLoc();
        }
    }


    @Override
    public void drawPolylinesOnMap(final ArrayList<LatLng> decode) {

    }

    @Override
    public void provideBaliData(List<Place> places) {

    }


    @Override
    public void onBackPressedWithScene(LatLngBounds latLngBounds) {

    }

    @Override
    public void moveMapAndAddMaker(LatLngBounds latLngBounds) {
        mapOverlayLayout.moveCamera(latLngBounds);
        mapOverlayLayout.setOnCameraIdleListener(() -> {
            for (int i = 0; i < temp.size(); i++) {
                mapOverlayLayout.createAndShowMarker(i, temp.get(i).getLatLng());
            }
            mapOverlayLayout.setOnCameraIdleListener(null);
        });
        mapOverlayLayout.setOnCameraMoveListener(mapOverlayLayout::refresh);
    }

    @Override
    public void updateMapZoomAndRegion(LatLng northeastLatLng, LatLng southwestLatLng) {

    }

    @Override
    public void onItemCover(int position) {
        mapOverlayLayout.showMarker(position); // notify Marker here
    }

    public void removeMarker() {
        Marker currentMarker = null;
            currentMarker.setVisible(true);
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

        }
    }

    public void doChangeTitle(String title, String field, String kind,String type) {
        strplace = title;
        strfield = field;
        strkind = kind;
        strtype=type;
        createPlace();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(mLocationListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(bfirst){
            getCurrentLocation();
            bfirst=false;
        }
    }



   private void getCurrentLocation() {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled)) {

        } else {
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {

            drawMarker(location);
        }
    }

    private void drawMarker(Location location) {
        if (mMap != null) {

            LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
        }

    }

    private void createPlace() {
        final DatabaseReference usersRef = mFirebaseDatabase.getReference(Constants.LOCATIONS);
        final String location=strplace;
        final String fieldmap=strfield;
        final String kindmap=strkind;
        final String kindtype=strtype;
        final String encodedEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        final DatabaseReference userRef = usersRef.child(encodedEmail);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                com.example.commontask.model.Place place=new com.example.commontask.model.Place();
                place.setEmail(currentUserEmail);
                place.setLatitude(latitude);
                place.setLongitude(longitude);
                place.setPlace_name(location);
                place.setNumbers_field(fieldmap);
                place.setKind(kindmap);
                place.setType(kindtype);
                DatabaseReference newRef =userRef.push();
                String id_place=String.valueOf(newRef);
                String [] output=id_place.split("/");
                place.setId(output[5]);
                newRef.setValue(place);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivityForResult(intent, 0);
    }


    public void checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int permissionCheck = ContextCompat.checkSelfPermission(MapActivity.this,
                    Manifest.permission.CAMERA);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            {

            } else {
                ActivityCompat.requestPermissions(MapActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }
    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc()
    {
        // TODO Add this Dependency
        // compile 'com.google.android.gms:play-services-location:7.+'

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(MapActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            mGoogleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            mGoogleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();

                    Log.e("keshav","status Called  -->" + status.getStatusCode());

                    switch (status.getStatusCode())
                    {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.e("keshav","LocationSettingsStatusCodes.RESOLUTION_REQUIRED Called ....");
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MapActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }


    private void initMap(){
        Log.d(TAG, "initMap: initializing map");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            @SuppressLint("ResourceType") View zoomControls =mapFragment.getView().findViewById(0x1);
            if(zoomControls!=null && zoomControls.getLayoutParams() instanceof RelativeLayout.LayoutParams){

                RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams) zoomControls.getLayoutParams();
                   params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                   params.addRule(RelativeLayout.ALIGN_RIGHT);

                  final  int margin=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics());

                     params.setMargins(margin,margin,margin,margin);
            }
        mapFragment.getMapAsync(MapActivity.this);
    }



    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
            case REQUEST_LOCATION:

                    case Activity.RESULT_OK:
                    {
                        // All required changes were successfully made
                        Toast.makeText(MapActivity.this, "Location enabled by user!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED:
                    {
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(MapActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();

                        finish();

                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
    }




}











