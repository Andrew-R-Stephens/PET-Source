package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models.MapListModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.models.MapModel;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.MapFileIO;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.io.MapFileReader;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;

import java.io.IOException;

/**
 * MapMenuFragment class
 *
 * @author TritiumGamingStudios
 */
public class MapMenuFragment extends InvestigationFragment {

    private MapListModel mapListModel;

    /*
    public MapMenuFragment() {
        super(R.layout.fragment_mapmenu);
    }
    */

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

        Log.d("Maps", "starting");
        try {
            MapFileIO mapFileIO = new MapFileIO();
            MapFileReader reader = mapFileIO.reader;
            if (getActivity() != null) {
                AssetManager assets = getActivity().getAssets();
                mapFileIO.readFile(assets.open("maps.json"), reader);
                mapListModel = new MapListModel(reader.mapsWrapper);
                mapListModel.orderRooms();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // INITIALIZE VIEWS
        AppCompatImageView backgroundImage = view.findViewById(R.id.imageView);

        // BACKGROUND IMAGE
        BitmapUtils bitmapUtils = new BitmapUtils();
        bitmapUtils.setResource(R.drawable.icon_map_sm);
        backgroundImage.setImageBitmap(bitmapUtils.compileBitmaps(getContext()));

        GridView gridView = view.findViewById(R.id.grid_maps);
        CustomAdapter customAdapter = new CustomAdapter(
                mapMenuViewModel.getMapNames(),
                mapMenuViewModel.getMapThumbnails());
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener((parent, itemView, position, id) -> {
            System.gc();
            if (mapMenuViewModel != null) {
                mapMenuViewModel.setCurrentMapData(position);
            }

            MapModel mapModel = mapListModel.getMapById(position);
            if(mapModel != null) {
                mapMenuViewModel.setCurrentMapModel(mapModel);
            }

            navigateToBasicMapView(itemView);
        });

    }

    private void navigateToBasicMapView(View view) {
        Navigation.findNavController(view).navigate(R.id.action_mapMenuFragment_to_mapViewerFragment);
    }

    public void initNavListeners(View lstnr_navLeft,
                                  View lstnr_navMedLeft,
                                  View lstnr_navCenter,
                                  View lstnr_navMedRight,
                                  View lstnr_navRight,
                                  AppCompatImageView icon_navLeft,
                                  AppCompatImageView icon_navMedLeft,
                                  AppCompatImageView icon_navCenter,
                                  AppCompatImageView icon_navMedRight,
                                  AppCompatImageView icon_navRight) {

        if(lstnr_navLeft != null) {
            ((View)lstnr_navLeft.getParent()).setVisibility(View.VISIBLE);
            icon_navLeft.setImageResource(R.drawable.icon_evidence);
            lstnr_navLeft.setOnClickListener(v -> Navigation.findNavController(v)
                    .popBackStack()
            );
        }

        if(lstnr_navMedLeft != null) { }

        if(lstnr_navCenter != null) { }

        if(lstnr_navMedRight != null) { }

        if(lstnr_navRight != null) { }

    }

    @Override
    public void softReset() {

    }

    public class CustomAdapter extends BaseAdapter {

        private String[] mapNames;
        private @DrawableRes int[] images;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] mapNames, @DrawableRes int[] images) {
            if(getContext() == null) {
                return;
            }
            this.mapNames = mapNames;
            this.images = images;

            layoutInflater =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View newView, ViewGroup parent) {

            if(newView == null) {
                newView = layoutInflater.inflate(R.layout.item_mapmenu_map, parent, false);
            }

            AppCompatTextView textView = newView.findViewById(R.id.label_mapName);
            AppCompatImageView imageView = newView.findViewById(R.id.image_map);

            textView.setText(mapNames[i].split(" ")[0]);
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
}