package com.shaznee.kayak.fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.shaznee.kayak.R;
import com.shaznee.kayak.activities.AirlineDetailsActivity;
import com.shaznee.kayak.adapters.AirlineCursorAdapter;
import com.shaznee.kayak.database.AirlinesProvider;
import com.shaznee.kayak.models.AppConstants;


public class FavoriteAirlinesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter cursorAdapter;

    public FavoriteAirlinesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fav_airlines, container, false);
        cursorAdapter = new AirlineCursorAdapter(getActivity(), null, 0);
        setListAdapter(cursorAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        restartLoader();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), AirlineDetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra(AppConstants.REQUEST_CODE, AppConstants.FAVORITE_AIRLINES_FRAGMENT_REQUEST_CODE);
        startActivity(intent);
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), AirlinesProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
