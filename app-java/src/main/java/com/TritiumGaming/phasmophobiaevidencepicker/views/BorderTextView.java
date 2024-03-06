package com.TritiumGaming.phasmophobiaevidencepicker.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class BorderTextView extends ConstraintLayout {

    public BorderTextView(Context context) {
        super(context);

        init(context);
    }

    public BorderTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public BorderTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.layout_labeled_textview, this);
    }

}
