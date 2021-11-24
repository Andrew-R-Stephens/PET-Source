package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;

public class SanitySeekBarView extends AppCompatSeekBar {

    private SanityData sanityData;

    private AppCompatTextView sanityPercentTextView;

    public SanitySeekBarView(@NonNull Context context) {
        super(context);
    }

    public SanitySeekBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SanitySeekBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(SanityData sanityData, AppCompatTextView sanityPercentTextView){
        this.sanityData = sanityData;
        this.sanityPercentTextView = sanityPercentTextView;

        setProgress((int)sanityData.getSanityActual());

        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (sanityData != null) {
                        sanityData.setProgressManually(progress);
                        sanityData.tick();

                        sanityPercentTextView.setText(sanityData.toPercentString());
                        sanityPercentTextView.invalidate();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void updateProgress() {
        setProgress((int) sanityData.getSanityActual());
        invalidate();
        sanityPercentTextView.setText(sanityData.toPercentString());
        sanityPercentTextView.invalidate();
    }

    public void resetProgress() {
        setProgress(0);
        invalidate();
        sanityPercentTextView.setText(sanityData.toPercentString());
        sanityPercentTextView.invalidate();
    }

}
