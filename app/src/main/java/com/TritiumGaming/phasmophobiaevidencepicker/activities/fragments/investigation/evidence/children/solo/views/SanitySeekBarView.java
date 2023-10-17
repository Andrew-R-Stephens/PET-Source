package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;

public class SanitySeekBarView extends AppCompatSeekBar {

    private SanityData sanityData;

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

        setProgress((int)sanityData.getSanityActual());

        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {

                    sanityData.setProgressManually(progress);
                    sanityData.tick();

                    /*sanityPercentTextView.setText(sanityData.toPercentString());*/
                    sanityPercentTextView.setText(formatSanityPercent());
                    sanityPercentTextView.invalidate();

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sanityPercentTextView.setText(formatSanityPercent());
    }

    @SuppressLint("DefaultLocale")
    public String formatSanityPercent() {
        String nbsp = "\u00A0";
        String input = String.format("%3d", Integer.parseInt(sanityData.toPercentString()));

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
        formatSanityPercent();
        invalidate();
    }

    public void resetProgress() {
        setProgress(0);
        formatSanityPercent();
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
