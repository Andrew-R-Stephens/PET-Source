package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class SanityToolsLayout extends ConstraintLayout {

    public SanityToolsLayout(@NonNull Context context) {
        super(context);

        init(context, null);
    }

    public SanityToolsLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public SanityToolsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public SanityToolsLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    public void init(@NonNull Context c, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {

        inflate(c, R.layout.layout_sanity_tools, this);

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
