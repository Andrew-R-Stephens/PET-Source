package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.mapsmenu.mapdisplay

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.get
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.InvestigationFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.mapsmenu.mapdisplay.views.InteractiveMapView
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.PETImageButton

/**
 * MapViewerFragment class
 *
 * @author TritiumGamingStudios
 */
class MapViewerFragment : InvestigationFragment() {
    private var imageDisplay: InteractiveMapView? = null
    private var selectorList: LinearLayout? = null
    private var selectorListWrapper: FrameLayout? = null
    private var selectorGroup: MapLayerSelectorGroup? = null
    private var layerName: AppCompatTextView? = null
    private var poiSpinner: POISpinner? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mapview, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.init()

        selectorListWrapper = view.findViewById(R.id.linearlayout_floorindicators_wrapper)
        selectorList = view.findViewById(R.id.linearlayout_floorindicators)

        val buttonLayerNext =
            view.findViewById<PETImageButton>(R.id.controller_nextLayerButton)
        val buttonLayerPrev =
            view.findViewById<PETImageButton>(R.id.controller_prevLayerButton)

        val buttonBack = view.findViewById<PETImageButton>(R.id.button_back)
        val mapName = view.findViewById<AppCompatTextView>(R.id.textview_title)
        val buttonHelp = view.findViewById<PETImageButton>(R.id.imageview_help)

        imageDisplay = view.findViewById(R.id.interactiveMapView)
        poiSpinner = view.findViewById(R.id.spinner_poiname)
        layerName = view.findViewById(R.id.textview_floorname)

        mapViewModel.let { mapViewModel ->
            val floor = mapViewModel.currentMapViewerModel.currentFloor
            mapViewModel.currentMapModel?.let { currentMap ->
                currentMap.currentLayer = currentMap.getFloor(floor).floorLayer
            }

            buttonLayerNext.setOnClickListener {
                mapViewModel.incrementFloorIndex()
                setMapLayer(mapViewModel.currentMapViewerModel.currentFloor)
                updateComponents()
            }

            buttonLayerPrev.setOnClickListener {
                mapViewModel.decrementFloorIndex()
                setMapLayer(mapViewModel.currentMapViewerModel.currentFloor)
                updateComponents()
            }

            buttonHelp.setOnClickListener { showHelpPopup() }
            buttonBack.setOnClickListener { handleBackAction() }

            Log.d("MapName", requireContext().getString(mapViewModel.currentMapViewerModel.mapName))
            mapName.text = requireContext().getString(mapViewModel.currentMapViewerModel.mapName)

            poiSpinner?.let { spinner ->
                imageDisplay?.let { mapView ->
                    mapView.init(mapViewModel, spinner)
                    mapView.setMapData(mapViewModel.currentMapViewerModel)
                }
            }

            val tempData = mapViewModel.currentMapViewerModel

            selectorGroup = MapLayerSelectorGroup(tempData.floorCount)
            for (i in 0 until (selectorGroup?.size ?: 0)) {
                selectorList?.addView(selectorGroup?.selectors?.get(i))
            }
            selectorGroup?.listener = object: MapLayerSelectorGroupListener() {
                override fun onSelect(index: Int) {
                    selectorList?.let { list ->
                        selectorListWrapper?.scrollTo(list[index].x.toInt(), 0)
                    }
                }
            }

            var mapNameStr: String? = requireContext().getString(tempData.mapName)
            val name = mapViewModel.currentMapModel?.mapName
            mapNameStr = if (name?.isNotEmpty() == true) name else mapNameStr

            mapName.text = mapNameStr
            mapName.isSelected = true
        }

        startThreads()
        updateComponents() // Spinner click listener

        initAd(view.findViewById(R.id.adView))
    }

    private fun handleBackAction() {
        backPressedHandler()
    }

    private fun setMapLayer(index: Int) {
        imageDisplay?.resetRoomSelection()

        mapViewModel.currentMapModel?.let { currentMap ->
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

    override fun reset() { }

    /** Starts a Thread which loads images into the InteractiveMapDisplayView */
    private fun startThreads() {
        stopThreads()

        mapViewModel.let { mapMenuViewModel ->
            mapMenuViewModel.imageDisplayThread = Thread {
                try {
                    imageDisplay?.setMapImages(requireActivity())
                    try { imageDisplay?.setPoiImages(requireActivity()) }
                    catch (e: Exception) { e.printStackTrace() }
                }
                catch (e: IllegalStateException) { e.printStackTrace() }

            }
            mapMenuViewModel.imageDisplayThread?.start()
        }
    }

    private fun stopThreads() {
        mapViewModel.let { mapMenuViewModel ->
            mapMenuViewModel.imageDisplayThread?.interrupt()
            mapMenuViewModel.imageDisplayThread = null
        }
    }

    private fun updateComponents() {
        mapViewModel.currentMapViewerModel.let { currentMapViewerModel ->
            selectorGroup?.setSelected(currentMapViewerModel.currentFloor)

            layerName?.text = resources.getString(currentMapViewerModel.floorName)
            imageDisplay?.invalidate()
            poiSpinner?.populateAdapter(mapViewModel)
        }
    }

    /** Saves states of the MapViewer to the MapViewModel */
    public override fun saveStates() {
        mapViewModel.let { mapMenuViewModel ->
            mapMenuViewModel.currentMapViewerModel.defaultFloor =
                mapMenuViewModel.currentMapViewerModel.currentFloor
        }
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
        var listener: MapLayerSelectorGroupListener? = null

        /** @return Selector array */
        val selectors: Array<MapLayerSelector?> = arrayOfNulls(count)

        init {
            for (i in selectors.indices) {
                selectors[i] = MapLayerSelector(requireContext())
            }
            mapViewModel?.let { mapMenuViewModel ->
                setSelected(mapMenuViewModel.currentMapViewerModel.currentFloor)
            }
        }

        /** @param index - the index of Selector */
        fun setSelected(index: Int) {
            deSelectAll()

            selectors[index]?.isSelected = true
            listener?.onSelect(index)
        }

        /** Deselects all Selectors */
        fun deSelectAll() {
            for (selector in selectors) {
                selector?.isSelected = false
            }
        }

        val size: Int
            /** @return number of Selectors */
            get() = selectors.size

        /** A Selector which represents the current layer of the selected map */
        private inner class MapLayerSelector(context: Context) : AppCompatImageView(context) {
            private val selectorImages: IntArray = intArrayOf(
                R.drawable.ic_selector_unsel,
                R.drawable.ic_selector_sel
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
                if (selectorImages.size == 2) {
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

    abstract class MapLayerSelectorGroupListener {
        abstract fun onSelect(index: Int)
    }
}

