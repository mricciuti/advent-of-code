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