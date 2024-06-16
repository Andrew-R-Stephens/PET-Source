package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.popups.EvidencePopupWindow;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.evidence.EvidenceView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.popups.EvidencePopupModel;
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
        for(int i = 0; i < ((EvidencePopupModel)getPopupData()).getCount(); i++) {

            EvidencePopupModel.EvidencePopupRecord popupRecord =
                    ((EvidencePopupModel)getPopupData()).getEvidencePopupRecordAt(i);

            EvidenceView evidenceView = new EvidenceView(getContext()) {

                @Override
                public void createPopup() {

                    if (getPopupWindow() != null) {
                        getPopupWindow().dismiss();
                    }

                    EvidencePopupWindow evidencePopupWindow =
                            new EvidencePopupWindow(getContext());
                    evidencePopupWindow.setPopupWindow(getPopupWindow());
                    evidencePopupWindow.build(getEvidenceViewModel(), popupRecord, getAdRequest());
                }

                @Override
                public void requestInvalidateGhostContainer() {
                    ghostList.requestInvalidateGhostContainer(
                            getGlobalPreferencesViewModel().getReorderGhostViews());
                }

            };

            evidenceView.build(getEvidenceViewModel(), i, ghostList);

            this.addView(evidenceView);
        }
    }
}
