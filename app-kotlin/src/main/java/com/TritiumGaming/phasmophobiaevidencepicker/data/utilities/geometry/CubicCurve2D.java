
package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.ulp;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Arrays;


public abstract class CubicCurve2D implements Shape, Cloneable {


    public static class Float extends CubicCurve2D implements Serializable {

        public float x1;


        public float y1;


        public float ctrlx1;


        public float ctrly1;


        public float ctrlx2;


        public float ctrly2;


        public float x2;


        public float y2;


        public Float() {
        }


        public Float(float x1, float y1,
                     float ctrlx1, float ctrly1,
                     float ctrlx2, float ctrly2,
                     float x2, float y2)
        {
            setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
        }


        public double getX1() {
            return (double) x1;
        }


        public double getY1() {
            return (double) y1;
        }


        @NonNull
        public Point2D getP1() {
            return new Point2D.Float(x1, y1);
        }


        public double getCtrlX1() {
            return (double) ctrlx1;
        }


        public double getCtrlY1() {
            return (double) ctrly1;
        }


        @NonNull
        public Point2D getCtrlP1() {
            return new Point2D.Float(ctrlx1, ctrly1);
        }


        public double getCtrlX2() {
            return (double) ctrlx2;
        }


        public double getCtrlY2() {
            return (double) ctrly2;
        }


        @NonNull
        public Point2D getCtrlP2() {
            return new Point2D.Float(ctrlx2, ctrly2);
        }


        public double getX2() {
            return (double) x2;
        }


        public double getY2() {
            return (double) y2;
        }


        @NonNull
        public Point2D getP2() {
            return new Point2D.Float(x2, y2);
        }


        public void setCurve(double x1, double y1,
                             double ctrlx1, double ctrly1,
                             double ctrlx2, double ctrly2,
                             double x2, double y2)
        {
            this.x1     = (float) x1;
            this.y1     = (float) y1;
            this.ctrlx1 = (float) ctrlx1;
            this.ctrly1 = (float) ctrly1;
            this.ctrlx2 = (float) ctrlx2;
            this.ctrly2 = (float) ctrly2;
            this.x2     = (float) x2;
            this.y2     = (float) y2;
        }


        public void setCurve(float x1, float y1,
                             float ctrlx1, float ctrly1,
                             float ctrlx2, float ctrly2,
                             float x2, float y2)
        {
            this.x1     = x1;
            this.y1     = y1;
            this.ctrlx1 = ctrlx1;
            this.ctrly1 = ctrly1;
            this.ctrlx2 = ctrlx2;
            this.ctrly2 = ctrly2;
            this.x2     = x2;
            this.y2     = y2;
        }


        @NonNull
        public Rectangle2D getBounds2D() {
            float left   = Math.min(Math.min(x1, x2),
                                    Math.min(ctrlx1, ctrlx2));
            float top    = Math.min(Math.min(y1, y2),
                                    Math.min(ctrly1, ctrly2));
            float right  = Math.max(Math.max(x1, x2),
                                    Math.max(ctrlx1, ctrlx2));
            float bottom = Math.max(Math.max(y1, y2),
                                    Math.max(ctrly1, ctrly2));
            return new Rectangle2D.Float(left, top,
                                         right - left, bottom - top);
        }


        private static final long serialVersionUID = -1272015596714244385L;
    }


    public static class Double extends CubicCurve2D implements Serializable {

        public double x1;


        public double y1;


        public double ctrlx1;


        public double ctrly1;


        public double ctrlx2;


        public double ctrly2;


        public double x2;


        public double y2;


        public Double() {
        }


        public Double(double x1, double y1,
                      double ctrlx1, double ctrly1,
                      double ctrlx2, double ctrly2,
                      double x2, double y2)
        {
            setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
        }


        public double getX1() {
            return x1;
        }


        public double getY1() {
            return y1;
        }


        @NonNull
        public Point2D getP1() {
            return new Point2D.Double(x1, y1);
        }


        public double getCtrlX1() {
            return ctrlx1;
        }


