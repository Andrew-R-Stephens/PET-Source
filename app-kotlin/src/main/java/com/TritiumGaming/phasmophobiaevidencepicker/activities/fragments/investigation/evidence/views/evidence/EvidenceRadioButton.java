package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views.evidence;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class EvidenceRadioButton extends AppCompatImageView {


    public EvidenceRadioButton(@NonNull Context context) {
        super(context);
        init();
    }

    public EvidenceRadioButton(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EvidenceRadioButton(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        setImageResource(R.drawable.investigation_evidence_selector);
    }

    public void setState(boolean isSelected) {
        setImageState(
                new int[] {
                        (isSelected) ?
                                R.attr.state_selected : -R.attr.state_selected
                },
                true);
    }

}
