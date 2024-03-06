package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AbstractAnimatedGraphic;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.AnimatedGraphicQueue;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedFrostData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedGraphicData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedHandData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedMirrorData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedOrbData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedWritingData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * TitleScreenAnimationView class
 *
 * @author TritiumGamingStudios
 */
public class StartScreenAnimationView extends View {

    private MainMenuViewModel titleScreenViewModel;

    private BitmapUtils bitmapUtils;

    private Thread thread_initAnima, thread_tickAnim, thread_renderAnim, thread_initReady;

    private boolean canAnimate = true;

    private ArrayList<Integer> writingResIds = new ArrayList<>();
    private ArrayList<Integer> handResIds = new ArrayList<>();

    private final Paint paint = new Paint();
    private Bitmap bitmap_orb, bitmap_hand, bitmap_writing, bitmap_frost, bitmap_mirror,
            bitmap_handRot, bitmap_writingRot;

    /**
     * @param context The parent Context
     */
    public StartScreenAnimationView(Context context) {
        super(context);
    }

    /**
     * @param context The parent Context
     * @param attrs The attributes given on init
     */
    public StartScreenAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context The parent Context
     * @param attrs The attributes given on init
     * @param defStyleAttr The style attributes given on init
     */
    public StartScreenAnimationView(
            Context context,
            @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param titleScreenViewModel The TitleScreenViewModel which contains necessary Animation data
     * @param bitmapUtils The BitmapUtils data which is used across all animations
     */
    public void init(
            MainMenuViewModel titleScreenViewModel,
            BitmapUtils bitmapUtils) {

        this.titleScreenViewModel = titleScreenViewModel;
        this.bitmapUtils = bitmapUtils;

        AnimatedGraphicData data = this.titleScreenViewModel.getAnimationData();

        //Set writing resources
        TypedArray bookwritingArray =
                getResources().obtainTypedArray(R.array.anim_bookwriting_images);
        writingResIds = new ArrayList<>();
        for (int i = 0; i < bookwritingArray.length(); i++) {
            writingResIds.add(bookwritingArray.getResourceId(i, 0));
        }
        bookwritingArray.recycle();

        //Set hand resources
        TypedArray handUVArray =
                getResources().obtainTypedArray(R.array.anim_hand_images);
        handResIds = new ArrayList<>();
        for (int i = 0; i < handUVArray.length(); i++) {
            handResIds.add(handUVArray.getResourceId(i, 0));
        }
        handUVArray.recycle();

        if (data.getSelectedWriting() == -1) {
            data.setSelectedWriting((int) (Math.random() * writingResIds.size()));
        }

        if (data.getSelectedHand() == -1) {
            data.setSelectedHand((int) (Math.random() * handResIds.size()));
        }

        buildImages();
        buildData();
    }

    /**
     *
     */
    public void buildImages() {
        AnimatedGraphicData data = titleScreenViewModel.getAnimationData();

        bitmap_orb = bitmapUtils.setResource(R.drawable.anim_ghostorb).
                compileBitmaps(getContext());
        bitmap_frost = bitmapUtils.setResource(R.drawable.anim_frost).
                compileBitmaps(getContext());
        bitmap_hand = bitmapUtils.setResource(
                handResIds.get(data.getSelectedHand())).
                compileBitmaps(getContext());
        bitmap_writing = bitmapUtils.setResource(
                writingResIds.get(data.getSelectedWriting())).
                compileBitmaps(getContext());

        bitmap_mirror = bitmapUtils.setResource(R.drawable.anim_mirror_crack)
                .addResource(R.drawable.anim_mirror_gradient, PorterDuff.Mode.MULTIPLY)
                .addResource(R.drawable.anim_mirror_crack, PorterDuff.Mode.MULTIPLY)
                .compileBitmaps(getContext());

    }

    /**
     *
     */
    public void buildData() {

        if (titleScreenViewModel == null) { return; }

        int
            screenW = Resources.getSystem().getDisplayMetrics().widthPixels,
            screenH = Resources.getSystem().getDisplayMetrics().heightPixels;

        AnimatedGraphicData animationData = titleScreenViewModel.getAnimationData();

        for (AbstractAnimatedGraphic g : animationData.getCurrentPool()) {
            if(g != null) {
                g.initDims(screenW, screenH);
            }
        }

        if (animationData.hasData()) {
            for (AbstractAnimatedGraphic animated : animationData.getAllPool()) {
                if (animated instanceof AnimatedHandData a) {
                    try {
                        bitmap_handRot = a.rotateBitmap(bitmap_hand);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                } else if (animated instanceof AnimatedWritingData a) {
                    try {
                        bitmap_writingRot = a.rotateBitmap(bitmap_writing);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
            return;
        }

        short ORB_COUNT = 3, HAND_COUNT = 1,  WRITING_COUNT = 1,
                MIRROR_COUNT = 1, FROST_COUNT = 1;

        //Add orbs
        for (int i = 0; i < ORB_COUNT; i++) {
            if (BitmapUtils.bitmapExists(bitmap_orb)) {
                AnimatedOrbData data = new AnimatedOrbData(screenW, screenH);
                animationData.addToAllPool(data);
            }
        }
        //Add hands
        for (int i = 0; i < HAND_COUNT; i++) {
            if (BitmapUtils.bitmapExists(bitmap_hand)) {
                int bW = bitmap_hand.getWidth();
                int bH = bitmap_hand.getHeight();
                AnimatedHandData data = new AnimatedHandData(
                        screenW, screenH, bW, bH);
                animationData.addToAllPool(data);
                try {
                    bitmap_handRot = data.rotateBitmap(bitmap_hand);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }

        //Add writing
        for (int i = 0; i < WRITING_COUNT; i++) {
            if (BitmapUtils.bitmapExists(bitmap_writing)) {
                int bW = bitmap_writing.getWidth();
                int bH = bitmap_writing.getHeight();
                AnimatedWritingData data = new AnimatedWritingData(
                        screenW, screenH, bW, bH, animationData);
                animationData.addToAllPool(data);
                try {
                    bitmap_writingRot = data.rotateBitmap(bitmap_writing);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }

        //Add Frost
        for (int i = 0; i < FROST_COUNT; i++) {
            if (BitmapUtils.bitmapExists(bitmap_frost)) {
                AnimatedFrostData data = new AnimatedFrostData(screenW, screenH);
                animationData.addToAllPool(data);
            }
        }

        //Add Mirror
        for (int i = 0; i < MIRROR_COUNT; i++) {
            if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                AnimatedMirrorData data = new AnimatedMirrorData(screenW, screenH);
                animationData.addToAllPool(data);
            }
        }

        //Create Queue
        animationData.setQueue(
                new AnimatedGraphicQueue(animationData.getAllPoolSize(), 750));
    }

    /**
     *
     */
    public void tick() {

        if (titleScreenViewModel == null)
            return;

        int
            screenW = Resources.getSystem().getDisplayMetrics().widthPixels,
            screenH = Resources.getSystem().getDisplayMetrics().heightPixels;

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
                                    screenW,
                                    screenH));
                        }
                    } else if (lastAnimInList instanceof AnimatedHandData) {
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {

                            int bitmapW = 0;
                            int bitmapH = 0;
                            try {
                                bitmapW = bitmap_hand.getWidth();
                                bitmapH = bitmap_hand.getHeight();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                            animationData.setToAllPool(index, new AnimatedHandData(
                                    screenW,
                                    screenH,
                                    bitmapW,
                                    bitmapH
                            ));

                            bitmap_handRot = ((AnimatedHandData) animationData.
                                    getLastFromCurrentPool()).rotateBitmap(bitmap_hand);
                        }
                    } else if (lastAnimInList instanceof AnimatedWritingData) {
                        if (BitmapUtils.bitmapExists(bitmap_writing)) {

                            int bitmapW = 0;
                            int bitmapH = 0;
                            try {
                                bitmapW = bitmap_writing.getWidth();
                                bitmapH = bitmap_writing.getHeight();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                            animationData.setToAllPool(index, new AnimatedWritingData(
                                    screenW,
                                    screenH,
                                    bitmapW,
                                    bitmapH,
                                    animationData
                            ));

                            bitmap_writingRot = ((AnimatedWritingData) animationData.
                                    getLastFromCurrentPool()).rotateBitmap(bitmap_writing);
                        }
                    } else if (lastAnimInList instanceof AnimatedFrostData) {
                        if (BitmapUtils.bitmapExists(bitmap_frost)) {
                            animationData.setToAllPool(index, new AnimatedFrostData(
                                    screenW,
                                    screenH
                            ));
                        }
                    } else if (lastAnimInList instanceof AnimatedMirrorData) {
                        if (BitmapUtils.bitmapExists(bitmap_mirror)) {
                            animationData.setToAllPool(index, new AnimatedMirrorData(
                                    screenW,
                                    screenH
                            ));
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
                    if (currentAnim instanceof AnimatedHandData data) {
                        animationData.setSelectedHand(
                                (int) (Math.random() * handResIds.size()));

                        BitmapUtils.destroyBitmap(bitmap_hand);
                        BitmapUtils.destroyBitmap(bitmap_handRot);

                        bitmapUtils.setResource(
                                handResIds.get(animationData.getSelectedHand()));
                        bitmap_hand = bitmapUtils.compileBitmaps(getContext());

                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = data.rotateBitmap(bitmap_hand);
                        }

                        /*
                        AnimatedHandData data = ((AnimatedHandData) currentAnim);
                        BitmapUtils.destroyBitmap(bitmap_handRot);
                        if (BitmapUtils.bitmapExists(bitmap_hand)) {
                            bitmap_handRot = data.rotateBitmap(bitmap_hand);
                        }
                        */
                    } else if (currentAnim instanceof AnimatedWritingData data) {
                        animationData.setSelectedWriting(
                                (int) (Math.random() * writingResIds.size()));

                        BitmapUtils.destroyBitmap(bitmap_writing);
                        BitmapUtils.destroyBitmap(bitmap_writingRot);

                        bitmapUtils.setResource(
                                writingResIds.get(animationData.getSelectedWriting()));
                        bitmap_writing = bitmapUtils.compileBitmaps(getContext());

                        if (BitmapUtils.bitmapExists(bitmap_writing)) {
                            bitmap_writingRot = data.rotateBitmap(bitmap_writing);
                        }
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
     * @param canvas The cavas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        if (titleScreenViewModel == null) {
            return;
        }

        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);

        try {
            for (AbstractAnimatedGraphic a : titleScreenViewModel.getAnimationData().getCurrentPool()) {
                if (a != null) {
                    paint.setColorFilter(a.getFilter());
                    try {
                        if (a instanceof AnimatedMirrorData mirror) {
                            mirror.draw(canvas, paint, bitmap_mirror);
                        } else if (a instanceof AnimatedWritingData writing) {
                            writing.draw(canvas, paint, bitmap_writingRot);
                        } else if (a instanceof AnimatedHandData hand) {
                            hand.draw(canvas, paint, bitmap_handRot);
                        } else if (a instanceof AnimatedOrbData orb) {
                            orb.draw(canvas, paint, bitmap_orb);
                        } else if (a instanceof AnimatedFrostData frost) {
                            frost.draw(canvas, paint, bitmap_frost);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ConcurrentModificationException ex) {
            ex.printStackTrace();
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
        BitmapUtils.destroyBitmap(bitmap_mirror);
        BitmapUtils.destroyBitmap(bitmap_handRot);
        BitmapUtils.destroyBitmap(bitmap_writingRot);

        bitmap_orb = null;
        bitmap_frost = null;
        bitmap_hand = null;
        bitmap_writing = null;
        bitmap_mirror = null;
        bitmap_handRot = null;
        bitmap_writingRot = null;

        System.gc();
    }


    public void startAnimInitThreads(
            MainMenuViewModel titleScreenViewModel, BitmapUtils bitmapUtils) {

        if (thread_initAnima == null) {
            thread_initAnima = new Thread() {
                public void run() {
                    init(titleScreenViewModel, bitmapUtils);
                }
            };
            thread_initAnima.start();
        }

        if (thread_initReady == null) {
            thread_initReady = new Thread(() -> {

                while (!canAnimate) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startAnimThreads();

            });

            thread_initReady.start();
        }
    }

    private void startAnimTickThread() {
        if (thread_tickAnim == null) {
            canAnimate = true;
            thread_tickAnim = new Thread() {

                public void run() {
                    while (canAnimate) {
                        update();
                        try {
                            tick();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                private void tick() throws InterruptedException {
                    long now = System.nanoTime();
                    long updateTime = System.nanoTime() - now;
                    double TARGET_FPS = 30, MAX_FPS = 60;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        try {
                            TARGET_FPS = getContext().getDisplay().getRefreshRate();
                            if (TARGET_FPS > MAX_FPS) {
                                TARGET_FPS = MAX_FPS;
                            }
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                    //TARGET_FPS = 200;
                    long OPTIMAL_TIME = (long) (1000000000 / TARGET_FPS);
                    long wait = (long) ((OPTIMAL_TIME - updateTime) / 1000000.0);

                    if (wait < 0) {
                        wait = 1;
                    }

                    Thread.sleep(wait);
                }

                private void update() {
                    try {
                        StartScreenAnimationView.this.tick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread_tickAnim.start();
        }
    }

    private void startAnimDrawThread() {
        if (thread_renderAnim != null) { return; }

        thread_renderAnim = new Thread() {

            public void run() {
                while (canAnimate) {
                    invalidate();
                    try {
                        tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void tick() throws InterruptedException {
                long now = System.nanoTime();
                long updateTime = System.nanoTime() - now;
                double TARGET_FPS = 24, MAX_FPS = 60;

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
                            (getContext().getDisplay() != null)) {
                        TARGET_FPS = getContext().getDisplay().getRefreshRate();
                        if (TARGET_FPS > MAX_FPS) {
                            TARGET_FPS = MAX_FPS;
                        }
                    }

                    long OPTIMAL_TIME = (long) (1000000000 / TARGET_FPS);
                    long wait = (long) ((OPTIMAL_TIME - updateTime) / 1000000.0);

                    if (wait < 0) {
                        wait = 1;
                    }

                    Thread.sleep(wait);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }

            private void invalidate() {
                try {
                    ((PETActivity)getContext()).runOnUiThread(StartScreenAnimationView.this::invalidate);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        };
        thread_renderAnim.start();
    }

    public void startAnimThreads() {
        startAnimTickThread();
        startAnimDrawThread();
    }

    public void stopAnimInitThreads() {
        if (thread_initAnima != null) {
            thread_initAnima.interrupt();
            thread_initAnima = null;
        }
        if (thread_initReady != null) {
            thread_initReady.interrupt();
            thread_initReady = null;
        }
    }

    public void stopAnimTickThread() {
        if (thread_tickAnim != null) {
            thread_tickAnim.interrupt();
            thread_tickAnim = null;
        }
    }

    public void stopAnimDrawThread() {
        if (thread_renderAnim != null) {
            thread_renderAnim.interrupt();
            thread_renderAnim = null;
        }
    }

    public void stopAnimThreads() {
        stopAnimDrawThread();
        stopAnimTickThread();
    }

    public void canAnimateBackground(boolean canAnimateBackground) {
        this.canAnimate = canAnimateBackground;
    }
}
