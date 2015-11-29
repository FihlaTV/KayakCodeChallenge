package com.shaznee.kayak.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.shaznee.kayak.R;
import com.shaznee.kayak.activities.AirlineDetailsActivity;
import com.shaznee.kayak.adapters.AirLinesListAdapter;
import com.shaznee.kayak.api.KayakAPI;
import com.shaznee.kayak.models.Airline;
import com.shaznee.kayak.models.AppConstants;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AirlinesFragment extends ListFragment {

    private static final String TAG = AirlinesFragment.class.getSimpleName();

    private Context context;
    private List<Airline> airlines;

    public AirlinesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_airlines, container, false);
        context = getActivity();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isOnline()) {
            requestData();
        } else {
            Toast.makeText(context, R.string.no_network, Toast.LENGTH_LONG).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null || networkInfo.isConnectedOrConnecting();
    }

    private void updateDisplay() {
        AirLinesListAdapter adapter = new AirLinesListAdapter(context, airlines);
        setListAdapter(adapter);
    }

    private void requestData(){

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Airline.BASE_URL)
                .build();

        KayakAPI kayakAPI = adapter.create(KayakAPI.class);
        kayakAPI.getAirLines(new Callback<List<Airline>>() {
            @Override
            public void success(List<Airline> airLinesList, Response response) {
                airlines = airLinesList;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Airline airline = airlines.get(position);
        Intent intent = new Intent(getActivity(), AirlineDetailsActivity.class);
        intent.putExtra(AppConstants.REQUEST_CODE, AppConstants.AIRLINES_FRAGMENT_REQUEST_CODE);
        intent.putExtra(AppConstants.AIRLINE_OBJECT, airline);
        startActivity(intent);
    }
}