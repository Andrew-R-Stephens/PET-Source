package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;

/**
 * MapMenuFragment class
 *
 * @author TritiumGamingStudios
 */
public class MapMenuFragment extends Fragment {

    private MapMenuViewModel mapViewViewModel;

    public MapMenuFragment() {
        super(R.layout.fragment_mapmenu);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (mapViewViewModel == null) {
            mapViewViewModel = new ViewModelProvider(requireActivity()).get(MapMenuViewModel.class);
            mapViewViewModel.init(getContext());
        }

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        AppCompatImageView icon_goto_left = view.findViewById(R.id.icon_goto_left);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);
        AppCompatImageView backgroundImage = view.findViewById(R.id.imageView);

        // BACKGROUND IMAGE
        BitmapUtils bitmapUtils = new BitmapUtils();
        bitmapUtils.setResource(R.drawable.icon_map_sm);
        backgroundImage.setImageBitmap(bitmapUtils.compileBitmaps(getContext()));

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

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        // SET NAVIGATION ITEMS
        label_goto_left.setText(R.string.general_evidence_button);

        GridView gridView = view.findViewById(R.id.grid_maps);
        CustomAdapter customAdapter = new CustomAdapter(
                mapViewViewModel.getMapNames(),
                mapViewViewModel.getMapThumbnails());
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener((parent, itemView, position, id) -> {
            System.gc();
            if (mapViewViewModel != null) {
                mapViewViewModel.setCurrentMapData(position);
            }

            navigateToBasicMapView(itemView);
            //navigateToAdvancedMapView(itemView);
        });

    }

    private void navigateToBasicMapView(View view) {
        Navigation.findNavController(view).navigate(R.id.action_mapmenu_to_mapview);
    }

    private void navigateToAdvancedMapView(View view) {
        Bundle b = new Bundle();
        b.putString("assetDir", "models");
        //b.putString("assetFilename", "prison_firstfloor.obj");
        b.putString("assetFilename", "asylum_topfloor.obj");
        b.putString("immersiveMode", "true");

        Navigation.findNavController(view).navigate(R.id.action_mapMenuFragment_to_modelFragment, b);
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