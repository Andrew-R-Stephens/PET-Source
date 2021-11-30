package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
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

    private MapMenuViewModel mapViewViewModel = null;

    private AppCompatImageView backgroundImage = null;

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
        }

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //set universal font
        Typeface universalFont = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            universalFont = getResources().getFont(R.font.norse_regular);
        }
        else {
            if (getContext() != null) {
                universalFont = ResourcesCompat.getFont(getContext(), R.font.norse_regular);
            }
        }

        // INITIALIZE VIEWS
        AppCompatTextView title = view.findViewById(R.id.mapmenu_title);
        AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        AppCompatTextView label_goto_right = view.findViewById(R.id.label_goto_right);
        AppCompatTextView label_resetAll = view.findViewById(R.id.label_resetAll);
        AppCompatImageView icon_goto_left = view.findViewById(R.id.icon_goto_left);
        AppCompatImageView icon_goto_right = view.findViewById(R.id.icon_goto_right);
        AppCompatImageView icon_resetAll = view.findViewById(R.id.icon_resetAll);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);
        View listener_goto_right = view.findViewById(R.id.listener_goto_right);
        View listener_resetAll = view.findViewById(R.id.listener_resetAll);
        backgroundImage = view.findViewById(R.id.imageView);
        LinearLayout mapList = view.findViewById(R.id.mapmenu_body_verticallayout);

        // BACKGROUND IMAGE
        BitmapUtils bitmapUtils = new BitmapUtils();
        bitmapUtils.setResource(R.drawable.icon_map_sm);
        backgroundImage.setImageBitmap(bitmapUtils.compileBitmaps(getContext()));

        // TEXT SIZE
        title.setAutoSizeTextTypeUniformWithConfiguration(
                20, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        label_goto_left.setAutoSizeTextTypeUniformWithConfiguration(
                10, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        listener_goto_left.setOnClickListener(v -> Navigation.findNavController(v).popBackStack()
        );

        // SET VIEWS DISABLED
        listener_goto_right.setEnabled(false);
        label_goto_right.setEnabled(false);
        icon_goto_right.setEnabled(false);
        listener_goto_right.setVisibility(View.INVISIBLE);
        label_goto_right.setVisibility(View.INVISIBLE);
        icon_goto_right.setVisibility(View.INVISIBLE);
        listener_resetAll.setEnabled(false);
        label_resetAll.setEnabled(false);
        icon_resetAll.setEnabled(false);
        listener_resetAll.setVisibility(View.INVISIBLE);
        label_resetAll.setVisibility(View.INVISIBLE);
        icon_resetAll.setVisibility(View.INVISIBLE);

        // SET NAVIGATION ITEMS
        label_goto_left.setText(R.string.general_evidence_button);
        icon_goto_left.setImageResource(R.drawable.icon_evidence);

        // SET DATA
        if (!mapViewViewModel.hasMapData()) {
            if (getContext() != null) {
                mapViewViewModel.setMapData(getContext());
            }
        }
        for (int i = 0; i < mapViewViewModel.getMapDataLength(); i++) {
            AppCompatTextView mapName = new AppCompatTextView(view.getContext());
            mapName.setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            mapName.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            mapName.setTypeface(universalFont);
            mapName.setAutoSizeTextTypeUniformWithConfiguration(
                    10, 30, 1,
                    TypedValue.COMPLEX_UNIT_SP);
            mapName.setText(mapViewViewModel.getMapData()[i].getMapName());
            mapName.setTextColor(Color.WHITE);
            int mapPos = i;
            mapName.setOnClickListener(v -> {
                System.gc();
                if (mapViewViewModel != null) {
                    mapViewViewModel.setCurrentMapData(mapPos);
                }
                Navigation.findNavController(v).navigate(R.id.action_mapmenu_to_mapview);
            });
            mapList.addView(mapName);
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