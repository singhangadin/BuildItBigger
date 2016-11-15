package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.gtbit.jokelib_android.JokesActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeLoadListener{
    private InterstitialAd mInterstitialAd;
    private ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        Button button = (Button) root.findViewById(R.id.tellJoke);
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        AdRequest interAd = new AdRequest.Builder()
                .addTestDevice("AA37CAB351D645D2B79AC6AD2D158791")
                .build();
        mInterstitialAd.loadAd(interAd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                EndpointsAsyncTask task = new EndpointsAsyncTask();
                task.setJokeLoadListener(MainActivityFragment.this);
                task.execute(getContext());
            }
        });

        return root;
    }

    @Override
    public void onJokeLoaded(String joke) {
        progressBar.setVisibility(View.INVISIBLE);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        Intent intent = new Intent(getActivity(), JokesActivity.class);
        intent.putExtra("joke", joke);
        startActivity(intent);
    }
}
