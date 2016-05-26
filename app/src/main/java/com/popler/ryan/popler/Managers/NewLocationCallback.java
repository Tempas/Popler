package com.popler.ryan.popler.Managers;

import android.location.Address;

/**
 * Created by Ryan on 5/26/16.
 */
public interface NewLocationCallback {
    void onNewLocation(String State, Address location);
}
