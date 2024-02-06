package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace;

public class AccountObtainCreditsView extends ConstraintLayout {

    public AccountObtainCreditsView(@NonNull Context context) {
        super(context);

        init(getContext());
    }

    public AccountObtainCreditsView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        init(getContext());
    }

    public AccountObtainCreditsView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(getContext());
    }

    public AccountObtainCreditsView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(getContext());
    }

    public void init(Context context) {
        inflate(context, R.layout.layout_account_obtain_credits, this);

        //setWatchAdsLabelDescription();
    }

    public void setWatchAdsLabelDescription() {
        setWatchAdsLabelDescription(10);
    }

    public void setWatchAdsLabelDescription(int quantity) {
        String descriptionQuantity = getResources().getQuantityString(R.plurals.marketplace_description_watch_ad, quantity, quantity);
        Log.d("Credits", descriptionQuantity);
        String descriptionFormat = getContext().getString(R.string.marketplace_description_watch_ad, descriptionQuantity);

        AppCompatTextView label_description = findViewById(R.id.label_ads_description);
        label_description.setText(descriptionFormat);
    }

}
