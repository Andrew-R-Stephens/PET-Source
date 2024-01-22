package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.investigation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class InvestigationSection extends ConstraintLayout {

    public InvestigationSection(@NonNull Context context) {
        super(context);
        initView();
    }

    public InvestigationSection(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public InvestigationSection(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public InvestigationSection(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.item_evidence_tool_section, this);
    }

    public void setLabel(String label) {
        ((TextView)findViewById(R.id.label_container)).setText(label);
    }
}
