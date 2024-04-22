package com.tritiumstudios.phasmophobiaevidencetool.android.views.investigation.sanity;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.tritiumstudios.phasmophobiaevidencetool.R;

/**
 * WarnTextView class
 *
 * @author TritiumGamingStudios
 */
public class SanityWarningView extends AppCompatTextView {

    private boolean state = false;
    private boolean flashOn = false;

    private final int OFF = 0, INACTIVE = 1, ACTIVE = 2;

    private @ColorInt
    int color_active, color_inactive, color_off;

    /**
     * @param context
     */
    public SanityWarningView(@NonNull Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public SanityWarningView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SanityWarningView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.light_active, typedValue, true);
        color_active = typedValue.data;
        theme.resolveAttribute(R.attr.light_inactive, typedValue, true);
        color_inactive = typedValue.data;
        theme.resolveAttribute(R.attr.light_off, typedValue, true);
        color_off = typedValue.data;

        setDefaults();
    }

    private void setDefaults() {
        setBackgroundResource(R.drawable.rect_border);
        getBackground().setLevel(0);

        setGravity(Gravity.CENTER);
        setMaxLines(1);
        setPaddingDefaults();
    }

    public void toggleFlash(boolean canFlash) {
        int c;

        if (this.state) {
            if (canFlash && (flashOn = !flashOn)) {
                c = color_active;
            }
            else {
                c = color_inactive;
            }
        } else {
            c = color_off;
        }

        setTextColor(c);
    }


    public void setState(boolean state) {
        this.state = state;

        if (this.state) {
            getBackground().setLevel(ACTIVE);

            if (flashOn) {
                setTextColor(color_active);
            }
            else {
                setTextColor(color_inactive);
            }
        } else {
            getBackground().setLevel(OFF);
            setTextColor(color_off);
        }
    }

    public void setState(boolean state, boolean flashOn) {
        this.state = state;
        this.flashOn = flashOn;

        if (this.state) {
            getBackground().setLevel(ACTIVE);
            if (this.flashOn) {
                setTextColor(color_active);
            }
            else {
                setTextColor(color_inactive);
            }
        } else {
            getBackground().setLevel(OFF);
            setTextColor(color_off);
        }
    }

    public void reset() {
        setState(false);
    }

    private void setPaddingDefaults() {
        setPadding(
                getPaddingLeft() == 0 ? 4 : getPaddingLeft(),
                getPaddingTop() == 0 ? 4 : getPaddingTop(),
                getPaddingRight() == 0 ? 4 : getPaddingRight(),
                getPaddingBottom() == 0 ? 4 : getPaddingBottom());
    }

}
