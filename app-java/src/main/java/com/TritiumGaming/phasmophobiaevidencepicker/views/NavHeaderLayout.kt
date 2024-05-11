package com.TritiumGaming.phasmophobiaevidencepicker.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class NavHeaderLayout extends ConstraintLayout {

    public NavHeaderLayout(@NonNull Context context) {
        super(context);

        init(context, null);
    }

    public NavHeaderLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public NavHeaderLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public NavHeaderLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    public void init(@NonNull Context c, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        inflate(c, R.layout.layout_navigationheader, this);

        if (attrs != null) {
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.NavHeaderLayout);

            int centerTitleResId =
                    a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutCenterTitle, 0);
            int leftTitleResId =
                    a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutLeftTitle, 0);
            int rightTitleResId =
                    a.getResourceId(R.styleable.NavHeaderLayout_NavHeaderLayoutRightTitle, 0);
            int buttonTypeLeft =
                    a.getInt(R.styleable.NavHeaderLayout_NavHeaderLayoutButtonLeft, 0);
            int buttonTypeRight =
                    a.getInt(R.styleable.NavHeaderLayout_NavHeaderLayoutButtonRight, 0);

            a.recycle();

            try {
                setCenterTitle(c.getString(centerTitleResId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                setLeftTitle(c.getString(leftTitleResId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                setRightTitle(c.getString(rightTitleResId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            setButtonLeft(buttonTypeLeft);
            setButtonRight(buttonTypeRight);

        }
    }

    private void setButtonLeft(int buttonType) {
        PETImageButton button_left = findViewById(R.id.button_left);

        if(button_left == null) { return; }

        button_left.setImageLevel(buttonType);
    }

    private void setButtonRight(int buttonType) {
        PETImageButton button_right = findViewById(R.id.button_right);

        if(button_right == null) { return; }

        button_right.setImageLevel(buttonType);
    }

    public void setCenterTitle(String title) {
        AppCompatTextView textView_title = findViewById(R.id.textView_title);

        if(textView_title == null) { return; }

        textView_title.setText(title);
    }

    public void setLeftTitle(String title) {
        AppCompatTextView textView_title = findViewById(R.id.textView_left);

        if(textView_title == null || title == null || title.isEmpty()) { return; }

        textView_title.setText(title);
        textView_title.setVisibility(VISIBLE);
    }

    public void setRightTitle(String title) {
        AppCompatTextView textView_title = findViewById(R.id.textView_right);

        if(textView_title == null || title == null || title.isEmpty()) { return; }

        textView_title.setText(title);
        textView_title.setVisibility(VISIBLE);
    }

}
