
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.itemstore.data;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.ArrayList;

public class ItemStoreEquipmentItemData {

    private @StringRes int flavorData, infoData;
    private @DrawableRes int imageData;
    private @IntegerRes int upgradeCostData, upgradeLevel;
    private final @StringRes ArrayList<Integer>
            positiveAttr = new ArrayList<>(),
            negativeAttr = new ArrayList<>();

    public void setImageData(int imageData) {
        this.imageData = imageData;
    }

    public void setFlavorData(@StringRes int flavorData) {
        this.flavorData = flavorData;
    }

    public void setInfoData(@StringRes int infoData) {
        this.infoData = infoData;
    }

    public void setUpgradeCostData(@IntegerRes int costData) {
        this.upgradeCostData = costData;
    }

    public void setUpgradeLevel(@IntegerRes int levelData) {
        this.upgradeLevel = levelData;
    }

    public void addPositiveAttribute(int value) {
        this.positiveAttr.add(value);
    }

    public void addNegativeAttribute(int value) {
        this.negativeAttr.add(value);
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

    public @IntegerRes int getUpgradeCostData() {
        return upgradeCostData;
    }

    public @IntegerRes int getUpgradeLevelData() {
        return upgradeLevel;
    }

    public @StringRes ArrayList<Integer> getPositiveAttributes() {
        return this.positiveAttr;
    }

    public @StringRes ArrayList<Integer> getNegativeAttributes() {
        return this.negativeAttr;
    }

    public String getAllAttributesAsFormattedHTML(Context c) {
        String pos = c.getString(R.string.shop_equipment_attribute_opinion_positive),
                neg = c.getString(R.string.shop_equipment_attribute_opinion_negative);
        String invsp = "&nbsp;";

        StringBuilder out = new StringBuilder();
        for(int v: positiveAttr) {
            String attr = c.getString(v).replace(" ", invsp);
            out.append(pos).append(invsp).append(attr).append(invsp).append(" ");
        }
        for(int v: negativeAttr) {
            String attr = c.getString(v).replace(" ", invsp);
            out.append(neg).append(invsp).append(attr).append(invsp).append(" ");
        }

        return out.toString();
    }

}