package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.views;

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
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;

/**
 * SanityMeterView class
 *
 * @author TritiumGamingStudios
 */
public class SanityMeterView extends View {

    private SanityData sanityData = null;

    private final Paint paint = new Paint();

    private final RectF containerRect = new RectF();
    private final Rect sanityRect = new Rect();

    @ColorInt
    int themeColor = 0;
    private ColorFilter filter = null;

    private Bitmap sanityImg_bottom = null;
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
                        R.color.map_tint_blue),
                PorterDuff.Mode.MULTIPLY);

        buildImages();

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.titleFontColor, typedValue, true);
        //themeColor = typedValue.data;
        themeColor = Color.WHITE;
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
    private Bitmap addLayer(Bitmap baseLayer, Bitmap topLayer) {
        if (baseLayer == null && topLayer != null)
            baseLayer = Bitmap.createBitmap(
                    topLayer.getWidth(),
                    topLayer.getHeight(),
                    topLayer.getConfig());

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

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        float padding = 5;
        float h = getHeight(), w = getWidth();
        if (getWidth() <= getHeight())
            h *= (float) getWidth() / (float) getHeight();
        else
            w *= (float) getHeight() / (float) getWidth();

        if (containerRect != null)
            containerRect.set((float) ((getWidth() * .5) - (w * .5) + padding),
                    (float) ((getHeight() * .5) - (h * .5) + padding),
                    (float) (w + ((getWidth() * .5) - (w * .5)) - padding),
                    (float) (h + ((getHeight() * .5) - (h * .5))) - padding);

        paint.setAntiAlias(true);

        paint.setStrokeWidth(1f);
        paint.setColor(Color.argb(0, 255, 255, 255));
        //paint.setColor(Color.argb(0, 255, 255, 255));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(containerRect, paint);

        float insanityDegree;
        if (sanityData != null) {
            insanityDegree = sanityData.getInsanityDegree();
            paint.setColor(
                    Color.rgb((255),
                            (int) (255 * sanityData.getInsanityPercent()),
                            (int) (255 * sanityData.getInsanityPercent())));
            if (sanityData != null)
                canvas.drawArc(containerRect, 270, insanityDegree, true, paint);

            paint.setStrokeWidth(5f);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(containerRect, 270, insanityDegree, true, paint);

            /*
            float inset = 20;
            paint.setStrokeWidth(1f);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawOval(
                    containerRect.left+inset,
                    containerRect.top+inset,
                    containerRect.right-inset,
                    containerRect.bottom-inset, paint);
            */

            createFilterColor(
                    Color.red(themeColor),
                    (int) (Color.green(themeColor) * sanityData.getInsanityPercent()),
                    (int) (Color.blue(themeColor) * sanityData.getInsanityPercent()));
        }


        if (sanityRect != null) {
            if (sanityImg_bottom != null) {
                paint.setColorFilter(filter);
                sanityRect.set(0, 0,
                        sanityImg_bottom.getWidth(),
                        sanityImg_bottom.getHeight());
                canvas.drawBitmap(sanityImg_bottom, sanityRect, containerRect, paint);
                paint.setColorFilter(null);
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

        if (containerRect != null)
            canvas.drawOval(containerRect, paint);

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

        if (scheduleGarbageCollect)
            System.gc();
    }

}
