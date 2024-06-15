package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.popups.InvestigationPopupModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel;
import com.google.android.gms.ads.AdRequest;

public abstract class InvestigationListView extends LinearLayout {

    protected GlobalPreferencesViewModel globalPreferencesViewModel;
    protected EvidenceViewModel evidenceViewModel;

    protected InvestigationPopupModel popupData;

    protected PopupWindow popupWindow;
    protected ProgressBar progressBar;

    protected AdRequest adRequest;

    public InvestigationListView(Context context) {
        super(context);
        initView();
    }

    public InvestigationListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public InvestigationListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public InvestigationListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        setLayoutTransition(new LayoutTransition());
        setClipToPadding(false);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
    }

    protected void init(
            GlobalPreferencesViewModel globalPreferencesViewModel,
            EvidenceViewModel evidenceViewModel,
            PopupWindow popupWindow, ProgressBar progressBar, AdRequest adRequest) {
        this.globalPreferencesViewModel = globalPreferencesViewModel;
        this.evidenceViewModel = evidenceViewModel;
        this.adRequest = adRequest;

        init(popupWindow, progressBar);
    }

    public void init(PopupWindow popupWindow, ProgressBar progressBar) {
        this.popupWindow = popupWindow;
        this.progressBar = progressBar;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void createViews() {
        Activity activity = (Activity) getContext();
        if(activity != null) {
            activity.runOnUiThread(() -> {
                buildViews();

                post(() -> haltProgressAnimation(progressBar));
            });
        }
    }

    protected void haltProgressAnimation(@NonNull ProgressBar progressBar) {
        progressBar.animate().alpha(0).setDuration(250).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressBar.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).start();
    }

    protected void createPopupWindow(PopupWindow popupWindow, InvestigationPopupModel popupData) {
        this.popupData = popupData;
        this.popupWindow = popupWindow;
    }

    protected abstract void buildViews();
}
