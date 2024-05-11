package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.VectorUtils;

/**
 * SanityMeterView class
 *
 * @author TritiumGamingStudios
 */
public class SanityMeterView extends View {

    @Nullable
    private SanityData sanityData = null;

    private final Paint paint = new Paint();

    private @ColorInt int
            sanityPieStartColor, sanityPieEndColor,
            sanityHeadBrainColor, sanityHeadSkullColor,
            sanityBorderColor;

    private final RectF containerRect = new RectF();
    private final Rect sanityRect = new Rect();

    @ColorInt
    int themeColor = 0, sanityTint = 0;
    @Nullable
    private ColorFilter filter;

    @Nullable
    private Bitmap sanityImg_skull, sanityImg_brain, sanityImg_border;

    /**
     * @param context
     */
    public SanityMeterView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public SanityMeterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SanityMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public SanityMeterView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * @param sanityData
     */
    public void init(SanityData sanityData) {
        this.sanityData = sanityData;

        filter = new PorterDuffColorFilter(
                ContextCompat.getColor(getContext(), R.color.white),
                PorterDuff.Mode.MULTIPLY);

        buildImages();
        buildColors();

        setDefaults();
    }

    private void buildColors() {
        themeColor =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.textColorPrimary);
        sanityTint =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.selectedColor);
        sanityPieStartColor =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.sanityPieStartColor);
        sanityPieEndColor =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.sanityPieEndColor);
        sanityHeadBrainColor =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.sanityHeadBrainColor);
        sanityHeadSkullColor =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.sanityHeadSkullColor);
        sanityBorderColor =
                ColorUtils.getColorFromAttribute(getContext(), R.attr.sanityBorderColor);
    }

    private void setDefaults() {
        setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    /**
     *
     */
    public void buildImages() {
        /*sanityImg_skull = createBitmap(sanityImg_skull, R.drawable.icon_sanityhead_skull);
        sanityImg_brain = createBitmap(sanityImg_brain, R.drawable.icon_sanityhead_brain);
        sanityImg_border = createBitmap(sanityImg_skull, R.drawable.icon_sanityhead_border);*/
        sanityImg_skull = VectorUtils.toBitmap(getContext(), R.drawable.icon_sanityhead_skull);
        sanityImg_brain = VectorUtils.toBitmap(getContext(), R.drawable.icon_sanityhead_brain);
        sanityImg_border = VectorUtils.toBitmap(getContext(), R.drawable.icon_sanityhead_border);
    }

    /**
     * @return
     */
    public boolean hasBuiltImages() {
        return sanityImg_skull != null && sanityImg_brain != null;
    }

    /**
     * @param toBitmap
     * @param id
     * @return
     */
    public Bitmap createBitmap(Bitmap toBitmap, int id) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 2;
        o.inJustDecodeBounds = false;

        return addLayer(toBitmap, BitmapFactory.decodeResource(getContext().getResources(),
                id, o));
    }

    /**
     * @param baseLayer
     * @param topLayer
     * @return
     */
    @Nullable
    private Bitmap addLayer(@Nullable Bitmap baseLayer, @Nullable Bitmap topLayer) {
        if (baseLayer == null && topLayer != null) {
            baseLayer = Bitmap.createBitmap(
                    topLayer.getWidth(),
                    topLayer.getHeight(),
                    topLayer.getConfig());
        }
        if (baseLayer != null) {
            Canvas canvas = new Canvas(baseLayer);

            if (topLayer != null) {
                canvas.drawBitmap(topLayer, new Matrix(), null);
                topLayer.recycle();
            }
        }
        System.gc();

        return baseLayer;
    }

    /**
     * @param r
     * @param g
     * @param b
     */
    public void createFilterColor(int r, int g, int b) {
        filter = new PorterDuffColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY);
    }

    public void createFilterColor(@ColorInt int color) {
        filter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        float padding = 4;
        float h = getHeight(), w = getWidth();
        if (getWidth() <= getHeight()) {
            h *= (float) getWidth() / (float) getHeight();
        }
        else {
            w *= (float) getHeight() / (float) getWidth();
        }
        if (containerRect != null) {
            containerRect.set((float) ((getWidth() * .5) - (w * .5) + padding),
                    (float) ((getHeight() * .5) - (h * .5) + padding),
                    (float) (w + ((getWidth() * .5) - (w * .5)) - padding),
                    (float) (h + ((getHeight() * .5) - (h * .5))) - padding);
        }

        paint.setAntiAlias(true);

        paint.setStrokeWidth(1f);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.transparent));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(containerRect, paint);

        float insanityDegree;
        if (sanityData != null) {
            insanityDegree = sanityData.getInsanityDegree();

            @ColorInt int sanityColor =
                    ColorUtils.interpolate(sanityPieStartColor, sanityPieEndColor,
                            sanityData.getInsanityPercent().getValue());
            //createFilterColor(sanityColor);
            paint.setColor(sanityColor);
            //paint.setColorFilter(filter);

            if (sanityData != null) {
                canvas.drawArc(containerRect, 270, insanityDegree, true, paint);
            }
            paint.setColorFilter(null);
            paint.setStrokeWidth(5f);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(containerRect, 270, insanityDegree, true, paint);

        }

        if (sanityRect != null) {

            sanityRect.set((int)padding, (int)padding,
                    sanityImg_skull.getWidth() - (int)padding,
                    sanityImg_skull.getHeight() - (int)padding);

            paint.setColorFilter(null);
            paint.setColor(sanityHeadSkullColor);
            canvas.drawBitmap(sanityImg_skull, sanityRect, containerRect, paint);

            paint.setColorFilter(null);
            paint.setColor(sanityHeadBrainColor);
            canvas.drawBitmap(sanityImg_brain, sanityRect, containerRect, paint);


            paint.setColorFilter(null);
            paint.setColor(sanityBorderColor);
            canvas.drawBitmap(sanityImg_border, sanityRect, containerRect, paint);

        }

        paint.setColor(sanityBorderColor);
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawOval(containerRect, paint);

        super.onDraw(canvas);
    }

    /**
     *
     */
    public void recycleBitmaps() {
        boolean scheduleGarbageCollect = (sanityImg_brain != null || sanityImg_skull != null);

        if (sanityImg_brain != null) {
            sanityImg_brain.recycle();
            sanityImg_brain = null;
        }
        if (sanityImg_skull != null) {
            sanityImg_skull.recycle();
            sanityImg_skull = null;
        }

        if (scheduleGarbageCollect) {
            System.gc();
        }
    }

}