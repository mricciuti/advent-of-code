package mri.advent.y2024.utils

fun String.toIntPair(): Pair<Int, Int> = with(this.split(Regex("\\s+"))) {
    return this[0].toInt() to this[1].toInt()
}