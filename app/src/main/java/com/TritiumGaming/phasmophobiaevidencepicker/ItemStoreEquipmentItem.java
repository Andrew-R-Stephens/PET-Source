
package com.TritiumGaming.phasmophobiaevidencepicker;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;

public class ItemStoreEquipmentItem extends AppCompatImageView {

    public ItemStoreEquipmentItem(@NonNull Context context) {
        super(context);
    }

    public ItemStoreEquipmentItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemStoreEquipmentItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTier(int tier) {
        setImageLevel(tier);
    }

    public void setEquipment(@DrawableRes int res) {
        LayerDrawable layerDrawable = (LayerDrawable) getDrawable();
        layerDrawable.setDrawableByLayerId(R.id.ic_type, ResourcesCompat.getDrawable(getResources(), res, getContext().getTheme()));

    }
}