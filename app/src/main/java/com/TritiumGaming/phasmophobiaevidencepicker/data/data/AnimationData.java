package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.Animated;
import com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data.AnimationQueue;

import java.util.ArrayList;

/**
 * AnimationData class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class AnimationData {

    private AnimationQueue queue = new AnimationQueue(10, 500);
    private final ArrayList<Animated> allPool = new ArrayList<>(), currentPool = new ArrayList<>();

    private int selectedWriting = -1;
    private float rotWriting, rotHand;

    /**
     * addToAllPool
     *
     * TODO
     *
     * @param animatedItem
     */
    public void addToAllPool(Animated animatedItem){
        allPool.add(animatedItem);
    }

    /**
     * setToAllPool
     *
     * TODO
     *
     * @param pos
     * @param animated
     */
    public void setToAllPool(int pos, Animated animated){
        allPool.set(pos, animated);
    }

    /**
     * getAllPool
     *
     * TODO
     *
     * @return
     */
    public ArrayList<Animated> getAllPool(){
        return allPool;
    }

    /**
     * getFromAllPool
     *
     * TODO
     *
     * @param i
     * @return
     */
    public Animated getFromAllPool(int i) { return allPool.get(i); }

    /**
     * getLastFromAllPool
     *
     * TODO
     *
     * @return
     */
    public Animated getLastFromAllPool() {
        return allPool.get(allPool.size()-1);
    }

    /**
     * getAllPoolSize
     *
     * TODO
     *
     * @return
     */
    public int getAllPoolSize(){ return allPool.size(); }

    /**
     * addToCurrentPool
     *
     * TODO
     *
     * @param animatedItem
     */
    public void addToCurrentPool(Animated animatedItem){
        currentPool.add(animatedItem);
    }

    /**
     * getFromCurrentPool
     *
     * TODO
     *
     * @param i
     * @return
     */
    public Animated getFromCurrentPool(int i) { return currentPool.get(i); }

    /**
     * getLastFromCurrentPool
     *
     * TODO
     *
     * @return
     */
    public Animated getLastFromCurrentPool() { return currentPool.get(currentPool.size()-1); }

    /**
     * removeFromCurrentPool
     *
     * TODO
     *
     * @param animated
     */
    public void removeFromCurrentPool(Animated animated) { currentPool.remove(animated); }

    /**
     * getCurrentPool
     *
     * TODO
     *
     * @return
     */
    public ArrayList<Animated> getCurrentPool(){
        return currentPool;
    }

    /**
     * getCurrentPoolSize
     *
     * TODO
     *
     * @return
     */
    public int getCurrentPoolSize(){ return currentPool.size(); }

    /**
     * setQueue
     *
     * TODO
     *
     * @param animationQueue
     */
    public void setQueue(AnimationQueue animationQueue) {
        this.queue = animationQueue;
    }

    /**
     * hasQueue
     *
     * TODO
     *
     * @return
     */
    public boolean hasQueue(){
        return queue != null;
    }

    /**
     * getQueue
     *
     * TODO
     *
     * @return
     */
    public AnimationQueue getQueue() {
        return queue;
    }

    /**
     * tick
     *
     * TODO
     */
    public void tick(){ queue.tick(); }

    /**
     * setSelectedWriting
     *
     * TODO
     *
     * @param selectedWriting
     */
    public void setSelectedWriting(int selectedWriting){
        this.selectedWriting = selectedWriting;
    }

    /**
     * getSelectedWriting
     *
     * TODO
     *
     * @return
     */
    public int getSelectedWriting(){
        return selectedWriting;
    }

    /**
     * setRotWriting
     *
     * TODO
     *
     * @param rot
     */
    public void setRotWriting(float rot){
        this.rotWriting = rot;
    }

    /**
     * getRotWriting
     *
     * TODO
     *
     * @return
     */
    public float getRotWriting(){
        return rotWriting;
    }

    /**
     * setRotHand
     *
     * TODO
     *
     * @param rot
     */
    public void setRotHand(float rot){
        this.rotHand = rot;
    }

    /**
     * getRotHand
     *
     * TODO
     *
     * @return
     */
    public float getRotHand(){
        return rotHand;
    }

    /**
     * hasData
     *
     * TODO
     *
     * @return
     */
    public boolean hasData() {
        return !allPool.isEmpty() && hasQueue();
    }
}
