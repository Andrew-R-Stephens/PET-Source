
package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.utilities.codex.children.itemstore.data.itemdata;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public abstract class ItemStoreItemData {

    private @StringRes int flavorData, infoData;
    private @DrawableRes int imageData;

    public void setImageData(int imageData) {
        this.imageData = imageData;
    }

    public void setFlavorData(@StringRes int flavorData) {
        this.flavorData = flavorData;
    }

    public void setInfoData(@StringRes int infoData) {
        this.infoData = infoData;
    }


    public @DrawableRes int getImageData() {
        return imageData;
    }

    public @StringRes int getFlavorData() {
        return flavorData;
    }

    public @StringRes int getInfoData() {
        return infoData;
    }

    public abstract String getAllAttributesAsFormattedHTML(Context c);

}