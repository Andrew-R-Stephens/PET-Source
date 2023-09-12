package com.TritiumGaming.phasmophobiaevidencepicker;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class ItemStoreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_safehouse_itemstore, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TypedArray tierAttr = getContext().getTheme().obtainStyledAttributes(
                R.attr.equipment_tier,
                R.styleable.EquipmentTiers
        );

        /*
        // Gets you the 'value' number - 0 or 666 in your example
        if (tierAttr.hasValue(R.styleable.IconView_icon)) {
            int value = tierAttr.getInt(R.styleable.EquipmentTiers, 0));
        }
        */

        LinearLayoutCompat parent = view.findViewById(R.id.testlayout).findViewById(R.id.testlayoutlist);

        for(int i = 0; i < parent.getChildCount(); i++) {
            GridLayout child = parent.getChildAt(0).findViewById(R.id.testgridlist);
            for(int j = 0; j < child.getChildCount(); j++) {
                AppCompatImageView item = (AppCompatImageView) child.getChildAt(j);
                Log.d("TierImageID", getResources().getResourceEntryName(item.getId()) + "");
                item.setImageState(new int[]{tierAttr.getInt(R.styleable.EquipmentTiers_equipment_tier, j)}, true);
            }
        }

        tierAttr.recycle();

    }
}
