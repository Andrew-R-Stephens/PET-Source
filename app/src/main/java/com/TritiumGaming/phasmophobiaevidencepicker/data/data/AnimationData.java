package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.Animated;
import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.AnimationQueue;

import java.util.ArrayList;

/**
 * AnimationData class
 *
 * @author TritiumGamingStudios
 */
public class AnimationData {

    private AnimationQueue queue = new AnimationQueue(10, 500);
    private final ArrayList<Animated> allPool = new ArrayList<>(), currentPool = new ArrayList<>();

    private int selectedWriting = -1;
    private float rotWriting, rotHand;

    /**
     *
     * @param animatedItem
     */
    public void addToAllPool(Animated animatedItem){
        allPool.add(animatedItem);
    }

    /**
     *
     * @param pos
     * @param animated
     */
    public void setToAllPool(int pos, Animated animated){
        allPool.set(pos, animated);
    }

    /**
     *
     * @return
     */
    public ArrayList<Animated> getAllPool(){
        return allPool;
    }

    /**
     *
     * @param i
     * @return
     */
    public Animated getFromAllPool(int i) { return allPool.get(i); }

    /**
     *
     * @return
     */
    public Animated getLastFromAllPool() {
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
    public void addToCurrentPool(Animated animatedItem){
        currentPool.add(animatedItem);
    }

    /**
     *
     * @param i
     * @return
     */
    public Animated getFromCurrentPool(int i) { return currentPool.get(i); }

    /**
     *
     * @return
     */
    public Animated getLastFromCurrentPool() {
        int index = currentPool.size()-1;
        if(index < 0)
            index = 0;
        return currentPool.get(index); }

    /**
     *
     * @param animated
     */
    public void removeFromCurrentPool(Animated animated) { currentPool.remove(animated); }

    /**
     *
     * @return
     */
    public ArrayList<Animated> getCurrentPool(){
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
    public void setQueue(AnimationQueue animationQueue) {
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
    public AnimationQueue getQueue() {
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
