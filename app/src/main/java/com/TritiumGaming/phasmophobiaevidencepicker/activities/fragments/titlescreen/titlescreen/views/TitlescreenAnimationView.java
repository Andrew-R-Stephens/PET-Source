package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.AbstractAnimatedGraphic;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.AnimatedGraphicQueue;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedFrostData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedGraphicData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedHandData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedOrbData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata.AnimatedWritingData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

import java.util.ArrayList;

/**
 * TitleScreenAnimationView class
 *
 * @author TritiumGamingStudios
 */
public class TitlescreenAnimationView extends View {

    private TitlescreenViewModel titleScreenViewModel = null;
    private BitmapUtils bitmapUtils = null;

    private ArrayList<Integer> bookwritingResId = new ArrayList<>();

    private final int
            screenW = Resources.getSystem().getDisplayMetrics().widthPixels,
            screenH = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final Paint paint = new Paint();
    private Bitmap
            bitmap_orb = null, bitmap_frost = null, bitmap_hand = null, bitmap_writing = null,
            bitmap_handRot = null, bitmap_writingRot = null;

    /**
     * @param context
     */
    public TitlescreenAnimationView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public TitlescreenAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TitlescreenAnimationView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public TitlescreenAnimationView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * @param titleScreenViewModel
     * @param bitmapUtils
     */
    public void init(
            TitlescreenViewModel titleScreenViewModel,
            BitmapUtils bitmapUtils) {
        this.titleScreenViewModel = titleScreenViewModel;
        this.bitmapUtils = bitmapUtils;

        //Set writing resources
        TypedArray bookwritingArray =
                getResources().obtainTypedArray(R.array.anim_bookwriting_images);
        bookwritingResId = new ArrayList<>();
        for (int i = 0; i < bookwritingArray.length(); i++)
            bookwritingResId.add(bookwritingArray.getResourceId(i, 0));
        bookwritingArray.recycle();

        if (titleScreenViewModel != null &&
                titleScreenViewModel.getAnimationData().getSelectedWriting() == -1) {
            titleScreenViewModel.getAnimationData().
                    setSelectedWriting((int) (Math.random() * bookwritingResId.size()));
        }
    }

    /**
     *
     */
    public void buildImages() {
        bitmap_orb = bitmapUtils.setResource(R.drawable.anim_ghostorb2).
                compileBitmaps(getContext());
        bitmap_frost = bitmapUtils.setResource(R.drawable.anim_frost_sm).
                compileBitmaps(getContext());
        bitmap_hand = bitmapUtils.setResource(R.drawable.anim_hand).
                compileBitmaps(getContext());
        bitmap_writing = bitmapUtils.setResource(
                bookwritingResId.get(titleScreenViewModel.getAnimationData().getSelectedWriting())).
                compileBitmaps(getContext());
    }

