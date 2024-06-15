package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.evidence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.compose.ui.platform.ComposeView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.InvestigationComposablesKt;

public abstract class EvidenceView extends ConstraintLayout {

    @ColorInt int fontEmphasisColor = 0;

    public EvidenceView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public EvidenceView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EvidenceView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public EvidenceView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(@NonNull Context context) {
        inflate(context, R.layout.item_investigation_evidence, this);

        LinearLayoutCompat.LayoutParams params =
                new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, 1f);
        setLayoutParams(params);

        fontEmphasisColor = ColorUtils.getColorFromAttribute(getContext(), R.attr.textColorBodyEmphasis);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void build(@NonNull EvidenceViewModel evidenceViewModel, int groupIndex, LinearLayout list_ghosts) {

        AppCompatTextView name = findViewById(R.id.label_name);
        name.setText(evidenceViewModel.getInvestigationData().getEvidenceList()
                .getList().get(groupIndex).getName());

        ComposeView radioGroupComposable = findViewById(R.id.radioGroup);
        InvestigationComposablesKt.setRulingGroup(
                radioGroupComposable, evidenceViewModel, groupIndex,
                () -> {
                    onSelectEvidenceIcon(list_ghosts);
                    return null;
                }
        );

        setVisibility(View.INVISIBLE);
        setAlpha(0);

        animate()
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setVisibility(View.VISIBLE);
                    }}
                )
                .alpha(1)
                .setStartDelay((long)(10f * groupIndex))
                .setDuration(100);

        EvidenceNameGesture evidenceNameGesture = new EvidenceNameGesture();
        GestureDetector nameDetector =
                new GestureDetector(getContext(), evidenceNameGesture);
        name.setOnTouchListener((v, motionEvent) -> nameDetector.onTouchEvent(motionEvent));

    }

    private void onSelectEvidenceIcon(@NonNull LinearLayout ghostContainer) {

        requestInvalidateGhostContainer();

        ScrollView parentScroller = ((ScrollView) ghostContainer.getParent());
        if(parentScroller != null) {
            parentScroller.smoothScrollTo(0, 0);
        }
    }

    public class EvidenceNameGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            Log.i("onDown :", String.valueOf(e.getAction()));
            return true;
        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {
            createPopup();
            return true;
        }
    }

    public abstract void createPopup();

    public abstract void requestInvalidateGhostContainer();

}
