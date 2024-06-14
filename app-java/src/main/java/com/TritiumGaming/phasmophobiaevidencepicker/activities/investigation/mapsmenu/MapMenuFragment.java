package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.MapListModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models.MapModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.MapFileIO;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.io.MapFileReader;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.BitmapUtils;
import com.google.common.primitives.Ints;

/**
 * MapMenuFragment class
 *
 * @author TritiumGamingStudios
 */
public class MapMenuFragment extends InvestigationFragment {

    private MapListModel mapListModel;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mapmenu, container, false);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        try {
            mapListModel = readMapsDataFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // INITIALIZE VIEWS
        AppCompatImageView backgroundImage = view.findViewById(R.id.imageView);

        // BACKGROUND IMAGE
        BitmapUtils bitmapUtils = new BitmapUtils();
        bitmapUtils.setResource(R.drawable.icon_map_sm);
        backgroundImage.setImageBitmap(bitmapUtils.compileBitmaps(requireContext()));

        GridView gridView = view.findViewById(R.id.grid_maps);
        /*CustomAdapter customAdapter = new CustomAdapter(
                mapMenuViewModel.getShortenedMapNames().toArray(new String[0]),
                Ints.toArray(mapMenuViewModel.getMapThumbnails()));*/

        if(mapListModel == null) {
            Toast.makeText(requireContext(),
                    "Error loading map data file!", Toast.LENGTH_LONG).show();
        } else {
            GridViewAdapter gridViewAdapter = new GridViewAdapter(
                    mapListModel.getShortenedMapNames().toArray(new String[0]),
                    Ints.toArray(mapMenuViewModel.getMapThumbnails()));
            gridView.setAdapter(gridViewAdapter);
            gridView.setOnItemClickListener((parent, itemView, position, id) -> {
                if (mapListModel == null) {
                    return;
                }

                System.gc();
                if (mapMenuViewModel != null) {
                    mapMenuViewModel.setCurrentMapIndex(position);
                }

                MapModel mapModel = mapListModel.getMapById(position);
                if (mapModel != null) {
                    mapMenuViewModel.setCurrentMapModel(mapModel);
                }

                navigateToBasicMapView(itemView);
            });
        }

    }

    @Nullable
    private MapListModel readMapsDataFromFile() throws Exception {
        AssetManager assets = requireActivity().getAssets();
        MapFileIO mapFileIO = new MapFileIO();
        MapFileReader reader = mapFileIO.getReader();

        mapFileIO.readFile(assets.open(getString(R.string.mapsJson)), reader);

        if(reader.worldMapDeserializer == null) {
            throw new Exception("Loading maps failed. There is a null mapsWrapper.");
        }
        mapListModel = new MapListModel(reader.worldMapDeserializer);
        mapListModel.orderRooms();

        return mapListModel;
    }

    private void navigateToBasicMapView(@NonNull View view) {
        Navigation.findNavController(view)
                .navigate(R.id.action_mapMenuFragment_to_mapViewerFragment);
    }

    public class GridViewAdapter extends BaseAdapter {

        private String[] mapNames = new String[0];
        private @DrawableRes int[] images = new int[0];
        private final LayoutInflater layoutInflater;

        public GridViewAdapter(String[] mapNames, @DrawableRes int[] images) {
            layoutInflater =
                    (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(mapNames.length != images.length) return;
            this.mapNames = mapNames;
            this.images = images;

        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int i, @Nullable View newView, ViewGroup parent) {

            if(newView == null) {
                newView = layoutInflater.inflate(R.layout.item_mapmenu_map, parent, false);
            }

            AppCompatTextView textView = newView.findViewById(R.id.label_mapName);
            AppCompatImageView imageView = newView.findViewById(R.id.image_map);

            textView.setText(mapNames[i]);
            imageView.setImageResource(images[i]);

            return newView;
        }
    }

    /**
     * Enforces garbage collection on low memory
     */
    @Override
    public void onLowMemory() {
        System.gc();

        super.onLowMemory();
    }

    @Override
    public void reset() {

    }

    @Override
    protected void saveStates() {

    }

}