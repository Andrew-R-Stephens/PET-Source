package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class InvestigationPopupWindow extends ConstraintLayout {

    protected PopupWindow popupWindow;

    public InvestigationPopupWindow(@NonNull Context context) {
        super(context);
    }

    public InvestigationPopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InvestigationPopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InvestigationPopupWindow(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initView(int layoutID) {
        inflate(getContext(), layoutID, this);

        popupWindow = new PopupWindow(
                this,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

}
