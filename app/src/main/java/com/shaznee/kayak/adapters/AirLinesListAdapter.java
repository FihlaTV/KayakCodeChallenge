package com.shaznee.kayak.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaznee.kayak.R;
import com.shaznee.kayak.models.Airline;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AirLinesListAdapter extends BaseAdapter {

    private static String TAG = AirLinesListAdapter.class.getSimpleName();
    private List<Airline> airlines;
    private Context context;

    public AirLinesListAdapter(Context context, List<Airline> listData) {
        this.airlines = listData;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListCell listCell;

        if (convertView == null) {
            listCell = new ListCell();
            convertView = LayoutInflater.from(context).inflate(R.layout.airlines_list_cell, parent, false);
            listCell.txtAirlineName = (TextView) convertView.findViewById(R.id.txtAirlineName);
            listCell.imgAirline = (ImageView) convertView.findViewById((R.id.imgAirline));
            convertView.setTag(listCell);
        } else {
            listCell = (ListCell) convertView.getTag();
        }

        Airline airline = this.airlines.get(position);
        listCell.txtAirlineName.setText(airline.getName());
        Picasso.with(context).load(airline.getCompleteLogoURL()).into(listCell.imgAirline);

        return convertView;
    }

    @Override
    public int getCount() {
        return airlines.size();
    }

    @Override
    public Object getItem(int position) {
        return airlines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ListCell {
        TextView txtAirlineName;
        ImageView imgAirline;
    }

}