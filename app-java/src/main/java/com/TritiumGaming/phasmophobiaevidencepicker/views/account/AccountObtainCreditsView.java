package com.TritiumGaming.phasmophobiaevidencepicker.views.account;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Html;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.google.firebase.auth.FirebaseUser;

public class AccountObtainCreditsView extends ConstraintLayout {

    private ColorStateList colorStateList;

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

        buildButtonStateColors(findViewById(R.id.button_ad_watch));
        buildButtonStateColors(findViewById(R.id.settings_account_buy_button));
        setWatchAdsLabelDescription();

        FirebaseUser user = null;
        try {
            user = FirestoreUser.getCurrentFirebaseUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContainerVisibility(user);
    }

    private void setContainerVisibility(@Nullable FirebaseUser user) {
        if(user == null) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    public void setWatchAdsLabelDescription() {
        setWatchAdsLabelDescription(-1);
    }

    public void setWatchAdsLabelDescription(int quantity) {
        AppCompatTextView label_description = findViewById(R.id.label_ads_description);
        if(label_description == null) { return; }

        @ColorInt int color =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.textColorPrimary);

        String descriptionQuantity = getResources().getQuantityString(
                R.plurals.marketplace_description_watch_ad,
                quantity, quantity);
        String colorHex = ColorUtils.intToHex(color);

        StringBuilder quantityStrTemp = new StringBuilder("<font color=")
                .append(colorHex).append(">");
        //String quantityStr = "";
        if(quantity >= 0) {
            quantityStrTemp.append(quantity);
        }
        quantityStrTemp.append("</font>");

        String descriptionFormat = getContext().getString(
                R.string.marketplace_description_watch_ad,
                quantityStrTemp.toString(), descriptionQuantity);
        label_description.setText(Html.fromHtml(descriptionFormat));
    }

    public void buildButtonStateColors(@Nullable AppCompatButton button) {
        if (button == null) {
            return;
        }

        ColorStateList colorStateList = button.getTextColors();
        @ColorInt int defaultColor = colorStateList.getDefaultColor();
        @ColorInt int disabledColor = ColorUtils.setColor(defaultColor, 50, -1, -1, -1);

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_enabled}
        };
        int[] colors = {
                defaultColor,
                disabledColor
        };
        colorStateList = new ColorStateList(states, colors);

        button.setTextColor(colorStateList);
    }

    public void enableAdWatchButton(boolean enable) {
        AppCompatButton button = findViewById(R.id.button_ad_watch);
        if(button == null) { return; }

        button.setEnabled(enable);
    }

}
