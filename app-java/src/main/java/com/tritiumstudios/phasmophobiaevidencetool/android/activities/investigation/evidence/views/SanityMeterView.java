package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.views;

import android.content.Context;
import android.content.res.Resources;
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
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.data.SanityData;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.utilities.ColorUtils;

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
            sanityStart = Color.GREEN/*R.attr.sanityColorStart*/;
    private @ColorInt int sanityEnd = Color.BLUE/*R.attr.sanityColorEnd*/;

    private final RectF containerRect = new RectF();
    private final Rect sanityRect = new Rect();

    @ColorInt
    int themeColor = 0, sanityTint = 0;
    @Nullable
    private ColorFilter filter = null;

    @Nullable
    private Bitmap sanityImg_bottom = null;
    @Nullable
    private Bitmap sanityImg_top = null;

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
                ContextCompat.getColor(
                        getContext(),
                        R.color.white),
                PorterDuff.Mode.MULTIPLY);

        buildImages();

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.textColorPrimary, typedValue, true);
        themeColor = Color.WHITE;
        theme.resolveAttribute(R.attr.selectedColor, typedValue, true);
        sanityTint = typedValue.data;

        theme.resolveAttribute(R.attr.sanityColorStart, typedValue, true);
        sanityStart = typedValue.data;
        theme.resolveAttribute(R.attr.sanityColorEnd, typedValue, true);
        sanityEnd = typedValue.data;

        setDefaults();
    }

    private void setDefaults() {
        setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    /**
     *
     */
    public void buildImages() {
        sanityImg_bottom = createBitmap(sanityImg_bottom, R.drawable.icon_sanityhead_bottom);
        sanityImg_top = createBitmap(sanityImg_top, R.drawable.icon_sanityhead_top);
    }

    /**
     * @return
     */
    public boolean hasBuiltImages() {
        return sanityImg_bottom != null && sanityImg_top != null;
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
        float padding = 5;
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
        paint.setColor(Color.argb(0, 255, 255, 255));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(containerRect, paint);

        float insanityDegree;
        if (sanityData != null) {
            insanityDegree = sanityData.getInsanityDegree();
            /*paint.setColor(
                    Color.rgb((255),
                            (int) (255 * sanityData.getInsanityPercent()),
                            (int) (255 * sanityData.getInsanityPercent())));*/
            paint.setColor(
                    Color.rgb(
                            (int) (255 * sanityData.getInsanityPercent()),
                            (int) (255 * sanityData.getInsanityPercent()),
                            (int) (255 * sanityData.getInsanityPercent())
                    ));

            @ColorInt int sanityColor =
                    ColorUtils.interpolate(sanityStart, sanityEnd, sanityData.getInsanityPercent());
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

            /*createFilterColor(
                    Color.red(themeColor),
                    (int) (Color.green(themeColor) * sanityData.getInsanityPercent()),
                    (int) (Color.blue(themeColor) * sanityData.getInsanityPercent()));*/
        }

        if (sanityRect != null) {
            if (sanityImg_bottom != null) {
                //paint.setColorFilter(filter);
                paint.setColorFilter(null);

                sanityRect.set(0, 0,
                        sanityImg_bottom.getWidth(),
                        sanityImg_bottom.getHeight());
                canvas.drawBitmap(sanityImg_bottom, sanityRect, containerRect, paint);
                //paint.setColorFilter(null);
            }
            if (sanityImg_top != null) {
                sanityRect.set(0, 0,
                        sanityImg_top.getWidth(),
                        sanityImg_top.getHeight());
                canvas.drawBitmap(sanityImg_top, sanityRect, containerRect, paint);
            }
        }

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);

        if (containerRect != null) {
            canvas.drawOval(containerRect, paint);
        }
        super.onDraw(canvas);
    }

    /**
     *
     */
    public void recycleBitmaps() {
        boolean scheduleGarbageCollect = (sanityImg_top != null || sanityImg_bottom != null);

        if (sanityImg_top != null) {
            sanityImg_top.recycle();
            sanityImg_top = null;
        }
        if (sanityImg_bottom != null) {
            sanityImg_bottom.recycle();
            sanityImg_bottom = null;
        }

        if (scheduleGarbageCollect) {
            System.gc();
        }
    }

}