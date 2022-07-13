
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.data.MapData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.views.InteractiveMapControlView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.services.SceneLoader;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.services.wavefront.ExampleSceneLoader;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.view.ModelSurfaceView;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.util.Utils;

import java.io.File;

/**
 * This activity represents the container for our 3D viewer.
 *
 * @author andresoviedo
 */
public class ModelFragment extends Fragment {

    private final MapMenuViewModel mapViewViewModel = null;

    private MapLayerSelectorGroup selectorGroup = null;
    private AppCompatTextView layerName = null;

    private static final int REQUEST_CODE_OPEN_FILE = 1000;

    private String paramAssetDir;
    private String paramAssetFilename;
    /**
     * The file to load. Passed as input parameter
     */
    private String paramFilename;
    /**
     * Enter into Android Immersive mode so the renderer is full screen or not
     */
    private boolean immersiveMode = false;
    /**
     * Background GL clear color. Default is light gray
     */
    private final float[] backgroundColor = new float[]{0.2f, 0.2f, 0.2f, 1.0f};

    private ModelSurfaceView gLView;

    private SceneLoader scene;

    private Handler handler;

    public ModelFragment() {
        super(R.layout.fragment_mapview3d);
    }

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Try to get input parameters
        Bundle b = getArguments();

        if (b != null) {
            this.paramAssetDir = b.getString("assetDir");
            this.paramAssetFilename = b.getString("assetFilename");
            this.paramFilename = b.getString("uri");
            this.immersiveMode = "true".equalsIgnoreCase(b.getString("immersiveMode"));
            try{
                String[] backgroundColors = b.getString("backgroundColor").split(" ");
                backgroundColor[0] = Float.parseFloat(backgroundColors[0]);
                backgroundColor[1] = Float.parseFloat(backgroundColors[1]);
                backgroundColor[2] = Float.parseFloat(backgroundColors[2]);
                backgroundColor[3] = Float.parseFloat(backgroundColors[3]);
            }catch(Exception ex) {
                // Assuming default background color
            }
        }
        Log.i("Renderer", "Params: assetDir '" + paramAssetDir + "', assetFilename '" + paramAssetFilename + "', uri '"
                + paramFilename + "'");

        if(getActivity() != null) {
            handler = new Handler(getActivity().getMainLooper());
        }

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = view.findViewById(R.id.interactiveMap3DDisplay);
        gLView.init(this);

        // Create our 3D sceneario
        if (paramFilename == null && paramAssetFilename == null) {
            scene = new ExampleSceneLoader(this);
        } else {
            scene = new SceneLoader(this);
        }
        try {
            scene.init();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        // TODO: Alert user when there is no multitouch support (2 fingers). He won't be able to rotate or zoom for
        // example
        Utils.printTouchCapabilities(getActivity().getPackageManager());

        LinearLayout selectorLayout = view.findViewById(R.id.linearlayout_floorindicators);

        AppCompatImageButton button_nextLayer = view.findViewById(R.id.controller_nextLayerButton);
        AppCompatImageButton button_prevLayer = view.findViewById(R.id.controller_prevLayerButton);

        // INITIALIZE VIEWS
        AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        AppCompatImageView icon_goto_left = view.findViewById(R.id.icon_goto_left);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);

        AppCompatTextView mapName = view.findViewById(R.id.textview_title);

        InteractiveMapControlView touchInput = view.findViewById(R.id.interactiveMapController);
        layerName = view.findViewById(R.id.textview_floorname);

        // SET NAVIGATION ITEMS
        label_goto_left.setText(R.string.general_maps_button);

        button_nextLayer.setOnClickListener(v -> {
            if (mapViewViewModel != null && mapViewViewModel.hasMapData()) {

                int layerIndex = mapViewViewModel.getCurrentMapData().getCurrentFloor();
                if (++layerIndex >= mapViewViewModel.getCurrentMapData().getFloorCount()) {
                    layerIndex = 0;
                }

                MapData d;
                if ((d = mapViewViewModel.getCurrentMapData()) != null) {
                    d.setCurrentFloor(layerIndex);
                }

                updateComponents();
            }

            paramAssetFilename = "prison_secondfloor.obj";

            scene = new SceneLoader(this);
            scene.init();
        });

