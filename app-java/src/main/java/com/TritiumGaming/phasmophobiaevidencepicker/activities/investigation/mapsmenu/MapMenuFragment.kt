package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.io.MapFileIO
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.MapListModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.BitmapUtils
import com.google.common.primitives.Ints

/**
 * MapMenuFragment class
 *
 * @author TritiumGamingStudios
 */
class MapMenuFragment : InvestigationFragment() {
    private var mapListModel: MapListModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mapmenu, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            mapListModel = readMapsDataFromFile()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // INITIALIZE VIEWS
        val backgroundImage = view.findViewById<AppCompatImageView>(R.id.imageView)

        // BACKGROUND IMAGE
        val bitmapUtils = BitmapUtils()
        bitmapUtils.setResource(R.drawable.icon_map_sm)
        backgroundImage.setImageBitmap(bitmapUtils.compileBitmaps(requireContext()))

        val gridView = view.findViewById<GridView>(R.id.grid_maps)

        if (mapListModel == null) {
            Toast.makeText(
                requireContext(),
                "Error loading map data file!", Toast.LENGTH_LONG
            ).show()
        } else {
            val gridViewAdapter = GridViewAdapter(
                mapListModel!!.shortenedMapNames.toTypedArray<String?>(),
                Ints.toArray(mapMenuViewModel.mapThumbnails)
            )
            gridView.adapter = gridViewAdapter
            gridView.onItemClickListener =
                AdapterView.OnItemClickListener { _: AdapterView<*>?, itemView: View,
                                                  position: Int, _: Long ->
                    mapListModel?.let { mapListModel ->
                        System.gc()

                        mapMenuViewModel.currentMapIndex = position
                        mapMenuViewModel.currentMapModel = mapListModel.getMapById(position)

                        navigateToBasicMapView(itemView)
                    }
                }
        }
    }

    @Throws(Exception::class)
    private fun readMapsDataFromFile(): MapListModel? {
        val assets = requireActivity().assets
        val mapFileIO = MapFileIO()
        val reader = mapFileIO.reader

        mapFileIO.readFile(assets.open(getString(R.string.mapsJson)), reader)

        reader.worldMapDeserializer
        mapListModel = MapListModel(reader.worldMapDeserializer)
        mapListModel?.orderRooms()

        return mapListModel
    }

    private fun navigateToBasicMapView(view: View) {
        findNavController(view)
            .navigate(R.id.action_mapMenuFragment_to_mapViewerFragment)
    }

    inner class GridViewAdapter(mapNames: Array<String?>, @DrawableRes images: IntArray) :
        BaseAdapter() {

        private var mapNames = arrayOfNulls<String>(0)

        @DrawableRes private var images = IntArray(0)
        private val layoutInflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        init {
            if (mapNames.size == images.size) {
                this.mapNames = mapNames
                this.images = images
            }
        }

        override fun getCount(): Int {
            return images.size
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(i: Int, passedNewView: View?, parent: ViewGroup): View {
            val newView: View = passedNewView?.let {
                passedNewView
            } ?: layoutInflater.inflate(R.layout.item_mapmenu_map, parent, false) as View


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

    override fun saveStates() {
    }
}