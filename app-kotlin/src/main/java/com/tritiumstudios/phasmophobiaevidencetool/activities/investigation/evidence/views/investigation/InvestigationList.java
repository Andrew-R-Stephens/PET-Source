package com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.views.investigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.EvidenceViewModel;
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
        this.adRequest = adRequest;

        init(popupWindow, progressBar);
    }

    public void init(PopupWindow popupWindow, ProgressBar progressBar) {
        this.popupWindow = popupWindow;
        this.progressBar = progressBar;
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

    public @LayoutRes int getLayoutRes() {
        return layoutRes;
    }

}