        button_prevLayer.setOnClickListener(v -> {
            if (mapViewViewModel != null && mapViewViewModel.hasMapData()) {
                int layerIndex = mapViewViewModel.getCurrentMapData().getCurrentFloor();
                if (--layerIndex < 0) {
                    layerIndex = mapViewViewModel.getCurrentMapData().getFloorCount() - 1;
                }

                MapData d;
                if ((d = mapViewViewModel.getCurrentMapData()) != null) {
                    d.setCurrentFloor(layerIndex);
                }

                updateComponents();
            }

            paramAssetFilename = "prison_firstfloor.obj";

            scene = new SceneLoader(this);
            scene.init();
        });

        listener_goto_left.setOnClickListener(v -> {
            saveStates();
            Navigation.findNavController(v).popBackStack();
        });

        // LISTENERS
        initNavListeners(
                listener_goto_left,
                null,
                null,
                null,
                null,
                icon_goto_left,
                null,
                null,

                null,
                null);


        if (getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        if (mapViewViewModel != null) {
            MapData tempData = mapViewViewModel.getCurrentMapData();
            if (tempData != null) {
                selectorGroup = new MapLayerSelectorGroup(tempData.getFloorCount());
                for (int i = 0; i < selectorGroup.getSize(); i++) {
                    selectorLayout.addView(selectorGroup.getSelectors()[i]);
                }
                mapName.setText(tempData.getMapName());
            }
        }

    }



    public File getParamFile() {
        return getParamFilename() != null ? new File(getParamFilename()) : null;
    }

    public String getParamAssetDir() {
        return paramAssetDir;
    }

    public String getParamAssetFilename() {
        return paramAssetFilename;
    }

    public String getParamFilename() {
        return paramFilename;
    }

    public float[] getBackgroundColor(){
        return backgroundColor;
    }

    public SceneLoader getScene() {
        return scene;
    }

    public ModelSurfaceView getgLView() {
        return gLView;
    }

    private void initNavListeners(View lstnr_navLeft,
                                  View lstnr_navMedLeft,
                                  View lstnr_navCenter,
                                  View lstnr_navMedRight,
                                  View lstnr_navRight,
                                  AppCompatImageView icon_navLeft,
                                  AppCompatImageView icon_navMedLeft,
                                  AppCompatImageView icon_navCenter,
                                  AppCompatImageView icon_navMedRight,
                                  AppCompatImageView icon_navRight) {
        if (lstnr_navLeft != null) {
            ((View) lstnr_navLeft.getParent()).setVisibility(View.VISIBLE);
            icon_navLeft.setImageResource(R.drawable.icon_evidence);
            lstnr_navLeft.setOnClickListener(v -> Navigation.findNavController(v)
                    .popBackStack()
            );
        }

        if (lstnr_navMedLeft != null) {
        }

        if (lstnr_navCenter != null) {
        }

        if (lstnr_navMedRight != null) {
        }

        if (lstnr_navRight != null) {
        }

    }

    /**
     *
     */
    public void updateComponents() {
        if (mapViewViewModel != null && mapViewViewModel.hasCurrentMapData()) {
            if (selectorGroup != null) {
                selectorGroup.setSelected(mapViewViewModel.getCurrentMapData().getCurrentFloor());
            }
            if (layerName != null) {
                layerName.setText(getResources().getString(
                        mapViewViewModel.getCurrentMapData().getFloorName()));
            }
        }
    }

    /**
     * Saves states of the MapViewer to the MapViewModel
     */
    public void saveStates() {
        if (mapViewViewModel != null && mapViewViewModel.hasCurrentMapData()) {
            mapViewViewModel.getCurrentMapData().setDefaultFloor(
                    mapViewViewModel.getCurrentMapData().getCurrentFloor());
        }
    }

