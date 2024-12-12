package mri.advent.y2024.utils

// create combined pairs of elements in the  given list
fun <T> List<T>.pairs(): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()
    for (i in this.indices) {
        for (j in i + 1 until this.size) {
            pairs.add(Pair(this[i], this[j]))
        }
    }
    return pairs
}

/**
 * Sort numbers in current list and returns ranges of consecutive numbers.
 * ex.:  [1, 2, 4, 5, 6]  ==>  {  [1..2] , [4..6] }
 */
fun List<Int>.consecutiveRanges(): List<IntRange> {
    if (this.isEmpty()) return emptyList()
    val sorted = this.sorted()
    val resultRanges = mutableListOf<IntRange>()
    var start = sorted[0]
    var prev = sorted[0]
    (1 until sorted.size).forEach { index ->
        val current = sorted[index]
        if (current != prev + 1) {
            resultRanges.add(start..prev)
            start = current
        }
        prev = current
    }
    resultRanges.add(start..prev)
    return resultRanges
}


