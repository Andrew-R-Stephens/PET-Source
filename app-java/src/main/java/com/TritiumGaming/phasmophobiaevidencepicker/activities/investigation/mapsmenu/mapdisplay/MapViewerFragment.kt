package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.views.InteractiveMapView
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute

/**
 * MapViewerFragment class
 *
 * @author TritiumGamingStudios
 */
class MapViewerFragment : InvestigationFragment() {
    private var imageDisplay: InteractiveMapView? = null

    private var selectorGroup: MapLayerSelectorGroup? = null
    private var layerName: AppCompatTextView? = null
    private var poiSpinner: POISpinner? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mapview, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.init()

        val selectorLayout = view.findViewById<LinearLayout>(R.id.linearlayout_floorindicators)

        val buttonLayerNext =
            view.findViewById<AppCompatImageButton>(R.id.controller_nextLayerButton)
        val buttonLayerPrev =
            view.findViewById<AppCompatImageButton>(R.id.controller_prevLayerButton)

        val buttonBack = view.findViewById<AppCompatImageView>(R.id.button_back)
        val mapName = view.findViewById<AppCompatTextView>(R.id.textview_title)
        val buttonHelp = view.findViewById<ConstraintLayout>(R.id.listener_help)

        imageDisplay = view.findViewById(R.id.interactiveMapView)
        poiSpinner = view.findViewById(R.id.spinner_poiname)
        layerName = view.findViewById(R.id.textview_floorname)

        val floor = mapMenuViewModel.currentMapData.currentFloor
        mapMenuViewModel.currentMapModel?.let { currentMap ->
            currentMap.currentLayer = currentMap.getFloor(floor).floorLayer
        }

        buttonLayerNext.setOnClickListener {
            mapMenuViewModel.incrementFloorIndex()
            setMapLayer(mapMenuViewModel.currentMapData.currentFloor)
            updateComponents()
        }

        buttonLayerPrev.setOnClickListener {
            mapMenuViewModel.decrementFloorIndex()
            setMapLayer(mapMenuViewModel.currentMapData.currentFloor)
            updateComponents()
        }

        buttonHelp.setOnClickListener { showHelpPopup() }
        buttonBack.setOnClickListener { handleBackAction() }

        Log.d("MapName", mapMenuViewModel.currentMapData.mapName)
        mapName.text = mapMenuViewModel.currentMapData.mapName

        poiSpinner?.let { spinner ->
            imageDisplay?.let { mapView ->
                mapView.init(mapMenuViewModel, spinner)
                mapView.setMapData(mapMenuViewModel.currentMapData)
            }
        }

        val tempData = mapMenuViewModel.currentMapData

        selectorGroup = MapLayerSelectorGroup(tempData.floorCount)
        for (i in 0 until (selectorGroup?.size ?: 0)) {
            selectorLayout.addView(selectorGroup?.selectors?.get(i))
        }

        var mapNameStr: String? = tempData.mapName
        val name = mapMenuViewModel.currentMapModel?.mapName
        mapNameStr = if (name?.isNotEmpty() == true) name else mapNameStr

        mapName.text = mapNameStr
        mapName.isSelected = true

        startThreads()
        updateComponents() // Spinner click listener

