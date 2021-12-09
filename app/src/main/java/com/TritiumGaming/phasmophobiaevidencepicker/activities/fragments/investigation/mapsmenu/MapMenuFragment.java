package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        AppCompatTextView label_goto_right = view.findViewById(R.id.label_goto_right);
        AppCompatTextView label_resetAll = view.findViewById(R.id.label_resetAll);
        AppCompatImageView icon_goto_left = view.findViewById(R.id.icon_goto_left);
        AppCompatImageView icon_goto_right = view.findViewById(R.id.icon_goto_right);
        AppCompatImageView icon_resetAll = view.findViewById(R.id.icon_resetAll);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);
        View listener_goto_right = view.findViewById(R.id.listener_goto_right);
        View listener_resetAll = view.findViewById(R.id.listener_resetAll);
        AppCompatImageView backgroundImage = view.findViewById(R.id.imageView);
        LinearLayout mapList = view.findViewById(R.id.mapmenu_body_verticallayout);

        // BACKGROUND IMAGE
        BitmapUtils bitmapUtils = new BitmapUtils();
        bitmapUtils.setResource(R.drawable.icon_map_sm);
        backgroundImage.setImageBitmap(bitmapUtils.compileBitmaps(getContext()));

        // LISTENERS
        listener_goto_left.setOnClickListener(v -> Navigation.findNavController(v).popBackStack()
        );

        // SET VIEWS DISABLED
        listener_goto_right.setEnabled(false);
        label_goto_right.setEnabled(false);
        icon_goto_right.setEnabled(false);
        listener_resetAll.setEnabled(false);
        label_resetAll.setEnabled(false);
        icon_resetAll.setEnabled(false);
        listener_goto_right.setVisibility(View.INVISIBLE);
        label_goto_right.setVisibility(View.INVISIBLE);
        icon_goto_right.setVisibility(View.INVISIBLE);
        listener_resetAll.setVisibility(View.INVISIBLE);
        label_resetAll.setVisibility(View.INVISIBLE);
        icon_resetAll.setVisibility(View.INVISIBLE);

        // SET NAVIGATION ITEMS
        label_goto_left.setText(R.string.general_evidence_button);
        icon_goto_left.setImageResource(R.drawable.icon_evidence);

        for (int i = 0; i < mapViewViewModel.getMapDataLength(); i++) {
            if(getContext() == null) {
                return;
            }

            LayoutInflater inflaterPopup =
                    (LayoutInflater) getContext().getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams")
            View mapView = inflaterPopup.inflate(R.layout.item_mapmenu_map, null);

            AppCompatTextView mapName = mapView.findViewById(R.id.label_mapName);

            mapView.setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));

            mapName.setText(mapViewViewModel.getMapData()[i].getMapName());

            int mapPos = i;
            mapName.setOnClickListener(v -> {
                System.gc();
                if (mapViewViewModel != null) {
                    mapViewViewModel.setCurrentMapData(mapPos);
                }
                Navigation.findNavController(v).navigate(R.id.action_mapmenu_to_mapview);
            });

            mapList.addView(mapView);
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