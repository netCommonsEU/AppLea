package com.example.commontask;

import com.example.commontask.fragment.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

public interface DetailsFragmentPresenter extends MvpPresenter<DetailsFragmentView> {

    void drawRoute(LatLng first, final int position);

    void provideBaliData();

    void onBackPressedWithScene();

    void moveMapAndAddMarker();

    
}