package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry;

import androidx.annotation.NonNull;

/**
 * A point representing a location in {@code (x,y)} coordinate space,
 * specified in integer precision.
 *
 * @author Sami Shaio
 * @since 1.0
 */
public class Point extends Point2D implements java.io.Serializable {
    /**
     * The X coordinate of this {@code Point}.
     * If no X coordinate is set it will default to 0.
     */
    public int x;

    /**
     * The Y coordinate of this {@code Point}.
     * If no Y coordinate is set it will default to 0.
     */
    public int y;

    /**
     * Use serialVersionUID from JDK 1.1 for interoperability.
     */
    private static final long serialVersionUID = -5276940640259749850L;

    /**
     * Constructs and initializes a point at the origin
     * (0,&nbsp;0) of the coordinate space.
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Constructs and initializes a point with the same location as
     * the specified {@code Point} object.
     */
    public Point(@NonNull Point p) {
        this(p.x, p.y);
    }

    /**
     * Constructs and initializes a point at the specified
     * {@code (x,y)} location in the coordinate space.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * {@inheritDoc}
     */
    public double getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the location of this point.
     * This method is included for completeness, to parallel the
     * {@code getLocation} method of {@code Component}.
     *
     * @return a copy of this point, at the same location
     * @since 1.1
     */
    @NonNull
    public Point getLocation() {
        return new Point(x, y);
    }

    /**
     * Sets the location of the point to the specified location.
     * This method is included for completeness, to parallel the
     * {@code setLocation} method of {@code Component}.
     *
     * @param p a point, the new location for this point
     * @since 1.1
     */
    public void setLocation(@NonNull Point p) {
        setLocation(p.x, p.y);
    }

    /**
     * Changes the point to have the specified location.
     * <p>
     * This method is included for completeness, to parallel the
     * {@code setLocation} method of {@code Component}.
     * Its behavior is identical with <code>move(int,&nbsp;int)</code>.
     *
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     * @since 1.1
     */
    public void setLocation(int x, int y) {
        move(x, y);
    }

    /**
     * Sets the location of this point to the specified double coordinates.
     * The double values will be rounded to integer values.
     * Any number smaller than {@code Integer.MIN_VALUE}
     * will be reset to {@code MIN_VALUE}, and any number
     * larger than {@code Integer.MAX_VALUE} will be
     * reset to {@code MAX_VALUE}.
     *
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     * @see #getLocation
     */
    public void setLocation(double x, double y) {
        this.x = (int) Math.floor(x + 0.5);
        this.y = (int) Math.floor(y + 0.5);
    }

    /**
     * Moves this point to the specified location in the
     * {@code (x,y)} coordinate plane. This method
     * is identical with <code>setLocation(int,&nbsp;int)</code>.
     *
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     */
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Translates this point, at location {@code (x,y)},
     * by {@code dx} along the {@code x} axis and {@code dy}
     * along the {@code y} axis so that it now represents the point
     * {@code (x+dx,y+dy)}.
     *
     * @param dx the distance to move this point
     *           along the X axis
     * @param dy the distance to move this point
     *           along the Y axis
     */
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Determines whether or not two points are equal. Two instances of
     * {@code Point2D} are equal if the values of their
     * {@code x} and {@code y} member fields, representing
     * their position in the coordinate space, are the same.
     *
     * @param obj an object to be compared with this {@code Point2D}
     * @return {@code true} if the object to be compared is
     * an instance of {@code Point2D} and has
     * the same values; {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Point pt) {
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }

    /**
     * Returns a string representation of this point and its location
     * in the {@code (x,y)} coordinate space. This method is
     * intended to be used only for debugging purposes, and the content
     * and format of the returned string may vary between implementations.
     * The returned string may be empty but may not be {@code null}.
     *
     * @return a string representation of this point
     */
    @NonNull
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
