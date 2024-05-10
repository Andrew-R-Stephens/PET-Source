package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class SanityWarningLayout extends ConstraintLayout {

    public SanityWarningLayout(@NonNull Context context) {
        super(context);

        init(context, null);
    }

    public SanityWarningLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public SanityWarningLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public SanityWarningLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    public void init(@NonNull Context c, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        inflate(c, R.layout.layout_sanity_warnings, this);

        setDefaults();
    }

    private void setDefaults() {
    }

}
