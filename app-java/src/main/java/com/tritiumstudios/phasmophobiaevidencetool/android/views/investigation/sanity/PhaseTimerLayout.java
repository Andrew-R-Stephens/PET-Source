package com.tritiumstudios.phasmophobiaevidencetool.android.views.investigation.sanity;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tritiumstudios.phasmophobiaevidencetool.R;

public class PhaseTimerLayout extends ConstraintLayout {

    public PhaseTimerLayout(@NonNull Context context) {
        super(context);

        init(context, null);
    }

    public PhaseTimerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public PhaseTimerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public PhaseTimerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    protected void init(@NonNull Context c, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {

        inflate(c, R.layout.layout_phasetimer, this);

        setDefaults();

        /*
        if (attrs != null) {
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.PETImageButton);

            setImageResource(
                    a.getResourceId(
                            R.styleable.PETImageButton_PETImageButtonBackground,
                            R.drawable.icon_button_designs));

            setImageLevel(
                    a.getInt(
                            R.styleable.PETImageButton_PETImageButtonType, 0));

            a.recycle();
        }
        */


    }

    private void setDefaults() {
    }


}
