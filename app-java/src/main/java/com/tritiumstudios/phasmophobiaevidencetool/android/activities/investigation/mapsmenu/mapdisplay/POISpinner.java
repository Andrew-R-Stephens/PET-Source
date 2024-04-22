package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.mapsmenu.mapdisplay;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.viewmodels.MapMenuViewModel;

import java.util.ArrayList;

public class POISpinner extends AppCompatSpinner {

    public POISpinner(@NonNull Context context) {
        super(context);
    }

    public POISpinner(@NonNull Context context, int mode) {
        super(context, mode);
    }

    public POISpinner(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public POISpinner(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public POISpinner(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public POISpinner(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    public void populateAdapter(@Nullable MapMenuViewModel mapMenuViewModel) {
        if(mapMenuViewModel != null && mapMenuViewModel.getCurrentMapModel() != null &&
                mapMenuViewModel.getCurrentMapModel().getCurrentFloor() != null) {
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                    getContext(),
                    R.layout.poi_spinner_item,
                    mapMenuViewModel.getCurrentMapModel().getCurrentFloor().getFloorRoomNames());

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(R.layout.poi_spinner);

            // attaching data adapter to spinner
            setAdapter(dataAdapter);
        }
    }

    public void populateAdapter(ArrayList<String> list) {

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.poi_spinner_item,
                list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        setAdapter(dataAdapter);
    }

}
