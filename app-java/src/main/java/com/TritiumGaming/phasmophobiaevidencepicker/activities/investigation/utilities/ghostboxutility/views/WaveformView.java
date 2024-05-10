package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.ghostboxutility.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;

/**
 * WaveformView class
 *
 * @author TritiumGamingStudios
 */
public class WaveformView extends View {
    @Nullable
    private byte[] mBytes;
    private float[] mPoints;

    private final float sampleRate = 128.0f;
    private final float peakAmplitude = .8f;

    private double xSpace = 0;
    private final double ySpace = .1;

    private final Rect waveformRect = new Rect();
    private final Rect frameRect = new Rect();

    private final Paint waveformPaint = new Paint();
    private final Paint framePaint = new Paint();
    private final Paint gridPaint = new Paint();
    private final Paint bgPaint = new Paint();

    /**
     * @param context
     */
    public WaveformView(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public WaveformView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public WaveformView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public WaveformView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     *
     */
    public void init() {
        mBytes = null;

        @ColorInt int color_frame = Color.WHITE,
                color_grid = Color.argb(50, 150, 0, 0),
                color_bg = Color.argb(50, 200, 0, 0),
                color_waveform = Color.RED;

        color_waveform = ColorUtils.getColorFromAttribute(getContext(), R.attr.textColorPrimary);
        color_frame = ColorUtils.getColorFromAttribute(getContext(), R.attr.textColorSecondary);

        waveformPaint.setStrokeWidth(5f);
        waveformPaint.setStyle(Paint.Style.STROKE);
        waveformPaint.setAntiAlias(true);
        waveformPaint.setColor(color_waveform);

        framePaint.setStrokeWidth(10f);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setAntiAlias(true);
        framePaint.setColor(color_frame);

        gridPaint.setStrokeWidth(1f);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setColor(color_grid);

        bgPaint.setStrokeWidth(1f);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(color_bg);

    }

    /**
     * @param bytes
     */
    public void updateVisualizer(byte[] bytes) {
        this.mBytes = bytes;

        invalidate();
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        xSpace = ySpace * ((double) getHeight() / (double) getWidth());

        frameRect.set(0, 0, getWidth(), getHeight());
        waveformRect.set(0, (int) (frameRect.height() * .2), frameRect.width(),
                (int) (frameRect.height() * .7));

        canvas.drawRect(frameRect, bgPaint);

        for (int y = 0; y < frameRect.height(); y += (int) (frameRect.height() * ySpace)) {
            canvas.drawLine(0, y, frameRect.width(), y, gridPaint);
        }
        for (int x = 0; x < frameRect.width(); x += (int) (frameRect.width() * xSpace)) {
            canvas.drawLine(x, 0, x, frameRect.height(), gridPaint);
        }

        if (mBytes == null) {
            canvas.drawLine(
                    0, waveformRect.height(),
                    waveformRect.width(), waveformRect.height(),
                    waveformPaint);
            return;
        }

        if (mPoints == null || mPoints.length < mBytes.length * 4) {
            mPoints = new float[mBytes.length * 4];
        }

        for (int i = 0; i < mBytes.length - 1; i++) {
            mPoints[i * 4] = (float) waveformRect.width() * i / (float) (mBytes.length - 1);
            mPoints[i * 4 + 1] = (float) waveformRect.height() + ((byte) (mBytes[i] + sampleRate)) * (waveformRect.height() * peakAmplitude) / sampleRate;
            mPoints[i * 4 + 2] = (float) waveformRect.width() * (i + 1) / ((float) mBytes.length - 1);
            mPoints[i * 4 + 3] = (float) waveformRect.height() + ((byte) (mBytes[i + 1] + sampleRate)) * (waveformRect.height() * peakAmplitude) / sampleRate;
        }

        canvas.drawLines(mPoints, waveformPaint);
    }
}