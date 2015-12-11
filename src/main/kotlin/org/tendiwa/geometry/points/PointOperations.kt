package org.tendiwa.geometry.points

import org.tendiwa.geometry.lines.Line
import org.tendiwa.geometry.segments.Segment
import org.tendiwa.geometry.vectors.Vector
import org.tendiwa.math.doubles.isCloseToZero
import org.tendiwa.plane.directions.Direction

infix fun Point.reallyCloseTo(another: Point): Boolean =
    (this.x - another.x).isCloseToZero && (this.y - another.y).isCloseToZero

fun comparePointsLinewise(a: Point, b: Point): Int {
    val signum = Math.signum(a.x - b.x).toInt();
    if (signum == 0) {
        return Math.signum(a.y - b.y).toInt();
    }
    return signum;
}

/**
 * Returns squared distance to some point.
 *
 * This method has better performance than [Point.distanceTo] because it
 * doesn't have to compute a square root; see
 * [Point-to-Point Distance](http://mathworld.wolfram.com/Point-PointDistance2-Dimensional.html).
 *
 * Optimizations come from the fact that:
 *
 * `(a squaredDistanceTo b).compareTo(a squaredDistanceTo c)`
 *
 * is equivalent to:
 *
 * `(a distanceTo b).compareTo(a distanceTo c)`
 */
infix fun Point.squaredDistanceTo(target: Point): Double =
    (target.x - this.x) * (target.x - this.x) + (target.y - this.y) * (target.y - this.y)

infix fun Point.segmentTo(target: Point): Segment =
    Segment(this, target)

infix fun Point.lineThrough(target: Point): Line =
    Line(
        this.y - target.y,
        target.x - this.x,
        (this.x - target.x) * this.y + (target.y - this.y) * this.x
    )

infix fun Point.vectorTo(target: Point): Vector =
    Vector(target.x - this.x, target.y - this.y)

/**
 * Creates a [Segment] by placing its another end *relative* to this point.
 */
fun Point.spanSegment(dx: Double, dy: Double): Segment =
    Segment(this, this.move(dx, dy))

fun Point.spanSegment(direction: Direction, distance: Double): Segment =
    Segment(this, this.moveKing(direction, distance))

fun Point.spanHorizontalSegment(dx: Double): Segment =
    Segment(this, this.move(dx, 0.0))

fun Point.spanVerticalSegment(dy: Double): Segment =
    Segment(this, this.move(0.0, dy))
