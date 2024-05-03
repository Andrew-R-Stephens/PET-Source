package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.children;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.LayerDrawable;
import android.text.Html;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.compose.ui.platform.ComposeView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossessionItemData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions.ItemStorePossnsGroupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.EquipmentDataView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreDataView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroup;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItem;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStorePaginator;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.PossessionDataView;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreComposablesKt;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreType;

public class PossessionsFragment extends ItemStoreFragment {

    @SuppressLint({"ResourceType"})
    protected void buildStoreData() {
        itemStoreViewModel.initPossessionData(requireContext());
        itemStoreViewModel.setCurrentStore(itemStoreViewModel.getPossessionData());
    }

    protected void setPageTitle(@NonNull AppCompatTextView titleView) {
        titleView.setText(R.string.store_title_cursedpossessions);
    }

    protected void setDataViewLayout(@NonNull View view) {
        FrameLayout parent = view.findViewById(R.id.item_safehouse_itemstore_itemData);
        dataView = new PossessionDataView(requireContext());
        parent.addView(new PossessionDataView(requireContext()));
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
                    ItemStoreType.Companion.getPossession());

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
