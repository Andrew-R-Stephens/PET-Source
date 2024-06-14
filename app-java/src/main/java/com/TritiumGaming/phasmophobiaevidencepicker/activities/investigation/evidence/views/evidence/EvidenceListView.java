package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.evidence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.ghost.GhostListView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.InvestigationListView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationpopups.EvidencePopupModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel;
import com.google.android.gms.ads.AdRequest;

public class EvidenceListView extends InvestigationListView {

    private GhostListView ghostList;

    public EvidenceListView(Context context) {
        super(context);
    }

    public EvidenceListView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EvidenceListView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EvidenceListView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(GlobalPreferencesViewModel globalPreferencesViewModel,
                     EvidenceViewModel evidenceViewModel,
                     PopupWindow popupWindow,
                     ProgressBar progressBar, AdRequest adRequest, GhostListView ghostList) {
        super.init(globalPreferencesViewModel, evidenceViewModel,
                popupWindow, progressBar, adRequest);

        this.ghostList = ghostList;
    }

    @SuppressLint("ResourceType")
    public void createPopupWindow(PopupWindow popupWindow) {
        super.createPopupWindow(popupWindow, new EvidencePopupModel(getContext()));
    }

    @Override
    protected void buildViews() {
        for(int i = 0; i < ((EvidencePopupModel)popupData).getCount(); i++) {

            EvidencePopupModel.EvidencePopupRecord popupRecord =
                    ((EvidencePopupModel)popupData).getEvidencePopupRecordAt(i);

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
                            globalPreferencesViewModel.getReorderGhostViews());
                }

            };

            evidenceView.build(evidenceViewModel, i, ghostList);

            this.addView(evidenceView);
        }
    }
}
