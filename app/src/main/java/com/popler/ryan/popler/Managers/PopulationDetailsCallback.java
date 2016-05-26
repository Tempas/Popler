package com.popler.ryan.popler.Managers;

import android.location.Address;

import com.popler.ryan.popler.Model.CensusInfo;

/**
 * Created by Ryan on 5/26/16.
 */
public interface PopulationDetailsCallback {
    void populationDetailsAvailable(Address address, CensusInfo information);
}
