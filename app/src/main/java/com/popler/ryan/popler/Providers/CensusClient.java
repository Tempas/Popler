package com.popler.ryan.popler.Providers;

import android.telecom.Call;

import com.popler.ryan.popler.Model.CensusInfo;
import com.popler.ryan.popler.Model.CensusInfoType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ryan on 5/22/16.
 */
public class CensusClient {
    public static final CensusClient sharedInstance = new CensusClient();

    private CensusApi mCensusApi;

    private static final String Endpoint = "http://api.census.gov/data/";
    private static final String CensusApiKey = "207a67ea7f1e4c8c2229e74d7bb12fab0997ec6b";
    private static final String MalePopulationCode = "B01001_002E";
    private static final String FemalePopulationCode = "B01001_026E";
    private static final String TotalPopulationCode = "B01001_001E";
    private static final String NameLabel = "NAME";

    private CensusClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mCensusApi = retrofit.create(CensusApi.class);
    }

    public void getUnitedStatesPopulation() {
        mCensusApi.queryCensus(NameLabel + "," + TotalPopulationCode + "," + MalePopulationCode + "," + FemalePopulationCode, "us:*", null, CensusApiKey)
                .enqueue(new Callback<List<List<String>>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<List<String>>> call, Response<List<List<String>>> response) {
                        int i = 0;
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<List<String>>> call, Throwable t) {
                        int i = 0;
                    }
                });
    }

    public void getStateCensusInformation(String stateId, final CensusCallback<Map<String, CensusInfo>> callback) {

        mCensusApi.queryCensus(NameLabel + "," + TotalPopulationCode + "," + MalePopulationCode + "," + FemalePopulationCode, "county+subdivision:*", "state:" + stateId, CensusApiKey)
                .enqueue(new Callback<List<List<String>>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<List<String>>> call, Response<List<List<String>>> response) {
                        Map<String, CensusInfo> result = new HashMap<String, CensusInfo>();
                        for (int i = 1; i < response.body().size(); i++) {
                            List<String> subCountyList = response.body().get(i);
                            String subCountyName = subCountyList.get(0).split(",")[0];
                            CensusInfo censusInfo = new CensusInfo(CensusInfoType.SubCounty, subCountyName, Integer.parseInt(subCountyList.get(1)), Integer.parseInt(subCountyList.get(2)), Integer.parseInt(subCountyList.get(3)));
                            result.put(censusInfo.title, censusInfo);
                        }

                        callback.success(result);
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<List<String>>> call, Throwable t) {
                        int a = 2;
                    }
                });
    }

    public void getStateIdInformation(final CensusCallback<Map<String, String>> callback) {
        mCensusApi.queryCensus(NameLabel, "state:*", null, CensusApiKey)
            .enqueue(new Callback<List<List<String>>>() {
            @Override
            public void onResponse(retrofit2.Call<List<List<String>>> call, Response<List<List<String>>> response) {
                Map<String, String> result = new HashMap<String, String>();
                List<List<String>> lists = response.body();
                for (int i = 1; i < lists.size(); i++) {
                    result.put(lists.get(i).get(0), lists.get(i).get(1));
                }

                callback.success(result);
            }

            @Override
            public void onFailure(retrofit2.Call<List<List<String>>> call, Throwable t) {

            }
        });
    }





}
