
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.views;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class ItemStoreItem extends AppCompatImageView {

    private boolean isSelected = false;

    public ItemStoreItem(@NonNull Context context) {
        super(context);
    }

    public ItemStoreItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemStoreItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTier(int tier) {
        setImageLevel(tier);
    }

    public void setEquipment(@DrawableRes int res) {
        LayerDrawable layerDrawable = (LayerDrawable) getDrawable();
        layerDrawable.setDrawableByLayerId(R.id.ic_type, ResourcesCompat.getDrawable(getResources(), res, getContext().getTheme()));
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;

        setImageState(new int[]{
                isSelected ? R.attr.state_selected : -R.attr.state_selected},
                true);
    }

    public boolean isSelected() {
        return isSelected;
    }

}