package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.ghost;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.GhostOrderData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.GhostPopupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.InvestigationList;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel;
import com.google.android.gms.ads.AdRequest;

public class GhostList extends InvestigationList {

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

    public void init(GlobalPreferencesViewModel globalPreferencesViewModel,
                     EvidenceViewModel evidenceViewModel,
                     PopupWindow popupWindow,
                     ProgressBar progressBar, AdRequest adRequest) {
        super.init(globalPreferencesViewModel, evidenceViewModel,
                popupWindow, progressBar, adRequest);
    }

    @SuppressLint("ResourceType")
    public void createPopupWindow(PopupWindow popupWindow) {
        super.createPopupWindow(popupWindow, new GhostPopupData(getContext()));
    }


    public void requestInvalidateGhostContainer(boolean canReorder) {
        if(evidenceViewModel.getGhostOrderData().hasChanges() && canReorder) {
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

    @Override
    protected void buildViews() {

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
                    ghostPopupWindow.build(evidenceViewModel, (GhostPopupData) popupData, j, adRequest);
                }
            };

            ghostView.build(evidenceViewModel, j);

            this.addView(ghostView);

        }
    }

}
