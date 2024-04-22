package com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.views.evidence;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.EvidenceViewModel;

public class EvidenceRadioGroup extends ConstraintLayout {

    public EvidenceRadioGroup(@NonNull Context context) {
        super(context);
    }

    public EvidenceRadioGroup(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EvidenceRadioGroup(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EvidenceRadioGroup(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void reset(@NonNull EvidenceViewModel evidenceViewModel, int groupIndex) {
        for(int i = 0; i < getChildCount(); i++) {
            EvidenceRadioButton button = getChildAt(i).findViewById(R.id.radioIcon);
            button.setState(evidenceViewModel.getRadioButtonsChecked()[groupIndex] == i);
        }
    }

}
