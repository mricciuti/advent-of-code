package mri.codingame.utils.ranges

/**
 *  Util `IntRange` extension functions ==============================================
 */

// an empty range , to avoid null checks
val VOID_RANGE = (0 until 0)

/**
 * Test if current range overlaps with the other given range.
 * => return true if at least one element in common
 */
fun IntRange.overlaps(other: IntRange) = other != VOID_RANGE && this.first <= other.last && this.last >= other.first

/**
 * Returns intersection between this range and the given one. (inclusive)
 * => return new range which contains all elements common to this and other ranges
 */
fun IntRange.intersection(other: IntRange): IntRange {
    if (!this.overlaps(other))
        return VOID_RANGE
    return IntRange(maxOf(this.first, other.first), minOf(this.last, other.last))
}

/**
 * Test if current range contains the given range, inclusive
 */
fun IntRange.contains(other: IntRange) = other != VOID_RANGE && this.first <= other.first && this.last >= other.last

/**
 * Test if current range is adjacent to the given one
 * > given ranges [x1,x2] [y1,y2]:  return true if  y1==x2+1  OR x1==y2+1
 */
fun IntRange.adjacentTo(other: IntRange) = other != VOID_RANGE && (this.first == other.last + 1 || other.first == this.last + 1)

/**
 * Merging given range into current range.
 * - returns both current and given ranges if there is no overlap
 * - returns new single merged range if current and given ranges overlap
 */
fun IntRange.merge(other: IntRange): List<IntRange> {
    if (this.overlaps(other) || this.adjacentTo(other)) {
        return listOf(minOf(this.first, other.first)..maxOf(this.last, other.last))
    }
    return listOf(this, other)
}

/**
 * Reduce list of ranges (merge)
 *  [0..4 , 2..6] becomes [0..6]
 *  [0..4 , 5..6] becomes [0..6]
 *  [0..4 , 6..8] remains [0..4 , 6..8]
 */
fun List<IntRange>.reduce(): List<IntRange> {
    if (this.size < 2) return this
    val reduced = mutableListOf<IntRange>()
    val sorted = this.sortedBy { it.first }
    reduced.add(sorted.first())
    (1 until sorted.size).forEach { idx ->
        val lastAdded = reduced.removeLast()
        reduced.addAll(lastAdded.merge(sorted[idx]))
    }
    return reduced
}


class Tester {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val range0_4 = 0..4
            val range2_6 = 2..6
            val range5_8 = 5..8
            val range6_9 = 6..9
            println(" overlaps true: " + range0_4.overlaps(range2_6))
            println(" overlaps true: " + range2_6.overlaps(range0_4))
            println(" overlaps false: " + range0_4.overlaps(range5_8))
            println(" overlaps false: " + range5_8.overlaps(range0_4))
            println(" adjacent: " + range0_4.adjacentTo(range5_8))

            println("merged: ${range0_4.merge(range2_6)}")
            println("merged: ${range0_4.merge(range6_9)}")

            println("reduce: ${listOf(range0_4, range2_6, range5_8, range6_9).reduce()}")

            println("interect: ${range0_4.intersection(range2_6)}")
            println("interect: ${range2_6.intersection(range0_4)}")
            println("interect: ${range0_4.intersection(range5_8)}")

            println("reduced ${listOf(15..20, 0..13, 15..17).reduce()}")
        }
    }
}