package com.popler.ryan.popler.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryan on 5/22/16.
 */
public class CensusInfo {
    public int type;
    public String title;
    public int totalPopulation;
    public int malePopulation;
    public int femalePopulation;

    private Map<String, CensusInfo> mSubRegionInfo;

    public CensusInfo(int type, String title, int totalPopulation, int malePopulation, int femalePopulation) {
        mSubRegionInfo = new HashMap<>();

        this.type = type;
        this.title = title;
        this.totalPopulation = totalPopulation;
        this.malePopulation = malePopulation;
        this.femalePopulation = femalePopulation;
    }

    public void addSubRegionCensusInfo(CensusInfo censusInfo) {
        mSubRegionInfo.put(censusInfo.title, censusInfo);
    }

    public int totalSubRegions() {
        return mSubRegionInfo.size();
    }

    public CensusInfo censusInfoForTitle(String title) {
        return mSubRegionInfo.get(title);
    }

    public Collection<CensusInfo> allSubRegionCensusInformation() {
        return mSubRegionInfo.values();
    }
}
