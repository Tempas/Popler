package com.popler.ryan.popler.Providers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Ryan on 5/22/16.
 */
public interface CensusApi {
    @GET("2013/acs3")
    Call<List<List<String>>> queryCensus(
            @Query(value = "get", encoded = true) String getInformation,
            @Query(value = "for", encoded = true) String forInformation,
            @Query(value = "in", encoded = true) String inInformation,
            @Query(value = "key", encoded = true) String key
    );
}
