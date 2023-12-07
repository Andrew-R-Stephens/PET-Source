package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.evidence;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.EvidencePopupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.ghost.GhostList;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.investigation.InvestigationList;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.google.android.gms.ads.AdRequest;

public class EvidenceList extends InvestigationList {

    private EvidencePopupData popupData;
    private GhostList ghostList;

    public EvidenceList(Context context) {
        super(context);
    }

    public EvidenceList(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EvidenceList(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EvidenceList(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(EvidenceViewModel evidenceViewModel, PopupWindow popupWindow,
                     ProgressBar progressBar, AdRequest adRequest, GhostList ghostList) {
        super.init(evidenceViewModel, popupWindow, progressBar, adRequest);

        this.ghostList = ghostList;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void buildEvidenceViews() {
        if(getContext() == null) { return; }

        for(int i = 0; i < popupData.getCount(); i++) {
            final int groupIndex = i;

            EvidencePopupData.EvidencePopupRecord popupRecord =
                    popupData.getEvidencePopupRecordAt(i);

            EvidenceView evidenceView = new EvidenceView(getContext()) {

                @Override
                public void createPopup() {

                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }

                    EvidencePopupWindow evidencePopupWindow =
                            new EvidencePopupWindow(getContext());
                    evidencePopupWindow.build(
                            evidenceViewModel, popupRecord, groupIndex, adRequest);

                    popupWindow = evidencePopupWindow.getPopupWindow();

                }

                @Override
                public void requestInvalidateGhostContainer() {
                    ghostList.requestInvalidateGhostContainer();
                }

            };

            evidenceView.build(evidenceViewModel, i, ghostList);

            this.addView(evidenceView);

        }

    }

    @SuppressLint("ResourceType")
    public void createEvidenceViews() {

        popupData = new EvidencePopupData(getContext());

        Activity activity = (Activity) getContext();
        if(activity != null) {
            activity.runOnUiThread(() -> {
                buildEvidenceViews();

                this.post(() -> haltProgressAnimation(progressBar));
            });
        }

    }

    public void forceResetEvidenceContainer() {

        for(int i = 0; i < this.getChildCount(); i++) {
            ((EvidenceRadioGroup)this.getChildAt(i).findViewById(R.id.radioGroup))
                    .reset(evidenceViewModel, i);
        }

    }

}
