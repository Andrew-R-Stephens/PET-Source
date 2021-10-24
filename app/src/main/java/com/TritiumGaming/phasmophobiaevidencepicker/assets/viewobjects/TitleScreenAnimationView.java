package com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.Animated;
import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.AnimationQueue;
import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.FrostscreenData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.GhostOrbData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.GhostWritingData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.HandprintData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.AnimationData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitleScreenViewModel;

import java.util.ArrayList;

/**
 * TitleScreenAnimationView class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenAnimationView extends View {

    private TitleScreenViewModel titleScreenViewModel = null;
    private BitmapUtils bitmapUtils = null;

    private ArrayList<Integer> bookwritingResId = new ArrayList<>();

    private final int screenW = Resources.getSystem().getDisplayMetrics().widthPixels,
            screenH = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final Paint paint = new Paint();
    private Bitmap
            bitmap_orb = null, bitmap_frost = null, bitmap_hand = null, bitmap_writing = null,
            bitmap_handRot = null, bitmap_writingRot = null;

    /**
     * TitleScreenAnimationView constructor
     *
     * TODO
     *
     * @param context
     */
    public TitleScreenAnimationView(Context context) {
        super(context);
    }

    /**
     * TitleScreenAnimationView constructor
     *
     * TODO
     *
     * @param context
     * @param attrs
     */
    public TitleScreenAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * TitleScreenAnimationView constructor
     *
     * TODO
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TitleScreenAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * TitleScreenAnimationView constructor
     *
     * TODO
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public TitleScreenAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * init
     *
     * TODO
     *
     * @param titleScreenViewModel
     * @param bitmapUtils
     */
    public void init(TitleScreenViewModel titleScreenViewModel, BitmapUtils bitmapUtils) {
        this.titleScreenViewModel = titleScreenViewModel;
        this.bitmapUtils = bitmapUtils;

        //Set writing resources
        TypedArray bookwritingArray = getResources().obtainTypedArray(R.array.anim_bookwriting_images);
        bookwritingResId = new ArrayList<>();
        for (int i = 0; i < bookwritingArray.length(); i++)
            bookwritingResId.add(bookwritingArray.getResourceId(i, 0));
        bookwritingArray.recycle();

        if(titleScreenViewModel != null && titleScreenViewModel.getAnimationData().getSelectedWriting() == -1) {
            titleScreenViewModel.getAnimationData().setSelectedWriting((int) (Math.random() * bookwritingResId.size()));
        }
    }

    /**
     * buildImages
     *
     * TODO
     */
    public void buildImages() {
        //Log.d("BuildImages", "Orb");
        bitmap_orb = bitmapUtils.setResource(R.drawable.anim_ghostorb).compileBitmaps(getContext());
        //Log.d("BuildImages", "Frost");
        bitmap_frost = bitmapUtils.setResource(R.drawable.anim_frost_sm).compileBitmaps(getContext());
        //Log.d("BuildImages", "Hand");
        bitmap_hand = bitmapUtils.setResource(R.drawable.anim_hand).compileBitmaps(getContext());
        //Log.d("BuildImages", "Writing");
        bitmap_writing = bitmapUtils.setResource(bookwritingResId.get(titleScreenViewModel.getAnimationData().getSelectedWriting())).compileBitmaps(getContext());
    }

    /**
     * buildData
     *
     * TODO
     */
    public void buildData(){

        if(titleScreenViewModel != null) {
            AnimationData animationData = titleScreenViewModel.getAnimationData();

            if(animationData.hasData()) {

                for(Animated animated: animationData.getAllPool()) {
                    if(animated instanceof HandprintData) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = ((HandprintData) animated).rotateBitmap(bitmap_hand);
                        }
                    }
                    else if(animated instanceof GhostWritingData) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            bitmap_writingRot = ((GhostWritingData) animated).rotateBitmap(bitmap_writing);
                        }
                    }
                }
                return;
            }

            //Add orbs
            int numOrbs = 3; //3
            for (int i = 0; i < numOrbs; i++)
                if(BitmapUtils.bitmapExists(bitmap_orb)) {
                    //animationData.addToAllPool(createNewGhostOrbData(screenW, screenH));
                    animationData.addToAllPool(new GhostOrbData(screenW, screenH));
                }

            //Add hands
            int numHands = 1; //1
            for (int i = 0; i < numHands; i++)
                if(BitmapUtils.bitmapExists(bitmap_hand)) {
                    //animationData.addToAllPool(createNewHandprintData(screenW, screenH));
                    animationData.addToAllPool(new HandprintData(screenW, screenH, bitmap_hand.getWidth(), bitmap_hand.getHeight()));
                    bitmap_handRot = ((HandprintData) animationData.getLastFromAllPool()).rotateBitmap(bitmap_hand);
                }

            //Add writing
            int numWriting = 1; //1
            for (int i = 0; i < numWriting; i++)
                if(BitmapUtils.bitmapExists(bitmap_writing)) {
                    //animationData.addToAllPool(createNewGhostWritingData(screenW, screenH));
                    animationData.addToAllPool(new GhostWritingData(screenW, screenH, bitmap_writing.getWidth(), bitmap_writing.getHeight(), animationData));
                    bitmap_writingRot = ((GhostWritingData) animationData.getLastFromAllPool()).rotateBitmap(bitmap_writing);
                }

            //Add Frost
            int numFrost = 1; //1
            for (int i = 0; i < numFrost; i++)
                if(BitmapUtils.bitmapExists(bitmap_frost)) {
                    //animationData.addToAllPool(createNewFrostData(screenW, screenH));
                    animationData.addToAllPool(new FrostscreenData(screenW, screenH));
                }
            //Create Queue
            animationData.setQueue(new AnimationQueue(animationData.getAllPoolSize(), 750));
        }
    }

    /**
     * tick
     *
     * TODO
     */
    public void tick(){

        if(titleScreenViewModel == null)
            return;

        AnimationData animationData = titleScreenViewModel.getAnimationData();
        animationData.tick();

        int maxQueue = 3;
        if((animationData.hasQueue() && animationData.getQueue().canDequeue()) && animationData.getCurrentPoolSize() < maxQueue) {

            AnimationQueue animationQueue = animationData.getQueue();

            int index = 0;
            Animated aTemp = null;
            try {
                index = animationQueue.dequeue();
                aTemp = animationData.getFromAllPool(index);
                animationData.addToCurrentPool(aTemp);
            } catch (IndexOutOfBoundsException e){
                animationQueue.enqueue(index);
                e.printStackTrace();
            }
            if(aTemp != null) {

                if (animationData.getLastFromCurrentPool() instanceof GhostOrbData) {
                    if(BitmapUtils.bitmapExists(bitmap_orb)) {
                        //animationData.setToAllPool(index, createNewGhostOrbData());
                        animationData.setToAllPool(index, new GhostOrbData(getWidth(), getHeight()));
                    }
                } else if (animationData.getLastFromCurrentPool() instanceof HandprintData) {
                    if(BitmapUtils.bitmapExists(bitmap_hand)) {
                        //animationData.setToAllPool(index, createNewHandprintData());
                        animationData.setToAllPool(index, new HandprintData(getWidth(), getHeight(), bitmap_hand.getWidth(), bitmap_hand.getHeight()));
                        bitmap_handRot = ((HandprintData) animationData.getLastFromCurrentPool()).rotateBitmap(bitmap_hand);
                    }
                } else if (animationData.getLastFromCurrentPool() instanceof GhostWritingData) {
                    if(BitmapUtils.bitmapExists(bitmap_writing)) {
                        //animationData.setToAllPool(index, createNewGhostWritingData());
                        animationData.setToAllPool(index, new GhostWritingData(getWidth(), getHeight(), bitmap_writing.getWidth(), bitmap_writing.getHeight(), animationData));
                        bitmap_writingRot = ((GhostWritingData) animationData.getLastFromCurrentPool()).rotateBitmap(bitmap_writing);
                    }
                } else if (animationData.getLastFromCurrentPool() instanceof FrostscreenData) {
                    if(BitmapUtils.bitmapExists(bitmap_frost)) {
                        //animationData.setToAllPool(index, createNewFrostData());
                        animationData.setToAllPool(index, new FrostscreenData(getWidth(), getHeight()));
                    }
                }
            }
        }

        for (int i = 0; i < animationData.getCurrentPoolSize(); i++) {
            Animated currentAnim = animationData.getFromCurrentPool(i);
            if(currentAnim != null) {
                currentAnim.tick();

                /*
                 * If the chosen Animated is not alive
                 * remove it from the list
                 * Replace it with a modified item of the same type
                 * Try the next Animated
                 */
                if (!currentAnim.isAlive()) {
                    if (currentAnim instanceof HandprintData) {
                        HandprintData data = ((HandprintData) currentAnim);
                        if (bitmap_handRot != null)
                            bitmap_handRot.recycle();
                        if(BitmapUtils.bitmapExists(bitmap_hand))
                            bitmap_handRot = data.rotateBitmap(bitmap_hand);
                    } else if (currentAnim instanceof GhostWritingData) {
                        GhostWritingData data = ((GhostWritingData) currentAnim);
                        animationData.setSelectedWriting((int) (Math.random() * bookwritingResId.size()));
                        if (bitmap_writing != null)
                            bitmap_writing.recycle();
                        bitmap_writing = null;
                        if (bitmap_writingRot != null)
                            bitmap_writingRot.recycle();
                        bitmap_writingRot = null;
                        bitmapUtils.setResource(bookwritingResId.get(animationData.getSelectedWriting()));
                        bitmap_writing = bitmapUtils.compileBitmaps(getContext());
                        if(BitmapUtils.bitmapExists(bitmap_writing))
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
     * onDraw
     *
     * TODO
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(titleScreenViewModel == null)
            return;

        paint.setStyle(Paint.Style.FILL);

        for (Animated a : titleScreenViewModel.getAnimationData().getCurrentPool()) {
            if(a != null) {
                paint.setColorFilter(a.getFilter());
                if (a instanceof GhostWritingData) {
                    a.draw(canvas, paint, bitmap_writingRot);
                } else if (a instanceof HandprintData) {
                    a.draw(canvas, paint, bitmap_handRot);
                } else if (a instanceof GhostOrbData) {
                    a.draw(canvas, paint, bitmap_orb);
                } else if (a instanceof FrostscreenData) {
                    a.draw(canvas, paint, bitmap_frost);
                }
            }
        }
    }

    /**
     * recycleBitmaps
     *
     * TODO
     */
    public void recycleBitmaps(){
        if(bitmap_orb != null)
            bitmap_orb.recycle();
        if(bitmap_frost != null)
            bitmap_frost.recycle();
        if(bitmap_hand != null)
            bitmap_hand.recycle();
        if(bitmap_writing != null)
            bitmap_writing.recycle();
        if(bitmap_handRot != null)
            bitmap_handRot.recycle();
        if(bitmap_writingRot != null)
            bitmap_writingRot.recycle();

        System.gc();

        bitmap_orb = null;
        bitmap_frost = null;
        bitmap_hand = null;
        bitmap_writing = null;
        bitmap_handRot = null;
        bitmap_writingRot = null;
    }
}
