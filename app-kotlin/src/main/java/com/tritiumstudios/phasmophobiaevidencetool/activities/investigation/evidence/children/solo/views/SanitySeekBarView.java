package com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.children.solo.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.SanityData;

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

    public void init(@NonNull SanityData sanityData, @Nullable AppCompatTextView sanityPercentTextView){
        this.sanityData = sanityData;
        this.sanityPercentTextView = sanityPercentTextView;

        setProgress((int)sanityData.getSanityActual());

        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {

                    sanityData.setProgressManually(progress);
                    sanityData.tick();

                    /*sanityPercentTextView.setText(sanityData.toPercentString());*/
                    if(sanityPercentTextView != null) {
                        sanityPercentTextView.setText(formatSanityPercent());
                        //sanityPercentTextView.invalidate();
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

        if(sanityPercentTextView != null) {
            sanityPercentTextView.setText(formatSanityPercent());
        }
    }

    @NonNull
    @SuppressLint("DefaultLocale")
    public String formatSanityPercent() {

        String nbsp = "\u00A0";

        String percentStr = sanityData.toPercentString();
        percentStr = percentStr.replace(nbsp, "").trim();

        int percentNum = 100;
        try {
            percentNum = Integer.parseInt(percentStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String input = String.format("%3d", percentNum);

        StringBuilder output = new StringBuilder();
        int i = 0;
        for(; i < input.length(); i++) {
            if(input.charAt(i) != '0') { break; }
            output.append(nbsp);
        }
        for(; i < input.length(); i++) {
            output.append(input.charAt(i));
        }
        output.append("%");

        return output.toString();
    }

    public void updateProgress() {
        setProgress((int) sanityData.getSanityActual());
        if(sanityPercentTextView != null) {
            sanityPercentTextView.setText(formatSanityPercent());
        }
        invalidate();
    }

    public void resetProgress() {
        setProgress(0);
        if(sanityPercentTextView != null) {
            sanityPercentTextView.setText(formatSanityPercent());
        }
        invalidate();
    }

    /*
    @Override
    public void invalidate() {
        super.invalidate();

        sanityPercentTextView.setText(formatSanityPercent());
    }
    */
}
