package com.popler.ryan.popler.Managers;

import android.content.Context;
import android.location.Address;
import android.text.TextUtils;

import com.popler.ryan.popler.Model.CensusInfo;
import com.popler.ryan.popler.Providers.CensusCallback;
import com.popler.ryan.popler.Providers.CensusClient;
import com.popler.ryan.popler.Util.IoUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ryan on 5/22/16.
 */
public class DataManager {
    private static final String StateSubCensusCacheFileName = "StateCeCacheFile";
    private static final String StateIdCacheFileName = "StateCacheFile";

    private Context mContext;

    public static final DataManager sharedInstance = new DataManager();

    // State name -> subcounty/county name -> CensusInfo
    private Map<String, Map<String, CensusInfo>> mStateCensusInfo;

    // State name -> state id
    private Map<String, String> mStateIdMap;

    private DataManager() {
    }

    public void initWithContext(Context context) {
        mContext = context;

        initializeData();
    }

    public void getCensusInfoForCurrentLocation(final PopulationDetailsCallback callback) {
        LocationManager.sharedInstance.getCurrentLocation(new NewLocationCallback() {
            @Override
            public void onNewLocation(String state, final Address location) {
                getCensusInfoForState(state, new StateCensusDataAvailableCallback() {
                    @Override
                    public void onDataAvailable(String state) {
                        String result = location.getSubLocality();
                        if (TextUtils.isEmpty(result)) {
                            result = location.getLocality() + " city";
                        }

                        callback.populationDetailsAvailable(location, mStateCensusInfo.get(state).get(result));
                    }
                });
            }
        });
    }

    private void getCensusInfoForState(final String stateName, final StateCensusDataAvailableCallback callback) {
        if (mStateCensusInfo.get(stateName) != null) {
            callback.onDataAvailable(stateName);
            return;
        }

        CensusClient.sharedInstance.getStateCensusInformation(mStateIdMap.get(stateName), new CensusCallback<Map<String, CensusInfo>>() {
            @Override
            public void success(Map<String, CensusInfo> result) {
                mStateCensusInfo.put(stateName, result);
                callback.onDataAvailable(stateName);
            }

            @Override
            public void failure() {

            }
        });
    }

    private void initializeData() {
        mStateIdMap = IoUtil.loadObjectFromFile(StateIdCacheFileName, mContext);
        mStateCensusInfo = IoUtil.loadObjectFromFile(StateSubCensusCacheFileName, mContext);

        if (mStateIdMap == null) {
            CensusClient.sharedInstance.getStateIdInformation(new CensusCallback<Map<String, String>>() {
                @Override
                public void success(Map<String, String> stringStringMap) {
                    mStateIdMap = stringStringMap;
                    IoUtil.saveObjectToFile(StateIdCacheFileName, mStateIdMap, mContext);
                }

                @Override
                public void failure() {

                }
            });
        }

        if (mStateCensusInfo == null) {
            mStateCensusInfo = new HashMap<>();
        }
    }
}