        initAd(view.findViewById(R.id.adView))
    }

    private fun handleBackAction() {
        backPressedHandler()
    }

    private fun setMapLayer(index: Int) {
        imageDisplay?.resetRoomSelection()

        mapMenuViewModel.currentMapModel?.let { currentMap ->
            val floor = currentMap.getFloor(index)
            currentMap.currentLayer = floor.floorLayer
            Log.d("Maps", currentMap.currentFloor.floorName + " ")
        }

    }


    private fun showHelpPopup() {

        popupWindow?.dismiss()

        val popupInflater =
            view?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = popupInflater.inflate(R.layout.popup_info_maphelp, null)
        val closeButton = popupView.findViewById<ImageButton>(R.id.button_topnav_left)

        popupWindow = PopupWindow(
            popupView,
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        popupWindow?.let { popupWindow ->
            closeButton.setOnClickListener { popupWindow.dismiss() }
            popupWindow.showAtLocation(view, Gravity.CENTER_VERTICAL, 0, 0)
        }
    }

    override fun reset() {
    }

    /** Starts a Thread which loads images into the InteractiveMapDisplayView */
    private fun startThreads() {
        stopThreads()

        if (mapMenuViewModel.imageDisplayThread == null) {
            mapMenuViewModel.imageDisplayThread = Thread {
                try {
                    imageDisplay?.setMapImages(requireActivity())
                    imageDisplay?.setPoiImages(requireActivity())
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
            mapMenuViewModel.imageDisplayThread?.start()
        }
    }

    private fun stopThreads() {
        mapMenuViewModel.imageDisplayThread?.interrupt()
        mapMenuViewModel.imageDisplayThread = null
    }

    private fun updateComponents() {
        mapMenuViewModel.currentMapData.let {
            selectorGroup?.setSelected(it.currentFloor)

            layerName?.text = resources.getString(it.floorName)
            imageDisplay?.invalidate()
            poiSpinner?.populateAdapter(mapMenuViewModel)
        }
    }

    /** Saves states of the MapViewer to the MapViewModel */
    public override fun saveStates() {
        mapMenuViewModel.currentMapData.defaultFloor =
            mapMenuViewModel.currentMapData.currentFloor
    }

    /** Destroys all threads and releases resource memory  */
    override fun onPause() {
        stopThreads()

        super.onPause()
    }

    /** Destroys all threads and releases resource memory  */
    override fun onDestroy() {
        stopThreads()

        super.onDestroy()
    }

    /** Forces garbage collection on low memory  */
    override fun onLowMemory() {
        System.gc()

        super.onLowMemory()
    }

    /** The group of selectors which act to cycle between image layers  */
    private inner class MapLayerSelectorGroup(count: Int) {
        /** @return Selector array */
        val selectors: Array<MapLayerSelector?> = arrayOfNulls(count)

        /** @param count - the total number of Selectors, based on map layers */
        init {
            for (i in selectors.indices) {
                selectors[i] = MapLayerSelector(requireContext())
            }
            if (mapMenuViewModel != null) {
                setSelected(mapMenuViewModel.currentMapData.currentFloor)
            }
        }

        /** @param index - the index of Selector */
        fun setSelected(index: Int) {
            deSelectAll()

            if (selectors[index] != null) {
                selectors[index]!!.isSelected = true
            }
        }

        /** Deselects all Selectors */
        fun deSelectAll() {
            for (selector in selectors) {
                if (selector != null) {
                    selector.isSelected = false
                }
            }
        }

        val size: Int
            /** @return number of Selectors */
            get() = selectors.size

        /** A Selector which represents the current layer of the selected map */
        private inner class MapLayerSelector(context: Context) : AppCompatImageView(context) {
            private val selectorImages: IntArray? = intArrayOf(
                R.drawable.icon_selector_unsel,
                R.drawable.icon_selector_sel
            )
            private var isSelected = false

            /** Selector constructor */
            init {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1f
                )
                scaleType = ScaleType.FIT_CENTER
                adjustViewBounds = true

                val orientation = resources.configuration.orientation
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setPadding(5, 2, 5, 2)
                } else {
                    setPadding(2, 8, 2, 8)
                }

                setColorFilter(getColorFromAttribute(getContext(), R.attr.textColorBody))
            }

            /** @param isSelected - the state of the Selector Sets the Selector as selected */
            override fun setSelected(isSelected: Boolean) {
                this.isSelected = isSelected

                updateImage()
            }

            /** Updates the Selector icon to reflect its current selection state  */
            private fun updateImage() {
                if (selectorImages != null && selectorImages.size == 2) {
                    if (!isSelected) {
                        setImageResource(selectorImages[0])
                    } else {
                        setImageResource(selectorImages[1])
                    }
                }
            }

            /** @return whether or not the Selector is selected */
            override fun isSelected(): Boolean {
                return isSelected
            }
        }
    }
}