        public double getCtrlY1() {
            return ctrly1;
        }


        @NonNull
        public Point2D getCtrlP1() {
            return new Point2D.Double(ctrlx1, ctrly1);
        }


        public double getCtrlX2() {
            return ctrlx2;
        }


        public double getCtrlY2() {
            return ctrly2;
        }


        @NonNull
        public Point2D getCtrlP2() {
            return new Point2D.Double(ctrlx2, ctrly2);
        }


        public double getX2() {
            return x2;
        }


        public double getY2() {
            return y2;
        }


        @NonNull
        public Point2D getP2() {
            return new Point2D.Double(x2, y2);
        }


        public void setCurve(double x1, double y1,
                             double ctrlx1, double ctrly1,
                             double ctrlx2, double ctrly2,
                             double x2, double y2)
        {
            this.x1     = x1;
            this.y1     = y1;
            this.ctrlx1 = ctrlx1;
            this.ctrly1 = ctrly1;
            this.ctrlx2 = ctrlx2;
            this.ctrly2 = ctrly2;
            this.x2     = x2;
            this.y2     = y2;
        }


        @NonNull
        public Rectangle2D getBounds2D() {
            double left   = Math.min(Math.min(x1, x2),
                                     Math.min(ctrlx1, ctrlx2));
            double top    = Math.min(Math.min(y1, y2),
                                     Math.min(ctrly1, ctrly2));
            double right  = Math.max(Math.max(x1, x2),
                                     Math.max(ctrlx1, ctrlx2));
            double bottom = Math.max(Math.max(y1, y2),
                                     Math.max(ctrly1, ctrly2));
            return new Rectangle2D.Double(left, top,
                                          right - left, bottom - top);
        }


        private static final long serialVersionUID = -4202960122839707295L;
    }


    protected CubicCurve2D() {
    }


    public abstract double getX1();


    public abstract double getY1();


    public abstract Point2D getP1();


    public abstract double getCtrlX1();


    public abstract double getCtrlY1();


    public abstract Point2D getCtrlP1();


    public abstract double getCtrlX2();


    public abstract double getCtrlY2();


    public abstract Point2D getCtrlP2();


    public abstract double getX2();


    public abstract double getY2();


    public abstract Point2D getP2();


    public abstract void setCurve(double x1, double y1,
                                  double ctrlx1, double ctrly1,
                                  double ctrlx2, double ctrly2,
                                  double x2, double y2);


    public void setCurve(@NonNull double[] coords, int offset) {
        setCurve(coords[offset], coords[offset + 1],
                 coords[offset + 2], coords[offset + 3],
                 coords[offset + 4], coords[offset + 5],
                 coords[offset + 6], coords[offset + 7]);
    }


    public void setCurve(@NonNull Point2D p1, @NonNull Point2D cp1, @NonNull Point2D cp2, @NonNull Point2D p2) {
        setCurve(p1.getX(), p1.getY(), cp1.getX(), cp1.getY(),
                 cp2.getX(), cp2.getY(), p2.getX(), p2.getY());
    }


    public void setCurve(@NonNull Point2D[] pts, int offset) {
        setCurve(pts[offset].getX(), pts[offset].getY(),
                 pts[offset + 1].getX(), pts[offset + 1].getY(),
                 pts[offset + 2].getX(), pts[offset + 2].getY(),
                 pts[offset + 3].getX(), pts[offset + 3].getY());
    }


    public void setCurve(@NonNull CubicCurve2D c) {
        setCurve(c.getX1(), c.getY1(), c.getCtrlX1(), c.getCtrlY1(),
                 c.getCtrlX2(), c.getCtrlY2(), c.getX2(), c.getY2());
    }


    public static double getFlatnessSq(double x1, double y1,
                                       double ctrlx1, double ctrly1,
                                       double ctrlx2, double ctrly2,
                                       double x2, double y2) {
        return Math.max(Line2D.ptSegDistSq(x1, y1, x2, y2, ctrlx1, ctrly1),
                        Line2D.ptSegDistSq(x1, y1, x2, y2, ctrlx2, ctrly2));

    }


