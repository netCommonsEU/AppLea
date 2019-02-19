package com.example.commontask;

import com.example.commontask.model.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.ArrayList;
import java.util.List;
import com.example.commontask.fragment.MvpView;

public interface DetailsFragmentView extends MvpView {

    void drawPolylinesOnMap(ArrayList<LatLng> decode);

    void provideBaliData(List<Place> places);

    void onBackPressedWithScene(LatLngBounds latLngBounds);

    void moveMapAndAddMaker(LatLngBounds latLngBounds);

    void updateMapZoomAndRegion(LatLng northeastLatLng, LatLng southwestLatLng);
}