
package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.views.InteractiveMapView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.FloorLayerType;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.map.FloorModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.maps.mapviewer.MapViewerModel;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils;

/**
 * MapViewerFragment class
 *
 * @author TritiumGamingStudios
 */
public class MapViewerFragment extends InvestigationFragment {

    private InteractiveMapView imageDisplay;

    private MapLayerSelectorGroup selectorGroup;
    private AppCompatTextView layerName;
    private POISpinner poiSpinner;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mapview, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        super.init();

        LinearLayout selectorLayout = view.findViewById(R.id.linearlayout_floorindicators);

        AppCompatImageButton button_nextLayer = view.findViewById(R.id.controller_nextLayerButton);
        AppCompatImageButton button_prevLayer = view.findViewById(R.id.controller_prevLayerButton);

        AppCompatImageView button_back = view.findViewById(R.id.button_back);
        AppCompatTextView mapName = view.findViewById(R.id.textview_title);
        ConstraintLayout button_help = view.findViewById(R.id.listener_help);

        imageDisplay = view.findViewById(R.id.interactiveMapView);
        poiSpinner = view.findViewById(R.id.spinner_poiname);
        layerName = view.findViewById(R.id.textview_floorname);

        if(mapMenuViewModel != null && mapMenuViewModel.getCurrentMapModel() != null) {
            int floor = mapMenuViewModel.getCurrentMapData().getCurrentFloor();
            FloorModel currentFloor = mapMenuViewModel.getCurrentMapModel().getFloor(floor);
            FloorLayerType newLayer = currentFloor.getFloorLayer();
            mapMenuViewModel.getCurrentMapModel().setCurrentLayer(newLayer);
        }

        button_nextLayer.setOnClickListener(v -> {
            mapMenuViewModel.incrementFloorIndex();
            setMapLayer(mapMenuViewModel.getCurrentMapData().getCurrentFloor());
            updateComponents();
        });

        button_prevLayer.setOnClickListener(v -> {
            mapMenuViewModel.decrementFloorIndex();
            setMapLayer(mapMenuViewModel.getCurrentMapData().getCurrentFloor());
            updateComponents();
        });

        button_help.setOnClickListener(helpButtonView -> showHelpPopup());

        button_back.setOnClickListener(v -> handleBackAction());

        if (mapMenuViewModel != null) {
            Log.d("MapName", mapMenuViewModel.getCurrentMapData().getMapName());
            mapName.setText(mapMenuViewModel.getCurrentMapData().getMapName());

            if(imageDisplay != null) {
                if (poiSpinner != null) {
                    imageDisplay.init(mapMenuViewModel, poiSpinner);
                }

                imageDisplay.setMapData(mapMenuViewModel.getCurrentMapData());
            }

            MapViewerModel tempData = mapMenuViewModel.getCurrentMapData();

            selectorGroup = new MapLayerSelectorGroup(tempData.getFloorCount());
            for (int i = 0; i < selectorGroup.getSize(); i++) {
                selectorLayout.addView(selectorGroup.getSelectors()[i]);
            }

            String mapNameStr = tempData.getMapName();
            if(mapMenuViewModel.getCurrentMapModel() != null) {
                String name = mapMenuViewModel.getCurrentMapModel().getMapName();
                mapNameStr = !name.isEmpty() ? name: mapNameStr;
            }
            mapName.setText(mapNameStr);
            mapName.setSelected(true);
        }

        startThreads();
        updateComponents();// Spinner click listener

