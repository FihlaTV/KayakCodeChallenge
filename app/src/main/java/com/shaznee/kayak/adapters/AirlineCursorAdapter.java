package com.shaznee.kayak.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaznee.kayak.R;
import com.shaznee.kayak.database.DBOpenHelper;
import com.shaznee.kayak.models.Airline;
import com.squareup.picasso.Picasso;

public class AirlineCursorAdapter extends CursorAdapter {

    private ListCell listCell;

    public AirlineCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_airlines_list_cell,parent, false);
        listCell = new ListCell();
        listCell.imgAirline = (ImageView) view.findViewById(R.id.imgAirline);
        listCell.txtAirlineName = (TextView) view.findViewById(R.id.txtAirlineName);
        view.setTag(listCell);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        listCell = (ListCell) view.getTag();

        String airlineName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.AIRLINE_NAME));
        StringBuilder airlineLogoURL = new StringBuilder(Airline.BASE_URL);
        airlineLogoURL.append(cursor.getString(cursor.getColumnIndex(DBOpenHelper.AIRLINE_LOGOURL)));

        listCell.txtAirlineName.setText(airlineName);
        Picasso.with(context).load(airlineLogoURL.toString()).into(listCell.imgAirline);
    }

    private static class ListCell {
        TextView txtAirlineName;
        ImageView imgAirline;
    }
}