    public static double getFlatness(double x1, double y1,
                                     double ctrlx1, double ctrly1,
                                     double ctrlx2, double ctrly2,
                                     double x2, double y2) {
        return Math.sqrt(getFlatnessSq(x1, y1, ctrlx1, ctrly1,
                                       ctrlx2, ctrly2, x2, y2));
    }


    public static double getFlatnessSq(@NonNull double[] coords, int offset) {
        return getFlatnessSq(coords[offset], coords[offset + 1],
                             coords[offset + 2], coords[offset + 3],
                             coords[offset + 4], coords[offset + 5],
                             coords[offset + 6], coords[offset + 7]);
    }


    public static double getFlatness(@NonNull double[] coords, int offset) {
        return getFlatness(coords[offset], coords[offset + 1],
                           coords[offset + 2], coords[offset + 3],
                           coords[offset + 4], coords[offset + 5],
                           coords[offset + 6], coords[offset + 7]);
    }


    public double getFlatnessSq() {
        return getFlatnessSq(getX1(), getY1(), getCtrlX1(), getCtrlY1(),
                             getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }


    public double getFlatness() {
        return getFlatness(getX1(), getY1(), getCtrlX1(), getCtrlY1(),
                           getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }


    public void subdivide(CubicCurve2D left, CubicCurve2D right) {
        subdivide(this, left, right);
    }


    public static void subdivide(@NonNull CubicCurve2D src,
                                 @Nullable CubicCurve2D left,
                                 @Nullable CubicCurve2D right) {
        double x1 = src.getX1();
        double y1 = src.getY1();
        double ctrlx1 = src.getCtrlX1();
        double ctrly1 = src.getCtrlY1();
        double ctrlx2 = src.getCtrlX2();
        double ctrly2 = src.getCtrlY2();
        double x2 = src.getX2();
        double y2 = src.getY2();
        double centerx = (ctrlx1 + ctrlx2) / 2.0;
        double centery = (ctrly1 + ctrly2) / 2.0;
        ctrlx1 = (x1 + ctrlx1) / 2.0;
        ctrly1 = (y1 + ctrly1) / 2.0;
        ctrlx2 = (x2 + ctrlx2) / 2.0;
        ctrly2 = (y2 + ctrly2) / 2.0;
        double ctrlx12 = (ctrlx1 + centerx) / 2.0;
        double ctrly12 = (ctrly1 + centery) / 2.0;
        double ctrlx21 = (ctrlx2 + centerx) / 2.0;
        double ctrly21 = (ctrly2 + centery) / 2.0;
        centerx = (ctrlx12 + ctrlx21) / 2.0;
        centery = (ctrly12 + ctrly21) / 2.0;
        if (left != null) {
            left.setCurve(x1, y1, ctrlx1, ctrly1,
                          ctrlx12, ctrly12, centerx, centery);
        }
        if (right != null) {
            right.setCurve(centerx, centery, ctrlx21, ctrly21,
                           ctrlx2, ctrly2, x2, y2);
        }
    }


    public static void subdivide(@NonNull double[] src, int srcoff,
                                 @Nullable double[] left, int leftoff,
                                 @Nullable double[] right, int rightoff) {
        double x1 = src[srcoff];
        double y1 = src[srcoff + 1];
        double ctrlx1 = src[srcoff + 2];
        double ctrly1 = src[srcoff + 3];
        double ctrlx2 = src[srcoff + 4];
        double ctrly2 = src[srcoff + 5];
        double x2 = src[srcoff + 6];
        double y2 = src[srcoff + 7];
        if (left != null) {
            left[leftoff] = x1;
            left[leftoff + 1] = y1;
        }
        if (right != null) {
            right[rightoff + 6] = x2;
            right[rightoff + 7] = y2;
        }
        x1 = (x1 + ctrlx1) / 2.0;
        y1 = (y1 + ctrly1) / 2.0;
        x2 = (x2 + ctrlx2) / 2.0;
        y2 = (y2 + ctrly2) / 2.0;
        double centerx = (ctrlx1 + ctrlx2) / 2.0;
        double centery = (ctrly1 + ctrly2) / 2.0;
        ctrlx1 = (x1 + centerx) / 2.0;
        ctrly1 = (y1 + centery) / 2.0;
        ctrlx2 = (x2 + centerx) / 2.0;
        ctrly2 = (y2 + centery) / 2.0;
        centerx = (ctrlx1 + ctrlx2) / 2.0;
        centery = (ctrly1 + ctrly2) / 2.0;
        if (left != null) {
            left[leftoff + 2] = x1;
            left[leftoff + 3] = y1;
            left[leftoff + 4] = ctrlx1;
            left[leftoff + 5] = ctrly1;
            left[leftoff + 6] = centerx;
            left[leftoff + 7] = centery;
        }
        if (right != null) {
            right[rightoff] = centerx;
            right[rightoff + 1] = centery;
            right[rightoff + 2] = ctrlx2;
            right[rightoff + 3] = ctrly2;
            right[rightoff + 4] = x2;
            right[rightoff + 5] = y2;
        }
    }


    public static int solveCubic(@NonNull double[] eqn) {
        return solveCubic(eqn, eqn);
    }


    public static int solveCubic(@NonNull double[] eqn, @NonNull double[] res) {
        // From Graphics Gems:
        // http://tog.acm.org/resources/GraphicsGems/gems/Roots3And4.c
        final double d = eqn[3];
        if (d == 0) {
            return QuadCurve2D.solveQuadratic(eqn, res);
        }

        /* normal form: x^3 + Ax^2 + Bx + C = 0 */
        final double A = eqn[2] / d;
        final double B = eqn[1] / d;
        final double C = eqn[0] / d;


        //  substitute x = y - A/3 to eliminate quadratic term:
        //     x^3 +Px + Q = 0
        //
        // Since we actually need P/3 and Q/2 for all of the
        // calculations that follow, we will calculate
        // p = P/3
        // q = Q/2
        // instead and use those values for simplicity of the code.
        double sq_A = A * A;
        double p = 1.0/3 * (-1.0/3 * sq_A + B);
        double q = 1.0/2 * (2.0/27 * A * sq_A - 1.0/3 * A * B + C);

        /* use Cardano's formula */

        double cb_p = p * p * p;
        double D = q * q + cb_p;

        final double sub = 1.0/3 * A;

        int num;
        if (D < 0) { /* Casus irreducibilis: three real solutions */
            // see: http://en.wikipedia.org/wiki/Cubic_function#Trigonometric_.28and_hyperbolic.29_method
            double phi = 1.0/3 * Math.acos(-q / Math.sqrt(-cb_p));
            double t = 2 * Math.sqrt(-p);

            if (res == eqn) {
                eqn = Arrays.copyOf(eqn, 4);
            }

            res[ 0 ] =  ( t * Math.cos(phi));
            res[ 1 ] =  (-t * Math.cos(phi + Math.PI / 3));
            res[ 2 ] =  (-t * Math.cos(phi - Math.PI / 3));
            num = 3;

            for (int i = 0; i < num; ++i) {
                res[ i ] -= sub;
            }

        } else {
            // Please see the comment in fixRoots marked 'XXX' before changing
            // any of the code in this case.
            double sqrt_D = Math.sqrt(D);
            double u = Math.cbrt(sqrt_D - q);
            double v = - Math.cbrt(sqrt_D + q);
            double uv = u+v;

            num = 1;

            double err = 1200000000*ulp(abs(uv) + abs(sub));
            if (iszero(D, err) || within(u, v, err)) {
                if (res == eqn) {
                    eqn = Arrays.copyOf(eqn, 4);
                }
                res[1] = -(uv / 2) - sub;
                num = 2;
            }
            // this must be done after the potential Arrays.copyOf
            res[ 0 ] =  uv - sub;
        }

        if (num > 1) { // num == 3 || num == 2
            num = fixRoots(eqn, res, num);
        }
        if (num > 2 && (res[2] == res[1] || res[2] == res[0])) {
            num--;
        }
        if (num > 1 && res[1] == res[0]) {
            res[1] = res[--num]; // Copies res[2] to res[1] if needed
        }
        return num;
    }

    // preconditions: eqn != res && eqn[3] != 0 && num > 1
    // This method tries to improve the accuracy of the roots of eqn (which
    // should be in res). It also might eliminate roots in res if it decideds
    // that they're not real roots. It will not check for roots that the
    // computation of res might have missed, so this method should only be
    // used when the roots in res have been computed using an algorithm
    // that never underestimates the number of roots (such as solveCubic above)
    private static int fixRoots(@NonNull double[] eqn, @NonNull double[] res, int num) {
        double[] intervals = {eqn[1], 2*eqn[2], 3*eqn[3]};
        int critCount = QuadCurve2D.solveQuadratic(intervals, intervals);
        if (critCount == 2 && intervals[0] == intervals[1]) {
            critCount--;
        }
        if (critCount == 2 && intervals[0] > intervals[1]) {
            double tmp = intervals[0];
            intervals[0] = intervals[1];
            intervals[1] = tmp;
        }

        // below we use critCount to possibly filter out roots that shouldn't
        // have been computed. We require that eqn[3] != 0, so eqn is a proper
        // cubic, which means that its limits at -/+inf are -/+inf or +/-inf.
        // Therefore, if critCount==2, the curve is shaped like a sideways S,
        // and it could have 1-3 roots. If critCount==0 it is monotonic, and
        // if critCount==1 it is monotonic with a single point where it is
        // flat. In the last 2 cases there can only be 1 root. So in cases
        // where num > 1 but critCount < 2, we eliminate all roots in res
        // except one.

        if (num == 3) {
            double xe = getRootUpperBound(eqn);
            double x0 = -xe;

            Arrays.sort(res, 0, num);
            if (critCount == 2) {
                // this just tries to improve the accuracy of the computed
                // roots using Newton's method.
                res[0] = refineRootWithHint(eqn, x0, intervals[0], res[0]);
                res[1] = refineRootWithHint(eqn, intervals[0], intervals[1], res[1]);
                res[2] = refineRootWithHint(eqn, intervals[1], xe, res[2]);
                return 3;
            } else if (critCount == 1) {
                // we only need fx0 and fxe for the sign of the polynomial
                // at -inf and +inf respectively, so we don't need to do
                // fx0 = solveEqn(eqn, 3, x0); fxe = solveEqn(eqn, 3, xe)
                double fxe = eqn[3];
                double fx0 = -fxe;

                double x1 = intervals[0];
                double fx1 = solveEqn(eqn, 3, x1);

                // if critCount == 1 or critCount == 0, but num == 3 then
                // something has gone wrong. This branch and the one below
                // would ideally never execute, but if they do we can't know
                // which of the computed roots is closest to the real root;
                // therefore, we can't use refineRootWithHint. But even if
                // we did know, being here most likely means that the
                // curve is very flat close to two of the computed roots
                // (or maybe even all three). This might make Newton's method
                // fail altogether, which would be a pain to detect and fix.
                // This is why we use a very stable bisection method.
                if (oppositeSigns(fx0, fx1)) {
                    res[0] = bisectRootWithHint(eqn, x0, x1, res[0]);
                } else if (oppositeSigns(fx1, fxe)) {
                    res[0] = bisectRootWithHint(eqn, x1, xe, res[2]);
                } else /* fx1 must be 0 */ {
                    res[0] = x1;
                }
                // return 1
            } else if (critCount == 0) {
                res[0] = bisectRootWithHint(eqn, x0, xe, res[1]);
                // return 1
            }
        } else if (num == 2 && critCount == 2) {
            // XXX: here we assume that res[0] has better accuracy than res[1].
            // This is true because this method is only used from solveCubic
            // which puts in res[0] the root that it would compute anyway even
            // if num==1. If this method is ever used from any other method, or
            // if the solveCubic implementation changes, this assumption should
            // be reevaluated, and the choice of goodRoot might have to become
            // goodRoot = (abs(eqn'(res[0])) > abs(eqn'(res[1]))) ? res[0] : res[1]
            // where eqn' is the derivative of eqn.
            double goodRoot = res[0];
            double badRoot = res[1];
            double x1 = intervals[0];
            double x2 = intervals[1];
            // If a cubic curve really has 2 roots, one of those roots must be
            // at a critical point. That can't be goodRoot, so we compute x to
            // be the farthest critical point from goodRoot. If there are two
            // roots, x must be the second one, so we evaluate eqn at x, and if
            // it is zero (or close enough) we put x in res[1] (or badRoot, if
            // |solveEqn(eqn, 3, badRoot)| < |solveEqn(eqn, 3, x)| but this
            // shouldn't happen often).
            double x = abs(x1 - goodRoot) > abs(x2 - goodRoot) ? x1 : x2;
            double fx = solveEqn(eqn, 3, x);

            if (iszero(fx, 10000000*ulp(x))) {
                double badRootVal = solveEqn(eqn, 3, badRoot);
                res[1] = abs(badRootVal) < abs(fx) ? badRoot : x;
                return 2;
            }
        } // else there can only be one root - goodRoot, and it is already in res[0]

        return 1;
    }

    // use newton's method.
    private static double refineRootWithHint(@NonNull double[] eqn, double min, double max, double t) {
        if (!inInterval(t, min, max)) {
            return t;
        }
        double[] deriv = {eqn[1], 2*eqn[2], 3*eqn[3]};
        double origt = t;
        for (int i = 0; i < 3; i++) {
            double slope = solveEqn(deriv, 2, t);
            double y = solveEqn(eqn, 3, t);
            double delta = - (y / slope);
            double newt = t + delta;

            if (slope == 0 || y == 0 || t == newt) {
                break;
            }

            t = newt;
        }
        if (within(t, origt, 1000*ulp(origt)) && inInterval(t, min, max)) {
            return t;
        }
        return origt;
    }

    private static double bisectRootWithHint(@NonNull double[] eqn, double x0, double xe, double hint) {
        double delta1 = Math.min(abs(hint - x0) / 64, 0.0625);
        double delta2 = Math.min(abs(hint - xe) / 64, 0.0625);
        double x02 = hint - delta1;
        double xe2 = hint + delta2;
        double fx02 = solveEqn(eqn, 3, x02);
        double fxe2 = solveEqn(eqn, 3, xe2);
        while (oppositeSigns(fx02, fxe2)) {
            if (x02 >= xe2) {
                return x02;
            }
            x0 = x02;
            xe = xe2;
            delta1 /= 64;
            delta2 /= 64;
            x02 = hint - delta1;
            xe2 = hint + delta2;
            fx02 = solveEqn(eqn, 3, x02);
            fxe2 = solveEqn(eqn, 3, xe2);
        }
        if (fx02 == 0) {
            return x02;
        }
        if (fxe2 == 0) {
            return xe2;
        }

        return bisectRoot(eqn, x0, xe);
    }

    private static double bisectRoot(@NonNull double[] eqn, double x0, double xe) {
        double fx0 = solveEqn(eqn, 3, x0);
        double m = x0 + (xe - x0) / 2;
        while (m != x0 && m != xe) {
            double fm = solveEqn(eqn, 3, m);
            if (fm == 0) {
                return m;
            }
            if (oppositeSigns(fx0, fm)) {
                xe = m;
            } else {
                fx0 = fm;
                x0 = m;
            }
            m = x0 + (xe-x0)/2;
        }
        return m;
    }

    private static boolean inInterval(double t, double min, double max) {
        return min <= t && t <= max;
    }

    private static boolean within(double x, double y, double err) {
        double d = y - x;
        return (d <= err && d >= -err);
    }

    private static boolean iszero(double x, double err) {
        return within(x, 0, err);
    }

    private static boolean oppositeSigns(double x1, double x2) {
        return (x1 < 0 && x2 > 0) || (x1 > 0 && x2 < 0);
    }

    private static double solveEqn(@NonNull double[] eqn, int order, double t) {
        double v = eqn[order];
        while (--order >= 0) {
            v = v * t + eqn[order];
        }
        return v;
    }

    /*
     * Computes M+1 where M is an upper bound for all the roots in of eqn.
     * See: http://en.wikipedia.org/wiki/Sturm%27s_theorem#Applications.
     * The above link doesn't contain a proof, but I [dlila] proved it myself
     * so the result is reliable. The proof isn't difficult, but it's a bit
     * long to include here.
     * Precondition: eqn must represent a cubic polynomial
     */
    private static double getRootUpperBound(@NonNull double[] eqn) {
        double d = eqn[3];
        double a = eqn[2];
        double b = eqn[1];
        double c = eqn[0];

        double M = 1 + max(max(abs(a), abs(b)), abs(c)) / abs(d);
        M += ulp(M) + 1;
        return M;
    }



    public boolean contains(double x, double y) {
        if (!(x * 0.0 + y * 0.0 == 0.0)) {
            /* Either x or y was infinite or NaN.
             * A NaN always produces a negative response to any test
             * and Infinity values cannot be "inside" any path so
             * they should return false as well.
             */
            return false;
        }
        // We count the "Y" crossings to determine if the point is
        // inside the curve bounded by its closing line.
        double x1 = getX1();
        double y1 = getY1();
        double x2 = getX2();
        double y2 = getY2();
        int crossings =
            (Curve.pointCrossingsForLine(x, y, x1, y1, x2, y2) +
             Curve.pointCrossingsForCubic(x, y,
                                          x1, y1,
                                          getCtrlX1(), getCtrlY1(),
                                          getCtrlX2(), getCtrlY2(),
                                          x2, y2, 0));
        return ((crossings & 1) == 1);
    }


    public boolean contains(@NonNull Point2D p) {
        return contains(p.getX(), p.getY());
    }


    public boolean intersects(double x, double y, double w, double h) {
        // Trivially reject non-existant rectangles
        if (w <= 0 || h <= 0) {
            return false;
        }

        int numCrossings = rectCrossings(x, y, w, h);
        // the intended return value is
        // numCrossings != 0 || numCrossings == Curve.RECT_INTERSECTS
        // but if (numCrossings != 0) numCrossings == INTERSECTS won't matter
        // and if !(numCrossings != 0) then numCrossings == 0, so
        // numCrossings != RECT_INTERSECT
        return numCrossings != 0;
    }


    public boolean intersects(@NonNull Rectangle2D r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }


    public boolean contains(double x, double y, double w, double h) {
        if (w <= 0 || h <= 0) {
            return false;
        }

        int numCrossings = rectCrossings(x, y, w, h);
        return !(numCrossings == 0 || numCrossings == Curve.RECT_INTERSECTS);
    }

    private int rectCrossings(double x, double y, double w, double h) {
        int crossings = 0;
        if (!(getX1() == getX2() && getY1() == getY2())) {
            crossings = Curve.rectCrossingsForLine(crossings,
                                                   x, y,
                                                   x+w, y+h,
                                                   getX1(), getY1(),
                                                   getX2(), getY2());
            if (crossings == Curve.RECT_INTERSECTS) {
                return crossings;
            }
        }
        // we call this with the curve's direction reversed, because we wanted
        // to call rectCrossingsForLine first, because it's cheaper.
        return Curve.rectCrossingsForCubic(crossings,
                                           x, y,
                                           x+w, y+h,
                                           getX2(), getY2(),
                                           getCtrlX2(), getCtrlY2(),
                                           getCtrlX1(), getCtrlY1(),
                                           getX1(), getY1(), 0);
    }


    public boolean contains(@NonNull Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }


    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }


    @NonNull
    public PathIterator getPathIterator(AffineTransform at) {
        return new CubicIterator(this, at);
    }


    @NonNull
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new FlatteningPathIterator(getPathIterator(at), flatness);
    }


    @NonNull
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                throw new InternalError(e);
            }
        }
        return null;
    }
}