        initAd(view.findViewById(R.id.adView));
    }

    public void handleBackAction() {
        backPressedHandler();
    }

    public void setMapLayer(int index) {
        /*MapViewerModel mapData = mapMenuViewModel.getCurrentMapData();
        mapData.setCurrentFloor(index);*/

        if(imageDisplay != null) {
            imageDisplay.resetRoomSelection();
        }
        if(mapMenuViewModel.getCurrentMapModel() != null) {
            mapMenuViewModel.getCurrentMapModel().getFloor(index);
            mapMenuViewModel.getCurrentMapModel().setCurrentLayer(
                    mapMenuViewModel.getCurrentMapModel().getFloor(index).getFloorLayer());
            if (mapMenuViewModel.getCurrentMapModel() != null) {
                Log.d("Maps", mapMenuViewModel.getCurrentMapModel().getCurrentFloor().getFloorName() + " ");
            }
        }
    }


    private void showHelpPopup() {

        if(getView() == null || getView().getContext() == null) {
            return;
        }

        if (popupWindow != null) {
            popupWindow.dismiss();
        }

        LayoutInflater popupInflater =
                (LayoutInflater) getView().getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        View popupView = popupInflater.inflate(R.layout.popup_info_maphelp, null);
        ImageButton closeButton = popupView.findViewById(R.id.button_topnav_left);

        popupWindow = new PopupWindow(
                popupView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        closeButton.setOnClickListener(closeButtonView -> popupWindow.dismiss());

        popupWindow.showAtLocation(getView(), Gravity.CENTER_VERTICAL, 0, 0);
    }

    @Override
    public void reset() {

    }

    /**
     * startThreads
     * <p>
     * Starts a Thread which loads images into the InteractiveMapDisplayView
     */
    public void startThreads() {
        stopThreads();

        if (mapMenuViewModel.getImageDisplayThread() == null) {
            mapMenuViewModel.setImageDisplayThread(new Thread(() -> {
                if (imageDisplay != null) {
                    try {
                        imageDisplay.setMapImages(requireActivity());
                        imageDisplay.setPoiImages(requireActivity());
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }));
            mapMenuViewModel.getImageDisplayThread().start();
        }
    }

    /**
     *
     * stopThreads
     */
    public void stopThreads() {
        if (mapMenuViewModel.getImageDisplayThread() != null) {
            mapMenuViewModel.getImageDisplayThread().interrupt();
            mapMenuViewModel.setImageDisplayThread(null);
        }
    }

    /**
     *
     * updateComponents
     */
    public void updateComponents() {
        if (mapMenuViewModel != null && mapMenuViewModel.hasCurrentMapData()) {
            if (selectorGroup != null) {
                selectorGroup.setSelected(mapMenuViewModel.getCurrentMapData().getCurrentFloor());
            }
            if (layerName != null) {
                layerName.setText(getResources().getString(
                        mapMenuViewModel.getCurrentMapData().getFloorName()));
            }
            if (imageDisplay != null) {
                imageDisplay.invalidate();
            }
            if(poiSpinner != null) {
                poiSpinner.populateAdapter(mapMenuViewModel);
            }
        }
    }

    /**
     *
     * saveStates
     * <p>
     * Saves states of the MapViewer to the MapViewModel
     */
    public void saveStates() {
        if (mapMenuViewModel != null && mapMenuViewModel.hasCurrentMapData()) {
            mapMenuViewModel.getCurrentMapData().setDefaultFloor(
                    mapMenuViewModel.getCurrentMapData().getCurrentFloor());
        }
    }

    /** Destroys all threads and releases resource memory */
    @Override
    public void onPause() {
        stopThreads();

        super.onPause();
    }

    /** Destroys all threads and releases resource memory */
    @Override
    public void onDestroy() {
        stopThreads();

        super.onDestroy();
    }

    /** Forces garbage collection on low memory */
    @Override
    public void onLowMemory() {
        System.gc();

        super.onLowMemory();
    }

    /** The group of selectors which act to cycle between image layers */
    private class MapLayerSelectorGroup {

        @NonNull
        private final MapLayerSelector[] selectors;

        /** @param count - the total number of Selectors, based on map layers */
        public MapLayerSelectorGroup(int count) {
            selectors = new MapLayerSelector[count];
            for (int i = 0; i < selectors.length; i++) {
                selectors[i] = new MapLayerSelector(requireContext());
            }
            if (mapMenuViewModel != null) {
                setSelected(mapMenuViewModel.getCurrentMapData().getCurrentFloor());
            }
        }

        /** @param index - the index of Selector */
        public void setSelected(int index) {
            deSelectAll();

            if (selectors[index] != null) {
                selectors[index].setSelected(true);
            }
        }

        /** Deselects all Selectors */
        public void deSelectAll() {
            for (MapLayerSelector selector : selectors) {
                if (selector != null) {
                    selector.setSelected(false);
                }
            }
        }

        /** @return Selector array */
        public MapLayerSelector[] getSelectors() {
            return selectors;
        }

        /** @return number of Selectors */
        public int getSize() {
            return selectors.length;
        }

        /** A Selector which represents the current layer of the selected map */
        private static class MapLayerSelector extends androidx.appcompat.widget.AppCompatImageView {

            private final int[] selectorImages = new int[]{
                    R.drawable.icon_selector_unsel,
                    R.drawable.icon_selector_sel};
            private boolean isSelected = false;

            /** Selector constructor */
            public MapLayerSelector(@NonNull Context context) {
                super(context);

                setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                setScaleType(ImageView.ScaleType.FIT_CENTER);
                setAdjustViewBounds(true);

                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setPadding(5, 2, 5, 2);
                } else {
                    setPadding(2, 8, 2, 8);
                }

                setColorFilter(ColorUtils.getColorFromAttribute(getContext(), R.attr.textColorBody));
            }

            /** @param isSelected - the state of the Selector Sets the Selector as selected */
            public void setSelected(boolean isSelected) {
                this.isSelected = isSelected;

                updateImage();
            }

            /** Updates the Selector icon to reflect its current selection state */
            private void updateImage() {
                if (selectorImages != null && selectorImages.length == 2) {
                    if (!isSelected) {
                        setImageResource(selectorImages[0]);
                    } else {
                        setImageResource(selectorImages[1]);
                    }
                }
            }

            /** @return whether or not the Selector is selected */
            public boolean isSelected() {
                return isSelected;
            }
        }
    }


}

