
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.possessions;

import android.content.Context;

import androidx.annotation.StringRes;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreItemData;

import java.util.ArrayList;

public class ItemStorePossessionItemData extends ItemStoreItemData {

    private final @StringRes ArrayList<Integer> attributes = new ArrayList<>();
    private @StringRes int altName, sanityDrain, drawChance;

    public void addAttribute(int value) {
        this.attributes.add(value);
    }

    public void setAltName(int value) {
        altName = value;
    }

    public int getAltName() {
        return altName;
    }

    public void setSanityDrainData(int value) {
        sanityDrain = value;
    }

    public int getSanityDrainData() {
        return sanityDrain;
    }

    public void setDrawChance(int value) {
        drawChance = value;
    }

    public int getDrawChance() {
        return drawChance;
    }

    public String getAllAttributesAsFormattedHTML(Context c) {
        StringBuilder out = new StringBuilder();
        for(int v: attributes) {
            out.append(c.getString(v)).append(" ");
        }

        return out.toString();
    }

}