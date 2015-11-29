package com.shaznee.kayak.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaznee.kayak.R;
import com.shaznee.kayak.database.AirlinesProvider;
import com.shaznee.kayak.database.DBOpenHelper;
import com.shaznee.kayak.models.Airline;
import com.shaznee.kayak.models.AppConstants;
import com.squareup.picasso.Picasso;

public class AirlineDetailsActivity extends AppCompatActivity {

    private ImageView imageViewAirlineLogo;
    private TextView textViewAirlineName, textViewAirlinePhone, textViewAirlineWebsite;
    private Airline airline;
    private FloatingActionButton fabUpdateDB;
    private boolean isAvailableInDB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

        Intent intent = getIntent();
        if (intent.getExtras().getString(AppConstants.REQUEST_CODE).equals
                (AppConstants.AIRLINES_FRAGMENT_REQUEST_CODE)) {

            this.airline = intent.getParcelableExtra(AppConstants.AIRLINE_OBJECT);
            Cursor cursor = checkAirlineFromDB(DBOpenHelper.AIRLINE_CODE, airline.getCode());
            if (cursor.getCount() != 0) {
                isAvailableInDB = true;
                this.airline = getAirlineFromDB(cursor);
            }
            setViews(this.airline);

        } else if (intent.getExtras().getString(AppConstants.REQUEST_CODE).equals
                (AppConstants.FAVORITE_AIRLINES_FRAGMENT_REQUEST_CODE)) {

            long id = intent.getExtras().getLong("id");
            Cursor cursor = checkAirlineFromDB(DBOpenHelper.AIRLINE_ID, String.valueOf(id));
            if (cursor.getCount() != 0) {
                isAvailableInDB = true;
                this.airline = getAirlineFromDB(cursor);
            }
            setViews(this.airline);
        }

        fabUpdateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAvailableInDB) {
                    deleteAirline(airline.getCode());
                } else {
                    insertAirline(airline);
                }
            }
        });

    }

    private void initViews() {
        fabUpdateDB = (FloatingActionButton) findViewById(R.id.fabUpdateDB);
        imageViewAirlineLogo = (ImageView) findViewById(R.id.imageViewAirlineLogo);
        textViewAirlineName = (TextView) findViewById(R.id.textAirlineName);
        textViewAirlinePhone = (TextView) findViewById(R.id.textAirlinePhoneNumber);
        textViewAirlineWebsite = (TextView) findViewById(R.id.textAirlineWebsite);
    }

    private void setViews(Airline airline) {
        Picasso.with(this).load(airline.getCompleteLogoURL()).into(imageViewAirlineLogo);
        textViewAirlineName.setText(airline.getName());
        textViewAirlinePhone.setText(airline.getPhone());
        textViewAirlineWebsite.setText(airline.getWebSite());
        setViewFabDBUpdate();
    }

    private void setViewFabDBUpdate() {
        if (isAvailableInDB) {
            fabUpdateDB.setImageResource(android.R.drawable.ic_menu_delete);
        }else {
            fabUpdateDB.setImageResource(android.R.drawable.ic_input_add);
        }
    }

    private Cursor checkAirlineFromDB(String columnName, String dbFilter) {
        String selection = columnName + " = ?";
        String[] selectionArgs = {dbFilter};
        return getContentResolver().query(AirlinesProvider.CONTENT_URI, DBOpenHelper.ALL_COLUMNS, selection, selectionArgs, null);
    }

    private Airline getAirlineFromDB(Cursor cursor) {
        Airline dbAirline = new Airline();
        cursor.moveToFirst();
        dbAirline.setCode(cursor.getString(cursor.getColumnIndex(DBOpenHelper.AIRLINE_CODE)));
        dbAirline.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.AIRLINE_NAME)));
        dbAirline.setPhone(cursor.getString(cursor.getColumnIndex(DBOpenHelper.AIRLINE_PHONE)));
        dbAirline.setWebSite(cursor.getString(cursor.getColumnIndex(DBOpenHelper.AIRLINE_WEBSITE)));
        dbAirline.setLogoURL(cursor.getString(cursor.getColumnIndex(DBOpenHelper.AIRLINE_LOGOURL)));
        return dbAirline;
    }

    private void insertAirline(Airline airline) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.AIRLINE_CODE, airline.getCode());
        values.put(DBOpenHelper.AIRLINE_NAME, airline.getName());
        values.put(DBOpenHelper.AIRLINE_PHONE, airline.getPhone());
        values.put(DBOpenHelper.AIRLINE_WEBSITE, airline.getWebSite());
        values.put(DBOpenHelper.AIRLINE_LOGOURL, airline.getLogoURL());
        getContentResolver().insert(AirlinesProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
        finish();
    }

    private void deleteAirline(String selectionArgs) {
        String selection = DBOpenHelper.AIRLINE_CODE + " = ?";
        String[] deleteSelectionArgs = {selectionArgs};
        getContentResolver().delete(AirlinesProvider.CONTENT_URI, selection, deleteSelectionArgs);
//        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

}
