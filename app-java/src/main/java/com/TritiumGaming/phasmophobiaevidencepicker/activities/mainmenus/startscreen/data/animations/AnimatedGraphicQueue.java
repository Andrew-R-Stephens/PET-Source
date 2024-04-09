package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * AdnimationQueue class
 *
 * @author TritiumGamingStudios
 */
public class AnimatedGraphicQueue {

    private final int maxSize;
    private int tick, timeout, maxTimeout;

    @NonNull
    private final ArrayList<Integer> queue = new ArrayList<>();

    /**
     * @param maxSize
     * @param maxTimeout
     */
    public AnimatedGraphicQueue(int maxSize, int maxTimeout) {
        this.maxSize = maxSize;

        setMaxTimeout(maxTimeout);
        setTimeout(this.maxTimeout);
        setTick((int) (timeout * .75));
    }

    /**
     * @param tick
     */
    public void setTick(int tick) {
        this.tick = tick;
    }

    /**
     *
     */
    public void tick() {
        tick++;
    }

    /**
     * @return
     */
    public boolean canDequeue() {
        boolean canDequeue = (tick >= timeout);
        if (canDequeue) {
            tick = 0;
            setTimeout(maxTimeout);
        }
        return canDequeue;
    }

    /**
     * @param maxTimeout
     */
    public void setMaxTimeout(int maxTimeout) {
        this.maxTimeout = maxTimeout;
    }

    /**
     * @param timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = (int) ((Math.random() * timeout) + (timeout * .5));
    }

    /**
     *
     */
    public void refill() {
        for (int i = 0; i < maxSize; i++) {
            enqueueRandomPos(i);
        }
    }

    /**
     * @param num
     */
    public void enqueue(int num) {

        if (queue.size() < maxSize) {
            if(num >= 0) {
                try {
                    queue.add(num);
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param index
     * @param num
     */
    public void enqueue(int index, int num) {

        if (queue.size() < maxSize) {
            queue.add(index, num);
        }
    }

    /**
     * @param num
     */
    public void enqueueRandomPos(int num) {
        enqueue((int) (Math.random() * queue.size()), num);
    }

    /**
     * @return
     */
    public int dequeue() {

        if (queue.isEmpty()) {
            refill();
        }

        return queue.remove(0);
    }

    /**
     * @return
     */
    public int getSize() {
        return queue.size();
    }

    /**
     * @return
     */
    @NonNull
    public String toString() {
        StringBuilder s = new StringBuilder("Queue List (" + getSize() + ") [");
        for (int i = 0; i < queue.size(); i++) {
            s.append(queue.get(i)).append(" ");
        }
        return s + "]";
    }
}
