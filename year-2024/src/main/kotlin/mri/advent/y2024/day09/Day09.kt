package mri.advent.y2024.day09

import mri.advent.y2024.BaseDay
import kotlin.collections.joinToString

/** --- Day 09: Disk Fragmenter  (https://adventofcode.com/2024/day/09)  --- */
class Day09(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    // sequence of blocks in the disk - can be a File (with id >= 0) of free space (id = -1)
    data class Section(val id: Int = -1, val length: Int) {
        val isFile = id >= 0
        fun blocks() = IntArray(length) { id }
        override fun toString() = (if (isFile) "${id % 10}" else ".").repeat(length)
    }

    data class Disk(val sections: List<Section>) {
        val blocks = sections.flatMap { it.blocks().toList() }.toMutableList()

        // part 1
        fun defragBlocks() {
            while (blocks.indexOf(-1) >= 0) {
                blocks[blocks.indexOf(-1)] = blocks.removeLast()
            }
        }

        // reorg - part 2
        fun defragFiles(): Disk {
            val updatedSections = sections.toMutableList()
            for (fileToMove in updatedSections.filter { it.isFile }.reversed()) {
                val fileToMoveIndex = updatedSections.indexOf(fileToMove)
                debug(" process file $fileToMove at position $fileToMoveIndex")
                val targetIndex = updatedSections.indexOfFirst { !it.isFile && it.length >= fileToMove.length }
                if (targetIndex in 0 .. fileToMoveIndex) {
                    debug("   ✅ move file $fileToMove from $fileToMoveIndex to $targetIndex")

                    // remove file => add free blocks
                    updatedSections.removeAt(fileToMoveIndex)
                    updatedSections.add(fileToMoveIndex, Section(-1, fileToMove.length))

                    // insert file & remaining free space
                    val freeSpaceRemoved = updatedSections.removeAt(targetIndex)
                    updatedSections.add(targetIndex, fileToMove)
                    val rest = freeSpaceRemoved.length - fileToMove.length
                    if (rest > 0) {
                        updatedSections.add(targetIndex + 1, Section(-1, rest))
                    }
                } else {
                    debug("   ❌ file $fileToMove cannot move")
                }
            }
            return Disk(updatedSections)
        }
        fun checksum() = blocks.mapIndexed { index, i -> if (i >= 0) i.toLong() * index else 0 }.sum()
        override fun toString() = sections.joinToString("")
    }

    fun String.toDisk() = Disk(this.mapIndexed { index, ch -> Section(if (index % 2 == 0) index / 2 else -1, ch.digitToInt()) })

    override fun partOne() = data.lines().first().toDisk().also { it.defragBlocks() }.checksum()
    override fun partTwo() = data.lines().first().toDisk().defragFiles().checksum()

}

fun debug(message: Any) {
    println(message)
}

fun main() {
    Day09().run()
}