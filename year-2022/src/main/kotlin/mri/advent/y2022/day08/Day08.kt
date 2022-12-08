package mri.advent.y2022.day08

import mri.advent.y2022.BaseDay

/** --- Day 08:  --- */
class Day08(inFile: String = "/day08.in") : BaseDay(inFile) {

    enum class Direction { UP, DOWN, LEFT, RIGHT }

    data class Tree(val height: Int) {
        // part 1
        var visible: Boolean = false

        // part 2
        val views = Direction.values().associateWith { 0 }.toMutableMap()
        fun viewScore() = views.values.reduce { acc, i -> acc * i }
    }

    data class TreeRange(val direction: Direction, val trees: List<Tree>)

    data class Grid(val trees: List<List<Tree>>) {
        private val height = trees.size
        private val width = trees[0].size
        val treeRanges = mutableListOf<TreeRange>()

        init {
            // tree columns - up & down
            (0 until width).forEach { col ->
                with((0 until height).map { trees[it][col] }) {
                    treeRanges.add(TreeRange(Direction.DOWN, this))
                    treeRanges.add(TreeRange(Direction.UP, this.reversed()))
                }
            }
            // tree rows - left & right
            trees.forEach { row ->
                with((0 until width).map { row[it] }) {
                    treeRanges.add(TreeRange(Direction.RIGHT, this))
                    treeRanges.add(TreeRange(Direction.LEFT, this.reversed()))
                }
            }
        }
    }

    private fun parseTrees(): Grid {
        return Grid(data.lines().map { it.toCharArray().map { height -> Tree(height.digitToInt()) } })
    }

    override fun part1(): Any {
        val grid = parseTrees()
        // compute visible trees
        grid.treeRanges.forEach { range ->
            var max = -1
            range.trees.forEach {
                if (it.height > max) {
                    it.visible = true
                    max = it.height
                }
            }
        }
        return grid.trees.flatten().count { it.visible }
    }

    override fun part2(): Any {
        val grid = parseTrees()
        // compute view score on each tree
        grid.treeRanges.forEach { range ->
            (0 until range.trees.size).forEach { treeIndex ->
                val currentTree = range.trees[treeIndex]
                var nbTrees = 0
                for (nextTree in range.trees.subList(0, treeIndex).reversed()) {
                    nbTrees++
                    if (nextTree.height >= currentTree.height) {
                        break
                    }
                }
                currentTree.views[range.direction] = nbTrees
            }
        }
        return grid.trees.flatten().maxOf { it.viewScore() }
    }
}

fun main() {
    Day08().execute()
    //  Part 1: 1818
    //  Part 2: 368368
}
