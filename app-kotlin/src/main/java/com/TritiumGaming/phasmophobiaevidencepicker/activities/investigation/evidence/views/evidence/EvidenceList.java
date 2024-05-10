package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.evidence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.EvidencePopupData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.ghost.GhostList;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.InvestigationList;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel;
import com.google.android.gms.ads.AdRequest;

public class EvidenceList extends InvestigationList {

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

    public void init(GlobalPreferencesViewModel globalPreferencesViewModel,
                     EvidenceViewModel evidenceViewModel,
                     PopupWindow popupWindow,
                     ProgressBar progressBar, AdRequest adRequest, GhostList ghostList) {
        super.init(globalPreferencesViewModel, evidenceViewModel,
                popupWindow, progressBar, adRequest);

        this.ghostList = ghostList;
    }

    @SuppressLint("ResourceType")
    public void createPopupWindow(PopupWindow popupWindow) {
        super.createPopupWindow(popupWindow, new EvidencePopupData(getContext()));
    }

    @Override
    protected void buildViews() {
        for(int i = 0; i < ((EvidencePopupData)popupData).getCount(); i++) {

            EvidencePopupData.EvidencePopupRecord popupRecord =
                    ((EvidencePopupData)popupData).getEvidencePopupRecordAt(i);

            EvidenceView evidenceView = new EvidenceView(getContext()) {

                @Override
                public void createPopup() {

                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }

                    EvidencePopupWindow evidencePopupWindow =
                            new EvidencePopupWindow(getContext());
                    evidencePopupWindow.setPopupWindow(popupWindow);
                    evidencePopupWindow.build(evidenceViewModel, popupRecord, adRequest);
                }

                @Override
                public void requestInvalidateGhostContainer() {
                    ghostList.requestInvalidateGhostContainer(
                            globalPreferencesViewModel.canReorderGhostViews());
                }

            };

            evidenceView.build(evidenceViewModel, i, ghostList);

            this.addView(evidenceView);
        }
    }
}
