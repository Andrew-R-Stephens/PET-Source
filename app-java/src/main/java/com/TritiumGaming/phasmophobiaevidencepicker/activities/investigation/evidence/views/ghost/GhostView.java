package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.ghost;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.GhostModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

public abstract class GhostView extends ConstraintLayout {

    private EvidenceViewModel evidenceViewModel;

    private GhostModel ghostData;

    private @IntegerRes int neutralSelColor, negativeSelColor, positiveSelColor;

    public GhostView(@NonNull Context context) {
        super(context);
        initView();
    }

    public GhostView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GhostView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GhostView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.item_investigation_ghost, this);

        LinearLayoutCompat.LayoutParams params =
                new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, 1f);
        setLayoutParams(params);

        neutralSelColor = ColorUtils.getColorFromAttribute(getContext(), R.attr.neutralSelColor);
        negativeSelColor = ColorUtils.getColorFromAttribute(getContext(), R.attr.negativeSelColor);
        positiveSelColor = ColorUtils.getColorFromAttribute(getContext(), R.attr.positiveSelColor);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void build(@NonNull EvidenceViewModel evidenceViewModel, int groupIndex) {

        this.evidenceViewModel = evidenceViewModel;
        this.ghostData = evidenceViewModel.getInvestigationData().ghostList.getAt(groupIndex);

        AppCompatTextView nameView = findViewById(R.id.label_name);
        LinearLayoutCompat linearLayout_iconRow = findViewById(R.id.icon_container);

        String ghostName = ghostData.getName();
        nameView.setText(ghostName);

        redrawGhostRejectionStatus(ghostData, groupIndex, false);

        if(linearLayout_iconRow != null) {

            int k = 0;
            for (; k < ghostData.getEvidence().length; k++) {

                AppCompatImageView evidenceIcon =
                        (AppCompatImageView) linearLayout_iconRow.getChildAt(k);
                evidenceIcon.setImageResource(ghostData.getEvidence()[k].getIcon());

                switch (ghostData.getEvidence()[k].getRuling()) {
                    case POSITIVE -> evidenceIcon.setColorFilter(positiveSelColor);
                    case NEGATIVE -> evidenceIcon.setColorFilter(negativeSelColor);
                    case NEUTRAL -> evidenceIcon.setColorFilter(neutralSelColor);
                }

            }

            for(; k < linearLayout_iconRow.getChildCount(); k ++) {
                linearLayout_iconRow.getChildAt(k).setVisibility(View.GONE);
            }
        }

        setVisibility(View.INVISIBLE);
        setAlpha(0);
        setId(groupIndex);

        GestureDetector swipeListener =
                new GestureDetector(getContext(), new GhostSwipeListener(groupIndex));

        nameView.setOnTouchListener((v, motionEvent) -> {
            swipeListener.onTouchEvent(motionEvent);
            return true;
        });

        addOnLayoutChangeListener((thisGhostView, i, i1, i2, i3, i4, i5, i6, i7) -> {

            redrawGhostRejectionStatus(ghostData, groupIndex, false);

            if(linearLayout_iconRow != null) {
                for (int l = 0; l < ghostData.getEvidence().length; l++) {

                    AppCompatImageView evidenceIcon =
                            linearLayout_iconRow.getChildAt(l).findViewById(R.id.evidence_icon);

                    switch (ghostData.getEvidence()[l].getRuling()) {
                        case POSITIVE -> evidenceIcon.setColorFilter(positiveSelColor);
                        case NEGATIVE -> evidenceIcon.setColorFilter(negativeSelColor);
                        case NEUTRAL -> evidenceIcon.setColorFilter(neutralSelColor);
                    }
                }
            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                animate()
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                setVisibility(View.VISIBLE);
                            }}
                        ).alpha(1).setStartDelay((long)(10f * groupIndex)).setDuration(100);
            }
        });
    }

    public void forceUpdateComponents() {
        LinearLayoutCompat linearLayout_iconRow = findViewById(R.id.icon_container);
        if(linearLayout_iconRow == null) { return; }

        for (int k = 0; k < ghostData.getEvidence().length; k++) {

            AppCompatImageView evidenceIcon =
                    (AppCompatImageView) linearLayout_iconRow.getChildAt(k);

            switch (ghostData.getEvidence()[k].getRuling()) {
                case POSITIVE -> evidenceIcon.setColorFilter(positiveSelColor);
                case NEGATIVE -> evidenceIcon.setColorFilter(negativeSelColor);
                case NEUTRAL -> evidenceIcon.setColorFilter(neutralSelColor);
            }
        }

        redrawGhostRejectionStatus(ghostData, ghostData.getId(), true);

    }

    private void redrawGhostRejectionStatus(@NonNull GhostModel ghost, int index, boolean animate) {

        int score = ghost.getEvidenceScore();
        AppCompatImageView statusIcon = findViewById(R.id.icon_status);

        boolean rejectionStatus = evidenceViewModel.getRejectionPile()[index];
        if (rejectionStatus) {
            statusIcon.setImageLevel(1);

        } else if (score <= -5) {
            statusIcon.setImageLevel(2+ (int) (Math.random() * 3));

        } else if (score == 3) {
            statusIcon.setImageLevel(5);

        } else {
            statusIcon.setImageLevel(0);
        }

    }

    public class GhostSwipeListener extends GestureDetector.SimpleOnGestureListener {

        private final int index;

        public GhostSwipeListener(int index) {
            super();

            this.index = index;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            boolean status = !evidenceViewModel.swapStatusInRejectedPile(index);

            evidenceViewModel.getGhostOrderData().updateOrder();

            redrawGhostRejectionStatus(evidenceViewModel.getInvestigationData().ghostList.getAt(index), index, true);

            Bundle params = new Bundle();
            params.putString("event_type", "ghost_swiped");
            params.putString("event_details", status ? "ghost_impartial" : "ghost_rejected");
            //analytics.logEvent("event_investigation", params);

            return true;
        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            createPopup();

            return super.onSingleTapConfirmed(e);
        }

    }

    public abstract void createPopup();
}
