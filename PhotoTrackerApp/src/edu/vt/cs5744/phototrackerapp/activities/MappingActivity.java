package edu.vt.cs5744.phototrackerapp.activities;

import android.os.Bundle;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;

import edu.vt.cs5744.phototrackerapp.R;

/**
 * This Activity handles the map view and functionality for the map view
 * 
 * Mapping API used: http://developer.mapquest.com/web/products/featured/android-maps-api/documentation
 * 
 * @author Eileen Balci
 *
 */
public class MappingActivity extends MapActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_map);

      // set the zoom level, center point and enable the default zoom controls 
      MapView map = (MapView) findViewById(R.id.map);
      map.getController().setZoom(9);
      map.getController().setCenter(new GeoPoint(38.892155,-77.036195));
      map.setBuiltInZoomControls(true);
    }

    // return false since no route is being displayed 
    @Override
    public boolean isRouteDisplayed() {
      return false;
    }
  }