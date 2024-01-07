package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

/**
 * WarnTextView class
 *
 * @author TritiumGamingStudios
 */
public class WarnTextView extends AppCompatTextView {

    private boolean state = false;
    private boolean flashOn = false;

    private final int OFF = 0, INACTIVE = 1, ACTIVE = 2;

    private @ColorInt
    int color_active, color_inactive, color_off;

    /**
     * @param context
     */
    public WarnTextView(@NonNull Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public WarnTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public WarnTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        getBackground().setLevel(0);
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

        if (this.state = state) {
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
        if (this.state = state) {
            getBackground().setLevel(ACTIVE);
            if (this.flashOn = flashOn) {
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

    /*
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

        if (this.state = state) {
            setBackgroundTintList(ColorStateList.valueOf(color_active));

            if (flashOn) {
                setTextColor(color_active);
            }
            else {
                setTextColor(color_inactive);
            }
        } else {
            setBackgroundTintList(ColorStateList.valueOf(color_off));
            setTextColor(color_off);
        }
    }

    public void setState(boolean state, boolean flashOn) {
        if (this.state = state) {
            setBackgroundTintList(ColorStateList.valueOf(color_active));
            if (this.flashOn = flashOn) {
                setTextColor(color_active);
            }
            else {
                setTextColor(color_inactive);
            }
        } else {
            setBackgroundTintList(ColorStateList.valueOf(color_off));
            setTextColor(color_off);
        }
    }

    public void reset() {
        setState(false);
    }
    */

}
