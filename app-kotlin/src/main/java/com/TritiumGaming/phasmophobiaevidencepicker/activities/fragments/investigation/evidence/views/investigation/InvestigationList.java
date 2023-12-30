package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.investigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.evidence.EvidenceList;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.google.android.gms.ads.AdRequest;

public class InvestigationList extends LinearLayout {

    protected @LayoutRes int layoutRes = R.layout.item_evidence_tool_section_list;

    protected EvidenceViewModel evidenceViewModel;

    protected PopupWindow popupWindow;
    protected ProgressBar progressBar;

    protected AdRequest adRequest;

    public InvestigationList(Context context) {
        super(context);
        initView();
    }

    public InvestigationList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public InvestigationList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public InvestigationList(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    protected void init(EvidenceViewModel evidenceViewModel, PopupWindow popupWindow, ProgressBar progressBar, AdRequest adRequest) {
        this.evidenceViewModel = evidenceViewModel;
        this.popupWindow = popupWindow;
        this.progressBar = progressBar;
        this.adRequest = adRequest;
    }

    protected void haltProgressAnimation(ProgressBar progressBar) {
        progressBar.animate().alpha(0).setDuration(250).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        progressBar.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).start();
    }

    public void init(PopupWindow popupWindow, ProgressBar progressBar) {
        this.popupWindow = popupWindow;
        this.progressBar = progressBar;

    }

    public @LayoutRes int getLayoutRes() {
        return layoutRes;
    }

}