package mri.advent.y2022.day07

import mri.advent.y2022.BaseDay

/** --- Day 07:  --- */
class Day07(inFile: String = "/day07.in"): BaseDay(inFile)  {

    data class FileNode(val name: String, val parent: FileNode? = null, val isDirectory: Boolean = false, var size: Long = 0, val children: MutableList<FileNode> = mutableListOf() ) {
        override fun toString() = name
        fun allChildren(): List<FileNode> = children + children.flatMap { it.allChildren() }
        fun totalSize(): Long = size + children.sumOf { it.totalSize() }
    }

    fun parseFileSystem() : FileNode {
        val root = FileNode("/", null, true)
        var currDir: FileNode? = null

        data.lines().forEach { line ->
            if (line.startsWith("$ cd")) {
                val targetDir = line.substringAfterLast(" ")
                println(" move to $targetDir")
                if (targetDir == "/") {
                    currDir = root
                } else if (targetDir == "..") {
                    currDir = currDir!!.parent
                } else {
                    currDir = currDir!!.children.firstOrNull { it.name == targetDir }?: throw RuntimeException("!!sous répertoire inconnu: $targetDir")
                }
            } else if (line.startsWith("dir")) {
                currDir!!.children.add(FileNode(line.substringAfterLast(" "), currDir, true))
            } else if (line.startsWith("$ ls")) {
                // noop
            } else {
                val (size, fName) = line.split(" ")
                println("  file found: $fName [$size]")
                currDir!!.children.add(FileNode(fName, currDir, size = size.toLong()))
            }
        }
        return root
    }
    override fun part1(): Any {
        val fs =  parseFileSystem()
        return fs.allChildren()
            .filter { it.isDirectory }
            .map { it.totalSize() }
            .filter { it <= 100000 }
            .sumOf { it }
    }

    override fun part2(): Any {
        val fs = parseFileSystem()
        val wantedSpace = 30000000L - (70000000L - fs.totalSize())

        return fs.allChildren()
            .filter { it.isDirectory }
            .map { it.totalSize() }
            .filter { it >= wantedSpace }
            .min()
    }
}

fun main() {
    Day07().execute()
}
