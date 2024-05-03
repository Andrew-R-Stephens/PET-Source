package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.children;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.compose.ui.platform.ComposeView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.equipment.ItemStoreEquipmentItemData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.EquipmentDataView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroup;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreComposablesKt;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreType;

public class EquipmentFragment extends ItemStoreFragment {

    @SuppressLint({"ResourceType"})
    protected void buildStoreData() {
        itemStoreViewModel.initEquipmentData(requireContext());
        itemStoreViewModel.setCurrentStore(itemStoreViewModel.getEquipmentData());
    }

    protected void setPageTitle(@NonNull AppCompatTextView titleView) {
        titleView.setText(R.string.store_title_equipment);
    }

    protected void setDataViewLayout(@NonNull View view) {
        FrameLayout parent = view.findViewById(R.id.item_safehouse_itemstore_itemData);
        dataView = new EquipmentDataView(requireContext());
        parent.addView(new EquipmentDataView(requireContext()));
    }

    protected void createGroup(
            @NonNull LinearLayoutCompat parent,
            @NonNull ItemStoreGroupData group
    ) {
        try {
            ItemStoreGroup itemStoreEquipmentGroup =
                    new ItemStoreGroup(requireContext(), R.layout.item_itemstore_itemgroup);

            itemStoreEquipmentGroup.build(
                    group.getNameData(), group.getItemImages(),
                    ItemStoreType.Companion.getEquipment());

            itemStoreEquipmentGroup.setVisibility(View.INVISIBLE);
            itemStoreEquipmentGroup.setAlpha(0);
            parent.addView(itemStoreEquipmentGroup);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void softReset() { }

    @Override
    protected void saveStates() { }

}
