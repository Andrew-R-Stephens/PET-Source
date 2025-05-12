package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.mapsmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation.findNavController
import com.google.common.primitives.Ints
import com.tritiumgaming.phasmophobiaevidencepicker.R

/**
 * MapMenuFragment class
 *
 * @author TritiumGamingStudios
 */
class MapMenuFragment : MapFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mapmenu, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView = view.findViewById<GridView>(R.id.grid_maps)

        mapViewModel.complexMapRepository.mapListModel?.let { mapListModel ->
            mapViewModel.let { mapViewModel ->
                val gridViewAdapter = GridViewAdapter(
                    mapListModel.shortenedMapNames.toTypedArray<String?>(),
                    Ints.toArray(mapViewModel.mapThumbnails)
                )
                gridView.adapter = gridViewAdapter
                gridView.onItemClickListener =
                    AdapterView.OnItemClickListener {
                        _: AdapterView<*>?, itemView: View, position: Int, _: Long ->
                        mapListModel.let { mapListModel ->
                            System.gc()

                            mapViewModel.currentMapIndex = position
                            val mapId = mapViewModel.currentSimpleMap.mapId
                            mapViewModel.currentComplexMap = mapListModel.getMapById(getString(mapId))

                            navigateToMapView(itemView)
                        }
                    }
            }
        } ?: Toast.makeText(requireContext(),
            getString(R.string.alert_error_generic), Toast.LENGTH_LONG).show()
    }

    private fun navigateToMapView(view: View) {
        try {
            findNavController(view).navigate(R.id.action_mapMenuFragment_to_mapViewerFragment)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    inner class GridViewAdapter(
        mapNames: Array<String?>, @DrawableRes images: IntArray
    ) : BaseAdapter() {

        private var mapNames = arrayOfNulls<String>(0)
        @DrawableRes private var images = IntArray(0)

        init {
            if (mapNames.size == images.size) {
                this.mapNames = mapNames
                this.images = images
            } else {
                Log.d("MapMenu", "Name count: ${mapNames.size} Images count: ${images.size}")
            }
        }

        override fun getCount(): Int { return images.size }

        override fun getItem(position: Int): Any? { return null }

        override fun getItemId(position: Int): Long { return 0 }

        override fun getView(i: Int, passedNewView: View?, parent: ViewGroup): View {
            val newView: View = passedNewView?.let { passedNewView }
                ?: layoutInflater.inflate(R.layout.item_mapmenu_map, parent, false)

            val textView = newView.findViewById<AppCompatTextView>(R.id.label_mapName)
            val imageView = newView.findViewById<AppCompatImageView>(R.id.image_map)

            textView?.text = mapNames[i]
            imageView?.setImageResource(images[i])

            return newView
        }
    }

    /**
     * Enforces garbage collection on low memory
     */
    override fun onLowMemory() {
        System.gc()

        super.onLowMemory()
    }

    override fun reset() {
    }

}