    /**
     *
     */
    public void buildData() {

        if (titleScreenViewModel != null) {
            AnimatedGraphicData animationData = titleScreenViewModel.getAnimationData();

            if (animationData.hasData()) {
                for (AbstractAnimatedGraphic animated : animationData.getAllPool()) {
                    if (animated instanceof AnimatedHandData) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = ((AnimatedHandData) animated).
                                    rotateBitmap(bitmap_hand);
                        }
                    } else if (animated instanceof AnimatedWritingData) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            bitmap_writingRot = ((AnimatedWritingData) animated).
                                    rotateBitmap(bitmap_writing);
                        }
                    }
                }
                return;
            }

            //Add orbs
            int numOrbs = 3; //3
            for (int i = 0; i < numOrbs; i++)
                if (BitmapUtils.bitmapExists(bitmap_orb)) {
                    animationData.addToAllPool(new AnimatedOrbData(
                            screenW,
                            screenH));
                }

            //Add hands
            int numHands = 1; //1
            for (int i = 0; i < numHands; i++)
                if (BitmapUtils.bitmapExists(bitmap_hand)) {
                    animationData.addToAllPool(new AnimatedHandData(
                            screenW,
                            screenH,
                            bitmap_hand.getWidth(),
                            bitmap_hand.getHeight()));
                    bitmap_handRot = ((AnimatedHandData) animationData.getLastFromAllPool()).
                            rotateBitmap(bitmap_hand);
                }

            //Add writing
            int numWriting = 1; //1
            for (int i = 0; i < numWriting; i++)
                if (BitmapUtils.bitmapExists(bitmap_writing)) {
                    animationData.addToAllPool(new AnimatedWritingData(
                            screenW,
                            screenH,
                            bitmap_writing.getWidth(),
                            bitmap_writing.getHeight(),
                            animationData));
                    bitmap_writingRot = ((AnimatedWritingData) animationData.
                            getLastFromAllPool()).rotateBitmap(bitmap_writing);
                }

            //Add Frost
            int numFrost = 1; //1
            for (int i = 0; i < numFrost; i++)
                if (BitmapUtils.bitmapExists(bitmap_frost)) {
                    animationData.addToAllPool(new AnimatedFrostData(
                            screenW,
                            screenH));
                }
            //Create Queue
            animationData.setQueue(
                    new AnimatedGraphicQueue(animationData.getAllPoolSize(), 750));
        }
    }

    /**
     *
     */
    public void tick() {

        if (titleScreenViewModel == null)
            return;

        AnimatedGraphicData animationData = titleScreenViewModel.getAnimationData();
        animationData.tick();

        int maxQueue = 3;
        if ((animationData.hasQueue() && animationData.getQueue().canDequeue()) &&
                animationData.getCurrentPoolSize() < maxQueue) {

            AnimatedGraphicQueue animationQueue = animationData.getQueue();

            int index = 0;
            AbstractAnimatedGraphic aTemp = null;
            try {
                index = animationQueue.dequeue();
                aTemp = animationData.getFromAllPool(index);
                animationData.addToCurrentPool(aTemp);
            } catch (IndexOutOfBoundsException e) {
                animationQueue.enqueue(index);
                e.printStackTrace();
            }
            if (aTemp != null) {
                AbstractAnimatedGraphic lastAnimInList = animationData.getLastFromCurrentPool();
                if (lastAnimInList != null) {
                    if (lastAnimInList instanceof AnimatedOrbData) {
                        if (BitmapUtils.bitmapExists(bitmap_orb)) {
                            animationData.setToAllPool(index, new AnimatedOrbData(
                                    getWidth(),
                                    getHeight()));
                        }
                    } else if (lastAnimInList instanceof AnimatedHandData) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            animationData.setToAllPool(index, new AnimatedHandData(
                                    getWidth(),
                                    getHeight(),
                                    bitmap_hand.getWidth(),
                                    bitmap_hand.getHeight()));
                            bitmap_handRot = ((AnimatedHandData) animationData.
                                    getLastFromCurrentPool()).rotateBitmap(bitmap_hand);
                        }
                    } else if (lastAnimInList instanceof AnimatedWritingData) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            animationData.setToAllPool(index, new AnimatedWritingData(
                                    getWidth(),
                                    getHeight(),
                                    bitmap_writing.getWidth(),
                                    bitmap_writing.getHeight(),
                                    animationData));
                            bitmap_writingRot = ((AnimatedWritingData) animationData.
                                    getLastFromCurrentPool()).rotateBitmap(bitmap_writing);
                        }
                    } else if (lastAnimInList instanceof AnimatedFrostData) {
                        if (BitmapUtils.bitmapExists(bitmap_frost)) {
                            animationData.setToAllPool(index, new AnimatedFrostData(
                                    getWidth(), getHeight()));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < animationData.getCurrentPoolSize(); i++) {
            AbstractAnimatedGraphic currentAnim = animationData.getFromCurrentPool(i);
            if (currentAnim != null) {
                currentAnim.tick();

                /*
                 * If the chosen Animated is not alive
                 * remove it from the list
                 * Replace it with a modified item of the same type
                 * Try the next Animated
                 */
                if (!currentAnim.isAlive()) {
                    if (currentAnim instanceof AnimatedHandData) {
                        AnimatedHandData data = ((AnimatedHandData) currentAnim);
                        BitmapUtils.destroyBitmap(bitmap_handRot);
                        if (BitmapUtils.bitmapExists(bitmap_hand))
                            bitmap_handRot = data.rotateBitmap(bitmap_hand);
                    } else if (currentAnim instanceof AnimatedWritingData) {
                        AnimatedWritingData data = ((AnimatedWritingData) currentAnim);
                        animationData.setSelectedWriting(
                                (int) (Math.random() * bookwritingResId.size()));
                        BitmapUtils.destroyBitmap(bitmap_writing);
                        BitmapUtils.destroyBitmap(bitmap_writingRot);
                        bitmapUtils.setResource(
                                bookwritingResId.get(animationData.getSelectedWriting()));
                        bitmap_writing = bitmapUtils.compileBitmaps(getContext());
                        if (BitmapUtils.bitmapExists(bitmap_writing))
                            bitmap_writingRot = data.rotateBitmap(bitmap_writing);
                    }
                    try {
                        animationData.removeFromCurrentPool(currentAnim);
                        i--;
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    System.gc();
                }
            }
        }
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (titleScreenViewModel == null)
            return;

        paint.setStyle(Paint.Style.FILL);

        for (AbstractAnimatedGraphic a : titleScreenViewModel.getAnimationData().getCurrentPool()) {
            if (a != null) {
                paint.setColorFilter(a.getFilter());
                if (a instanceof AnimatedWritingData) {
                    a.draw(canvas, paint, bitmap_writingRot);
                } else if (a instanceof AnimatedHandData) {
                    a.draw(canvas, paint, bitmap_handRot);
                } else if (a instanceof AnimatedOrbData) {
                    a.draw(canvas, paint, bitmap_orb);
                } else if (a instanceof AnimatedFrostData) {
                    a.draw(canvas, paint, bitmap_frost);
                }
            }
        }
    }

    /**
     *
     */
    public void recycleBitmaps() {
        BitmapUtils.destroyBitmap(bitmap_orb);
        BitmapUtils.destroyBitmap(bitmap_frost);
        BitmapUtils.destroyBitmap(bitmap_hand);
        BitmapUtils.destroyBitmap(bitmap_writing);
        BitmapUtils.destroyBitmap(bitmap_handRot);
        BitmapUtils.destroyBitmap(bitmap_writingRot);

        bitmap_orb = null;
        bitmap_frost = null;
        bitmap_hand = null;
        bitmap_writing = null;
        bitmap_handRot = null;
        bitmap_writingRot = null;

        System.gc();
    }

}
