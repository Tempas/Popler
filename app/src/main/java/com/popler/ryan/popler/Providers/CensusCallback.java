package com.popler.ryan.popler.Providers;

/**
 * Created by Ryan on 5/26/16.
 */
public interface  CensusCallback<T> {
    void success(T result);
    void failure();
}
