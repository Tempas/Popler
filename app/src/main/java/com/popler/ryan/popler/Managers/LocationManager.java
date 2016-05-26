package com.popler.ryan.popler.Managers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;

import com.popler.ryan.popler.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ryan on 5/26/16.
 */
public class LocationManager{
    private static final String TAG = "LocationManager";
    public static final LocationManager sharedInstance = new LocationManager();

    private Context mContext;

    private LocationManager() {

    }

    public void initWithContext(Context context) {
        mContext = context;
    }

    public void getCurrentLocation(final NewLocationCallback callback) {
        android.location.LocationManager locationManager = (android.location.LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                    List<Address> addresses = null;

                    try {
                        addresses = geocoder.getFromLocation(
                                location.getLatitude(),
                                location.getLongitude(),
                                1);

                        Address address = addresses.get(0);

                        callback.onNewLocation( address.getAdminArea(), address);
                    } catch (IOException ioException) {
                        Log.e(TAG, ioException.toString());
                    } catch (IllegalArgumentException illegalArgumentException) {
                        Log.e(TAG, illegalArgumentException.toString());
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } catch (SecurityException ex) {

        }
    }
}
