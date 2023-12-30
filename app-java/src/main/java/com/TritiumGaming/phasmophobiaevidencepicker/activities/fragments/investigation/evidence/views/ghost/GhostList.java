package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.ghost;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.GhostOrderData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.GhostPopupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.investigation.InvestigationList;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.google.android.gms.ads.AdRequest;

public class GhostList extends InvestigationList {

    private GhostPopupData popupData;

    public GhostList(Context context) {
        super(context);
    }

    public GhostList(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GhostList(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GhostList(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(EvidenceViewModel evidenceViewModel, PopupWindow popupWindow, ProgressBar progressBar, AdRequest adRequest) {
        super.init(evidenceViewModel, popupWindow, progressBar, adRequest);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void createGhostViews(PopupWindow popupWindow) {

        popupData = new GhostPopupData(getContext());

        Activity activity = (Activity) getContext();
        if(activity != null) {
            activity.runOnUiThread(() -> {
                buildGhostViews(popupWindow);

                post(() -> haltProgressAnimation(progressBar));
            });
        }

    }

    public void requestInvalidateGhostContainer() {
        if(evidenceViewModel.getGhostOrderData().hasChanges()) {
            reorderGhostViews();
        } else {
            updateGhostViews();
        }
    }
    public void forceResetGhostContainer() {
        reorderGhostViews();
    }

    protected void reorderGhostViews() {

        GhostOrderData ghostOrderData = evidenceViewModel.getGhostOrderData();
        int[] currOrder = ghostOrderData.getCurrOrder();

        for (int j : currOrder) {

            View childView = this.findViewById(j);

            this.removeView(childView);
            this.addView(childView);

        }

    }

    private void updateGhostViews() {
        for(int i = 0; i < this.getChildCount(); i++) {
            GhostView g = (GhostView) this.getChildAt(i);
            g.forceUpdateComponents();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void buildGhostViews(PopupWindow pw) {

        this.popupWindow = pw;

        if(getContext() == null) { return; }

        int[] newGhostOrder = evidenceViewModel.getGhostOrderData().getCurrOrder();

        this.setWeightSum(newGhostOrder.length);

        for (int j : newGhostOrder) {

            GhostView ghostView = new GhostView(getContext()) {

                @Override
                public void createPopup() {

                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }

                    GhostPopupWindow ghostPopupWindow = new GhostPopupWindow(getContext());
                    ghostPopupWindow.setPopupWindow(popupWindow);
                    ghostPopupWindow.build(evidenceViewModel, popupData, j, adRequest);
                    //popupWindow = ghostPopupWindow.getPopupWindow();

                }
            };

            ghostView.build(evidenceViewModel, j);

            this.addView(ghostView);

        }
    }

}
