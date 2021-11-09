package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.graphicsdata;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.AbstractAnimatedGraphic;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.data.animations.AnimatedGraphicQueue;

import java.util.ArrayList;

/**
 * AnimationData class
 *
 * @author TritiumGamingStudios
 */
public class AnimatedGraphicData {

    private AnimatedGraphicQueue queue = new AnimatedGraphicQueue(10, 500);
    private final ArrayList<AbstractAnimatedGraphic> allPool = new ArrayList<>(), currentPool = new ArrayList<>();

    private int selectedWriting = -1;
    private float rotWriting, rotHand;

    /**
     *
     * @param animatedItem
     */
    public void addToAllPool(AbstractAnimatedGraphic animatedItem){
        allPool.add(animatedItem);
    }

    /**
     *
     * @param pos
     * @param animated
     */
    public void setToAllPool(int pos, AbstractAnimatedGraphic animated){
        allPool.set(pos, animated);
    }

    /**
     *
     * @return
     */
    public ArrayList<AbstractAnimatedGraphic> getAllPool(){
        return allPool;
    }

    /**
     *
     * @param i
     * @return
     */
    public AbstractAnimatedGraphic getFromAllPool(int i) { return allPool.get(i); }

    /**
     *
     * @return
     */
    public AbstractAnimatedGraphic getLastFromAllPool() {
        return allPool.get(allPool.size()-1);
    }

    /**
     *
     * @return
     */
    public int getAllPoolSize(){ return allPool.size(); }

    /**
     *
     * @param animatedItem
     */
    public void addToCurrentPool(AbstractAnimatedGraphic animatedItem){
        currentPool.add(animatedItem);
    }

    /**
     *
     * @param i
     * @return
     */
    public AbstractAnimatedGraphic getFromCurrentPool(int i) { return currentPool.get(i); }

    /**
     *
     * @return
     */
    public AbstractAnimatedGraphic getLastFromCurrentPool() throws IndexOutOfBoundsException{
        if(currentPool.size() <= 0)
            return null;
        int index = currentPool.size()-1;

        return currentPool.get(index);
    }

    /**
     *
     * @param animated
     */
    public void removeFromCurrentPool(AbstractAnimatedGraphic animated) { currentPool.remove(animated); }

    /**
     *
     * @return
     */
    public ArrayList<AbstractAnimatedGraphic> getCurrentPool(){
        return currentPool;
    }

    /**
     *
     * @return
     */
    public int getCurrentPoolSize(){ return currentPool.size(); }

    /**
     *
     * @param animationQueue
     */
    public void setQueue(AnimatedGraphicQueue animationQueue) {
        this.queue = animationQueue;
    }

    /**
     *
     * @return
     */
    public boolean hasQueue(){
        return queue != null;
    }

    /**
     *
     * @return
     */
    public AnimatedGraphicQueue getQueue() {
        return queue;
    }

    /**
     *
     */
    public void tick(){ queue.tick(); }

    /**
     *
     * @param selectedWriting
     */
    public void setSelectedWriting(int selectedWriting){
        this.selectedWriting = selectedWriting;
    }

    /**
     *
     * @return
     */
    public int getSelectedWriting(){
        return selectedWriting;
    }

    /**
     *
     * @param rot
     */
    public void setRotWriting(float rot){
        this.rotWriting = rot;
    }

    /**
     *
     * @return
     */
    public float getRotWriting(){
        return rotWriting;
    }

    /**
     *
     * @param rot
     */
    public void setRotHand(float rot){
        this.rotHand = rot;
    }

    /**
     *
     * @return
     */
    public float getRotHand(){
        return rotHand;
    }

    /**
     *
     * @return
     */
    public boolean hasData() {
        return !allPool.isEmpty() && hasQueue();
    }
}
