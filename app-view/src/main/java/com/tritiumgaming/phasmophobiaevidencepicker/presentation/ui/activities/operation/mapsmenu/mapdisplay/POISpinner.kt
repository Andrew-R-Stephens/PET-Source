package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.mapsmenu.mapdisplay

import android.content.Context
import android.content.res.Resources.Theme
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MapsViewModel

class POISpinner : AppCompatSpinner {
    constructor(context: Context) :
            super(context) { init() }

    constructor(context: Context, mode: Int) :
            super(context, mode) { init() }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { init() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { init() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, mode: Int) :
            super(context, attrs, defStyleAttr, mode) { init() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, mode: Int, popupTheme: Theme?) :
            super(context, attrs, defStyleAttr, mode, popupTheme) { init() }

    private fun init() {
        // --
    }

    fun populateAdapter(mapMenuViewModel: MapsViewModel?) {
        if (mapMenuViewModel?.currentComplexMap != null) {
            // Creating adapter for spinner
            val dataAdapter = ArrayAdapter(
                context,
                R.layout.poi_spinner_item,
                mapMenuViewModel.currentComplexMap!!.currentFloor.floorRoomNames
            )

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(R.layout.poi_spinner)

            // attaching data adapter to spinner
            adapter = dataAdapter
        }
    }

    fun populateAdapter(list: ArrayList<String>) {
        // Creating adapter for spinner
        val dataAdapter = ArrayAdapter(context, R.layout.poi_spinner_item, list)

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner
        adapter = dataAdapter
    }
}
