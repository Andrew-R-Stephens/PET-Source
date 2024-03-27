package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class SanityCarouselView extends ConstraintLayout {

    public SanityCarouselView(@NonNull Context context) {
        super(context);

        init(context, null);
    }

    public SanityCarouselView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public SanityCarouselView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    public SanityCarouselView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    public void init(@NonNull Context c, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {

        inflate(c, R.layout.layout_sanity_carousel, this);

        if (attrs != null) {
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.SanityCarouselView);

            String title =
                    a.getString(R.styleable.SanityCarouselView_SanityCarouselViewTitle);
            String name =
                    a.getString(R.styleable.SanityCarouselView_SanityCarouselViewName);

            setTitle(title);
            setName(name);

            a.recycle();
        }

        setDefaults();

    }

    private void setDefaults() {

    }

    public void setTitle(int title) {
        setTitle(getContext().getString(title));
    }

    public void setTitle(String title) {
        AppCompatTextView titleView = findViewById(R.id.carousel_title);

        if(titleView != null) {
            titleView.setText(title);
        }
    }

    public void setName(int name) {
        setName(getContext().getString(name));
    }

    public void setName(String name) {
        AppCompatTextView nameView = findViewById(R.id.carousel_name);

        if(nameView != null) {
            nameView.setText(name);
        }
    }

}