    /*
     *
     * Forces garbage collection on low memory
     */
    @Override
    public void onLowMemory() {
        System.gc();

        super.onLowMemory();
    }

    /*
     *
     * LayerSelectorGroup class
     * <p>
     * The group of selectors which act to cycle between image layers
     */
    private class MapLayerSelectorGroup {

        private final MapLayerSelectorGroup.MapLayerSelector[] selectors;

        /*
         *
         * LayerSelectorGroup constructor
         *
         * @param count - the total number of Selectors, based on map layers
         */
        public MapLayerSelectorGroup(int count) {
            selectors = new MapLayerSelectorGroup.MapLayerSelector[count];
            for (int i = 0; i < selectors.length; i++) {
                selectors[i] = new MapLayerSelectorGroup.MapLayerSelector(getContext());
            }
            if (mapViewViewModel != null) {
                setSelected(mapViewViewModel.getCurrentMapData().getCurrentFloor());
            }
        }

        /*
         *
         * setSelected
         * <p>
         * Selects a specific Selector
         *
         * @param index - the index of Selector
         */
        public void setSelected(int index) {
            deSelectAll();

            if (selectors[index] != null) {
                selectors[index].setSelected(true);
            }
        }

        /*
         *
         * deSelectAll
         * <p>
         * Deselects all Selectors
         */
        public void deSelectAll() {
            for (MapLayerSelector selector : selectors) {
                if (selector != null) {
                    selector.setSelected(false);
                }
            }
        }

        /*
         *
         * getSelectors
         *
         * @return Selector array
         */
        public MapLayerSelector[] getSelectors() {
            return selectors;
        }

        /*
         *
         * getSize
         *
         * @return number of Selectors
         */
        public int getSize() {
            if (selectors == null) {
                return 0;
            }

            return selectors.length;
        }

        /*
         *
         * Selector class
         * <p>
         * A Selector which represents the current layer of the selected map
         */
        private class MapLayerSelector extends androidx.appcompat.widget.AppCompatImageView {

            private final int[] selectorImages = new int[]{
                    R.drawable.icon_selector_unselected,
                    R.drawable.icon_selector_selected};
            private boolean isSelected = false;

            /*
             *
             * Selector constructor
             */
            public MapLayerSelector(Context context) {
                super(context);

                setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                setScaleType(ImageView.ScaleType.FIT_CENTER);
                setAdjustViewBounds(true);
                setPadding(10, 2, 2, 2);
                setColorFilter(Color.WHITE);
            }

            /*
             *
             * setSelected
             *
             * @param isSelected - the state of the Selector
             *                   Sets the Selector as selected
             */
            public void setSelected(boolean isSelected) {
                this.isSelected = isSelected;

                updateImage();
            }

            /*
             *
             * updateImage
             * <p>
             * Updates the Selector icon to reflect its current selection state
             */
            private void updateImage() {
                if (selectorImages != null && selectorImages.length == 2) {
                    if (!isSelected) {
                        setImageResource(selectorImages[0]);
                    } else {
                        setImageResource(selectorImages[1]);
                    }
                }
            }

            /*
             *
             * isSelected
             *
             * @return whether or not the Selector is selected
             */
            public boolean isSelected() {
                return isSelected;
            }
        }
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPEN_FILE:
                if (resultCode == getActivity().RESULT_OK) {
                    // The URI of the selected file
                    final Uri uri = data.getData();
                    Log.i("Menu", "Loading '" + uri.toString() + "'");
                    if (uri != null && getActivity() != null) {
                        final String path = ContentUtils.getPath(getActivity().getApplicationContext(), uri);
                        if (path != null) {
                            try {
                                scene.loadTexture(null, new URL("file://"+path));
                            } catch (MalformedURLException e) {
                                Toast.makeText(getActivity().getApplicationContext(), "Problem loading texture '" + uri.toString() + "'",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Problem loading texture '" + uri.toString() + "'",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Result when loading texture was '" + resultCode + "'",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }
    */
}
