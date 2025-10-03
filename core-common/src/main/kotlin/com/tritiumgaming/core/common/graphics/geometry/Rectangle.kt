package com.tritiumgaming.core.common.graphics.geometry

import java.io.Serial
import java.io.Serializable
import kotlin.math.ceil
import kotlin.math.floor

class Rectangle(
    override var x: Double = 0.0,
    override var y: Double = 0.0,
    override var width: Double = 0.0,
    override var height: Double = 0.0
) : Rectangle2D(), Shape, Serializable {

    constructor(r: Rectangle) : this(r.x, r.y, r.width, r.height)
    constructor(width: Int, height: Int) : this(0.0, 0.0, width.toDouble(), height.toDouble())
    constructor(p: Point, d: Dimension) : this(p.x.toDouble(), p.y.toDouble(), d.width, d.height)
    constructor(p: Point) : this(p.x.toDouble(), p.y.toDouble(), 0.0, 0.0)
    constructor(d: Dimension) : this(0.0, 0.0, d.width, d.height)

    var location: Point
        get() = Point(x.toInt(), y.toInt())
        set(p) {
            setLocation(p.x, p.y)
        }

    var size: Dimension
        get() = Dimension(width, height)
        set(d) {
            setSize(d.width.toInt(), d.height.toInt())
        }

    override val bounds: Rectangle
        get() = Rectangle(x, y, width, height)

    override val bounds2D: Rectangle2D
        get() = Rectangle2DDouble(x, y, width, height)

    override val isEmpty: Boolean
        get() = (width <= 0) || (height <= 0)

    fun setBounds(r: Rectangle) {
        setBounds(r.x, r.y, r.width, r.height)
    }

    fun setBounds(x: Int, y: Int, width: Int, height: Int) {
        reshape(x, y, width, height)
    }

    fun setBounds(x: Double, y: Double, width: Double, height: Double) {
        reshape(x.toInt(), y.toInt(), width.toInt(), height.toInt())
    }

    override fun setRect(x: Double, y: Double, width: Double, height: Double) {
        var width = width
        var height = height
        val newx: Int
        val newy: Int
        val neww: Int
        val newh: Int

        if (x > 2.0 * Int.MAX_VALUE) {
            // Too far in positive X direction to represent...
            // We cannot even reach the left side of the specified
            // rectangle even with both x & width set to MAX_VALUE.
            // The intersection with the "maximal integer rectangle"
            // is non-existent so we should use a width < 0.
            // REMIND: Should we try to determine a more "meaningful"
            // adjusted value for neww than just "-1"?
            newx = Int.MAX_VALUE
            neww = -1
        } else {
            newx = clip(x, false)
            if (width >= 0) width += x - newx
            neww = clip(width, width >= 0)
        }

        if (y > 2.0 * Int.MAX_VALUE) {
            // Too far in positive Y direction to represent...
            newy = Int.MAX_VALUE
            newh = -1
        } else {
            newy = clip(y, false)
            if (height >= 0) height += y - newy
            newh = clip(height, height >= 0)
        }

        reshape(newx, newy, neww, newh)
    }

    @Deprecated("")
    fun reshape(x: Int, y: Int, width: Int, height: Int) {
        this.x = x.toDouble()
        this.y = y.toDouble()
        this.width = width.toDouble()
        this.height = height.toDouble()
    }

    @Deprecated("")
    fun reshape(x: Double, y: Double, width: Double, height: Double) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    fun setLocation(x: Int, y: Int) {
        move(x, y)
    }

    fun setLocation(x: Double, y: Double) {
        move(x.toInt(), y.toInt())
    }

    @Deprecated("")
    fun move(x: Int, y: Int) {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    fun translate(dx: Int, dy: Int) {
        var oldv = this.x
        var newv = oldv + dx
        if (dx < 0) {
            // moving leftward
            if (newv > oldv) {
                // negative overflow
                // Only adjust width if it was valid (>= 0).
                if (width >= 0) {
                    // The right edge is now conceptually at
                    // newv+width, but we may move newv to prevent
                    // overflow.  But we want the right edge to
                    // remain at its new location in spite of the
                    // clipping.  Think of the following adjustment
                    // conceptually the same as:
                    // width += newv; newv = MIN_VALUE; width -= newv;
                    width += newv - Int.MIN_VALUE
                    // width may go negative if the right edge went past
                    // MIN_VALUE, but it cannot overflow since it cannot
                    // have moved more than MIN_VALUE and any non-negative
                    // number + MIN_VALUE does not overflow.
                }
                newv = Int.MIN_VALUE.toDouble()
            }
        } else {
            // moving rightward (or staying still)
            if (newv < oldv) {
                // positive overflow
                if (width >= 0) {
                    // Conceptually the same as:
                    // width += newv; newv = MAX_VALUE; width -= newv;
                    width += newv - Int.MAX_VALUE
                    // With large widths and large displacements
                    // we may overflow so we need to check it.
                    if (width < 0) width = Int.MAX_VALUE.toDouble()
                }
                newv = Int.MAX_VALUE.toDouble()
            }
        }
        this.x = newv

        oldv = this.y
        newv = oldv + dy
        if (dy < 0) {
            // moving upward
            if (newv > oldv) {
                // negative overflow
                if (height >= 0) {
                    height += newv - Int.MIN_VALUE
                    // See above comment about no overflow in this case
                }
                newv = Int.MIN_VALUE.toDouble()
            }
        } else {
            // moving downward (or staying still)
            if (newv < oldv) {
                // positive overflow
                if (height >= 0) {
                    height += newv - Int.MAX_VALUE
                    if (height < 0) height = Int.MAX_VALUE.toDouble()
                }
                newv = Int.MAX_VALUE.toDouble()
            }
        }
        this.y = newv
    }

    fun setSize(width: Int, height: Int) {
        resize(width, height)
    }

    @Deprecated("")
    fun resize(width: Int, height: Int) {
        this.width = width.toDouble()
        this.height = height.toDouble()
    }

    fun contains(p: Point): Boolean {
        return contains(p.x, p.y)
    }

    fun contains(x: Int, y: Int): Boolean {
        return inside(x, y)
    }

    fun contains(r: Rectangle): Boolean {
        return contains(r.x, r.y, r.width, r.height)
    }

    fun contains(X: Int, Y: Int, W: Int, H: Int): Boolean {
        var W = W
        var H = H
        var w = this.width
        var h = this.height
        if ((w.toInt() or h.toInt() or W or H) < 0) {
            // At least one of the dimensions is negative...
            return false
        }
        // Note: if any dimension is zero, tests below must return false...
        val x = this.x
        val y = this.y
        if (X < x || Y < y) {
            return false
        }
        w += x
        W += X
        if (W <= X) {
            // X+W overflowed or W was zero, return false if...
            // either original w or W was zero or
            // x+w did not overflow or
            // the overflowed x+w is smaller than the overflowed X+W
            if (w >= x || W > w) return false
        } else {
            // X+W did not overflow and W was not zero, return false if...
            // original w was zero or
            // x+w did not overflow and x+w is smaller than X+W
            if (w >= x && W > w) return false
        }
        h += y
        H += Y
        return if (H <= Y) {
            h < y && H <= h
        } else {
            h < y || H <= h
        }
    }

    @Deprecated("")
    fun inside(X: Int, Y: Int): Boolean {
        var w = this.width
        var h = this.height
        if ((w.toInt() or h.toInt()) < 0) {
            // At least one of the dimensions is negative...
            return false
        }
        // Note: if either dimension is zero, tests below must return false...
        val x = this.x
        val y = this.y
        if (X < x || Y < y) {
            return false
        }
        w += x
        h += y
        //    overflow || intersect
        return ((w < x || w > X) &&
                (h < y || h > Y))
    }

    fun intersects(r: Rectangle): Boolean {
        var tw = this.width
        var th = this.height
        var rw = r.width
        var rh = r.height
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false
        }
        val tx = this.x
        val ty = this.y
        val rx = r.x
        val ry = r.y
        rw += rx
        rh += ry
        tw += tx
        th += ty
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry))
    }

    fun intersection(r: Rectangle): Rectangle {
        var tx1 = this.x
        var ty1 = this.y
        val rx1 = r.x
        val ry1 = r.y
        var tx2 = tx1.toLong()
        tx2 += width.toLong()
        var ty2 = ty1.toLong()
        ty2 += height.toLong()
        var rx2 = rx1.toLong()
        rx2 += r.width.toLong()
        var ry2 = ry1.toLong()
        ry2 += r.height.toLong()
        if (tx1 < rx1) tx1 = rx1
        if (ty1 < ry1) ty1 = ry1
        if (tx2 > rx2) tx2 = rx2
        if (ty2 > ry2) ty2 = ry2
        tx2 -= tx1.toLong()
        ty2 -= ty1.toLong()
        // tx2,ty2 will never overflow (they will never be
        // larger than the smallest of the two source w,h)
        // they might underflow, though...
        if (tx2 < Int.MIN_VALUE) tx2 = Int.MIN_VALUE.toLong()
        if (ty2 < Int.MIN_VALUE) ty2 = Int.MIN_VALUE.toLong()
        return Rectangle(tx1, ty1, tx2.toDouble(), ty2.toDouble())
    }

    fun union(r: Rectangle): Rectangle {
        var tx2 = width.toLong()
        var ty2 = height.toLong()
        if ((tx2 or ty2) < 0) {
            // This rectangle has negative dimensions...
            // If rect has non-negative dimensions then it is the answer.
            // If rect is non-existent (has a negative dimension), then both
            // are non-existent and we can return any non-existent rectangle
            // as an answer.  Thus, returning rect meets that criterion.
            // Either way, rect is our answer.
            return Rectangle(r)
        }
        var rx2 = r.width.toLong()
        var ry2 = r.height.toLong()
        if ((rx2 or ry2) < 0) {
            return Rectangle(
                this
            )
        }
        var tx1 = this.x
        var ty1 = this.y
        tx2 += tx1.toLong()
        ty2 += ty1.toLong()
        val rx1 = r.x
        val ry1 = r.y
        rx2 += rx1.toLong()
        ry2 += ry1.toLong()
        if (tx1 > rx1) tx1 = rx1
        if (ty1 > ry1) ty1 = ry1
        if (tx2 < rx2) tx2 = rx2
        if (ty2 < ry2) ty2 = ry2
        tx2 -= tx1.toLong()
        ty2 -= ty1.toLong()
        // tx2,ty2 will never underflow since both original rectangles
        // were already proven to be non-empty
        // they might overflow, though...
        if (tx2 > Int.MAX_VALUE) tx2 = Int.MAX_VALUE.toLong()
        if (ty2 > Int.MAX_VALUE) ty2 = Int.MAX_VALUE.toLong()
        return Rectangle(tx1, ty1, tx2.toDouble(), ty2.toDouble())
    }

    fun add(newx: Int, newy: Int) {
        if ((width.toInt() or height.toInt()) < 0) {
            this.x = newx.toDouble()
            this.y = newy.toDouble()
            this.height = 0.0
            this.width = this.height
            return
        }
        var x1 = this.x
        var y1 = this.y
        var x2 = width.toLong()
        var y2 = height.toLong()
        x2 += x1.toLong()
        y2 += y1.toLong()
        if (x1 > newx) x1 = newx.toDouble()
        if (y1 > newy) y1 = newy.toDouble()
        if (x2 < newx) x2 = newx.toLong()
        if (y2 < newy) y2 = newy.toLong()
        x2 -= x1.toLong()
        y2 -= y1.toLong()
        if (x2 > Int.MAX_VALUE) x2 = Int.MAX_VALUE.toLong()
        if (y2 > Int.MAX_VALUE) y2 = Int.MAX_VALUE.toLong()
        reshape(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
    }

    fun add(pt: Point) {
        add(pt.x, pt.y)
    }

    fun add(r: Rectangle) {
        var tx2 = width.toLong()
        var ty2 = height.toLong()
        if ((tx2 or ty2) < 0) {
            reshape(r.x, r.y, r.width, r.height)
        }
        var rx2 = r.width.toLong()
        var ry2 = r.height.toLong()
        if ((rx2 or ry2) < 0) {
            return
        }
        var tx1 = this.x
        var ty1 = this.y
        tx2 += tx1.toLong()
        ty2 += ty1.toLong()
        val rx1 = r.x
        val ry1 = r.y
        rx2 += rx1.toLong()
        ry2 += ry1.toLong()
        if (tx1 > rx1) tx1 = rx1
        if (ty1 > ry1) ty1 = ry1
        if (tx2 < rx2) tx2 = rx2
        if (ty2 < ry2) ty2 = ry2
        tx2 -= tx1.toLong()
        ty2 -= ty1.toLong()
        // tx2,ty2 will never underflow since both original
        // rectangles were non-empty
        // they might overflow, though...
        if (tx2 > Int.MAX_VALUE) tx2 = Int.MAX_VALUE.toLong()
        if (ty2 > Int.MAX_VALUE) ty2 = Int.MAX_VALUE.toLong()
        reshape(tx1.toInt(), ty1.toInt(), tx2.toInt(), ty2.toInt())
    }

    fun grow(h: Int, v: Int) {
        var x0 = x.toLong()
        var y0 = y.toLong()
        var x1 = width.toLong()
        var y1 = height.toLong()
        x1 += x0
        y1 += y0

        x0 -= h.toLong()
        y0 -= v.toLong()
        x1 += h.toLong()
        y1 += v.toLong()

        if (x1 < x0) {
            // Non-existent in X direction
            // Final width must remain negative so subtract x0 before
            // it is clipped so that we avoid the risk that the clipping
            // of x0 will reverse the ordering of x0 and x1.
            x1 -= x0
            if (x1 < Int.MIN_VALUE) x1 = Int.MIN_VALUE.toLong()
            if (x0 < Int.MIN_VALUE) x0 = Int.MIN_VALUE.toLong()
            else if (x0 > Int.MAX_VALUE) x0 = Int.MAX_VALUE.toLong()
        } else { // (x1 >= x0)
            // Clip x0 before we subtract it from x1 in case the clipping
            // affects the representable area of the rectangle.
            if (x0 < Int.MIN_VALUE) x0 = Int.MIN_VALUE.toLong()
            else if (x0 > Int.MAX_VALUE) x0 = Int.MAX_VALUE.toLong()
            x1 -= x0
            // The only way x1 can be negative now is if we clipped
            // x0 against MIN and x1 is less than MIN - in which case
            // we want to leave the width negative since the result
            // did not intersect the representable area.
            if (x1 < Int.MIN_VALUE) x1 = Int.MIN_VALUE.toLong()
            else if (x1 > Int.MAX_VALUE) x1 = Int.MAX_VALUE.toLong()
        }

        if (y1 < y0) {
            // Non-existent in Y direction
            y1 -= y0
            if (y1 < Int.MIN_VALUE) y1 = Int.MIN_VALUE.toLong()
            if (y0 < Int.MIN_VALUE) y0 = Int.MIN_VALUE.toLong()
            else if (y0 > Int.MAX_VALUE) y0 = Int.MAX_VALUE.toLong()
        } else { // (y1 >= y0)
            if (y0 < Int.MIN_VALUE) y0 = Int.MIN_VALUE.toLong()
            else if (y0 > Int.MAX_VALUE) y0 = Int.MAX_VALUE.toLong()
            y1 -= y0
            if (y1 < Int.MIN_VALUE) y1 = Int.MIN_VALUE.toLong()
            else if (y1 > Int.MAX_VALUE) y1 = Int.MAX_VALUE.toLong()
        }

        reshape(x0.toInt(), y0.toInt(), x1.toInt(), y1.toInt())
    }

    override fun outcode(x: Double, y: Double): Int {
        /*
         * Note on casts to double below.  If the arithmetic of
         * x+w or y+h is done in int, then we may get integer
         * overflow. By converting to double before the addition
         * we force the addition to be carried out in double to
         * avoid overflow in the comparison.
         *
         * See bug 4320890 for problems that this can cause.
         */
        var out = 0
        if (this.width <= 0) {
            out = out or (OUT_LEFT or OUT_RIGHT)
        } else if (x < this.x) {
            out = out or OUT_LEFT
        } else if (x > this.x + width) {
            out = out or OUT_RIGHT
        }
        if (this.height <= 0) {
            out = out or (OUT_TOP or OUT_BOTTOM)
        } else if (y < this.y) {
            out = out or OUT_TOP
        } else if (y > this.y + height) {
            out = out or OUT_BOTTOM
        }
        return out
    }

    override fun createIntersection(r: Rectangle2D): Rectangle2D {
        if (r is Rectangle) {
            return intersection(r)
        }
        val dest: Rectangle2D = Rectangle2DDouble()
        intersect(
            this, r, dest
        )
        return dest
    }

    override fun createUnion(r: Rectangle2D): Rectangle2D {
        if (r is Rectangle) {
            return union(r)
        }
        val dest: Rectangle2D = Rectangle2DDouble()
        union(
            this, r, dest
        )
        return dest
    }

    override fun equals(obj: Any?): Boolean {
        if (obj is Rectangle) {
            return ((x == obj.x) &&
                    (y == obj.y) &&
                    (width == obj.width) &&
                    (height == obj.height))
        }
        return super.equals(obj)
    }

    override fun toString(): String {
        return javaClass.name + "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]"
    }

    companion object {
        @Serial
        private val serialVersionUID = -4345857070255674764L

        // Return best integer representation for v, clipped to integer
        // range and floor-ed or ceiling-ed, depending on the boolean.
        private fun clip(v: Double, doceil: Boolean): Int {
            if (v <= Int.MIN_VALUE) {
                return Int.MIN_VALUE
            }
            if (v >= Int.MAX_VALUE) {
                return Int.MAX_VALUE
            }
            return (if (doceil) ceil(v) else floor(v)).toInt()
        }
    }
}
