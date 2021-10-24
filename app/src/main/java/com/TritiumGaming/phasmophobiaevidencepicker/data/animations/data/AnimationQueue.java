package com.TritiumGaming.phasmophobiaevidencepicker.data.animations.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * AdnimationQueue class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class AnimationQueue {

    private final int maxSize;
    private int tick, timeout, maxTimeout;

    private final ArrayList<Integer> queue = new ArrayList<>();

    /**
     * AnimationQueue
     *
     * TODO
     *
     * @param maxSize
     * @param maxTimeout
     */
    public AnimationQueue(int maxSize, int maxTimeout){
        this.maxSize = maxSize;

        setMaxTimeout(maxTimeout);
        setTimeout(this.maxTimeout);
        setTick((int)(timeout*.75));
    }

    /**
     * setTick
     *
     * TODO
     *
     * @param tick
     */
    public void setTick(int tick){
        this.tick = tick;
    }

    /**
     * tick
     *
     * TODO
     */
    public void tick(){
        tick++;
    }

    /**
     * canDequeue
     *
     * TODO
     *
     * @return
     */
    public boolean canDequeue(){
        boolean canDequeue = (tick >= timeout);
        if(canDequeue) {
            tick = 0;
            setTimeout(maxTimeout);
        }
        return canDequeue;
    }

    /**
     * setMaxTimeout
     *
     * TODO
     *
     * @param maxTimeout
     */
    public void setMaxTimeout(int maxTimeout){
        this.maxTimeout = maxTimeout;
    }

    /**
     * setTimeout
     *
     * TODO
     *
     * @param timeout
     */
    public void setTimeout(int timeout){
        this.timeout = (int)((Math.random() * timeout) + (timeout*.5));
    }

    /**
     * refill
     *
     * TODO
     */
    public void refill(){
        for(int i = 0; i < maxSize; i++)
            enqueueRandomPos(i);
    }

    /**
     * enqueue
     *
     * TODO
     *
     * @param num
     */
    public void enqueue(int num){
        if(queue.size() < maxSize) {
            queue.add(num);
        }
    }

    /**
     * enqueue
     *
     * TODO
     *
     * @param index
     * @param num
     */
    public void enqueue(int index, int num){
        if(queue.size() < maxSize) {
            queue.add(index, num);
        }
    }

    /**
     * enqueueRandomPos
     *
     * TODO
     *
     * @param num
     */
    public void enqueueRandomPos(int num){
        enqueue((int) (Math.random() * queue.size()), num);
    }

    /**
     * dequeue
     *
     * TODO
     *
     * @return
     */
    public int dequeue(){
        if(queue.isEmpty()) {
            refill();
        }
        return queue.remove(0);
    }

    /**
     * getSize
     *
     * TODO
     *
     * @return
     */
    public int getSize(){
        return queue.size();
    }

    /**
     * toString
     *
     * TODO
     *
     * @return
     */
    @NonNull
    public String toString(){
        String s = "Queue List (" + getSize() +") [";
        for(int i = 0; i < queue.size(); i++)
            s += queue.get(i) + " ";
        return s + "]";
    }
}
