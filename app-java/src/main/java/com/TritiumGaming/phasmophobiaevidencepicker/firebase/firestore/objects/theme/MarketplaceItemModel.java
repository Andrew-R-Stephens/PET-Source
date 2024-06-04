package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.theme;

import androidx.annotation.NonNull;

public abstract class MarketplaceItemModel {

    protected String uuid;
    protected String name;
    protected long buyCredits;

    public MarketplaceItemModel() {
    }

    public MarketplaceItemModel(long buyCredits, String name) {
        setBuyCredits(buyCredits);
        setName(name);
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setBuyCredits(long buyCredits) {
        this.buyCredits = buyCredits;
    }

    protected void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public long getBuyCredits() {
        return buyCredits;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    @NonNull
    public String toString() {
        return getUuid() + " " + getName() + " " + getBuyCredits();
    }
}

