package com.popler.ryan.popler.Model;

/**
 * Created by Ryan on 5/22/16.
 */
public class CensusInfoType {
    public static final int Country = 0;
    public static final int State = 1;
    public static final int County = 2;
    public static final int SubCounty = 3;
    public static final int InvalidType = -1;

    public int subRegionForType(int type) {
        switch (type) {
            case Country:
                return State;
            case State:
                return County;
            case County:
                return SubCounty;
            default:
                return InvalidType;
        }
    }
}
