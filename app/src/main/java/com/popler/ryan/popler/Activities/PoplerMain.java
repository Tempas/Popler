package com.popler.ryan.popler.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.popler.ryan.popler.Managers.DataManager;
import com.popler.ryan.popler.Managers.LocationManager;
import com.popler.ryan.popler.Managers.PopulationDetailsCallback;
import com.popler.ryan.popler.Model.CensusInfo;
import com.popler.ryan.popler.R;

import java.text.NumberFormat;
import java.util.Locale;

public class PoplerMain extends AppCompatActivity {

    private TextView mMalePopulationLabel;
    private TextView mFemalePopulationLabel;
    private TextView mTotalPopulationLabel;
    private TextView mLocationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popler_main);

        DataManager.sharedInstance.initWithContext(this);
        LocationManager.sharedInstance.initWithContext(this);

        handleLocationPermissions();

        mMalePopulationLabel = (TextView)findViewById(R.id.male_count_title);
        mFemalePopulationLabel = (TextView)findViewById(R.id.female_count_title);
        mTotalPopulationLabel = (TextView)findViewById(R.id.total_title);
        mLocationLabel = (TextView)findViewById(R.id.location_title);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestPopulationInformation();
                }
                return;
            }
        }
    }

    private void requestPopulationInformation() {
        DataManager.sharedInstance.getCensusInfoForCurrentLocation(new PopulationDetailsCallback() {
            @Override
            public void populationDetailsAvailable(Address address, CensusInfo information) {
                updateView(address, information);
            }
        });
    }

    private void handleLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            requestPopulationInformation();
        }
    }

    private void updateView(Address address, CensusInfo information) {

        mTotalPopulationLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(information.totalPopulation) + "\ntotal");
        mMalePopulationLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(information.malePopulation));
        mFemalePopulationLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(information.femalePopulation));

        String result = address.getSubLocality();
        if (TextUtils.isEmpty(result)) {
            result = address.getLocality();
        }

        mLocationLabel.setText(result + ", " +  address.getAdminArea());
    }
}
