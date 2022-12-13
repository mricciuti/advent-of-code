package mri.advent.y2022.day13

import mri.advent.y2022.BaseDay

const val START = '['
const val END = ']'
const val SEP = ','

sealed class Packet : Comparable<Packet> {
    class PacketList(val subPackets: List<Packet>) : Packet() {
        val size = subPackets.size
        override fun compareTo(other: Packet): Int {
            if (other is PacketValue) return this.compareTo(other.toPacketList())
            other as PacketList
            val commonSize = minOf(this.size, (other.size))
            (0 until commonSize).forEach {
                val res = this.subPackets[it].compareTo(other.subPackets[it])
                if (res != 0)
                    return res
            }
            return this.size.compareTo(other.size)
        }

        override fun toString() = "[" + subPackets.joinToString(", ") { it.toString() } + "]"
    }

    class PacketValue(val value: Int) : Packet() {
        override fun toString() = "$value"
        override fun compareTo(other: Packet): Int {
            if (other is PacketValue) return this.value.compareTo(other.value)
            return this.toPacketList().compareTo(other)
        }

        fun toPacketList() = PacketList(listOf(this))
    }
}

fun String.toPacket(): Packet {
    if (this.startsWith(START)) {
        val subPackets = mutableListOf<Packet>()
        val content = this.subSequence(1, this.length - 1)
        if (!content.isEmpty()) {
            var nestedLevel = 0
            var nextPacketStart = 0
            content.forEachIndexed { index, c ->
                if (c == START) {
                    nestedLevel++
                } else if (c == END) {
                    nestedLevel--
                } else if (c == SEP) {
                    if (nestedLevel == 0) {
                        subPackets.add(content.substring(nextPacketStart, index).toPacket())
                        nextPacketStart = index + 1
                    }
                }
            }
            if (nextPacketStart < content.length) {
                subPackets.add(content.substring(nextPacketStart, content.length).toPacket())
            }
        }
        return Packet.PacketList(subPackets)
    } else {
        return Packet.PacketValue(this.toInt())
    }
}

/** --- Day 13:  --- */
class Day13(inFile: String = "/day13.in") : BaseDay(inFile) {

    override fun part1(): Any {
        return data.lines().asSequence()
            .filter { it.isNotBlank() }
            .chunked(2)
            .map { Pair(it[0].toPacket(), it[1].toPacket()) }
            .mapIndexed { index, pair -> index + 1 to (pair.first <= pair.second) }
            .filter { it.second }
            .sumOf { it.first }
    }

    override fun part2(): Any {
        val dividers = sequenceOf("[[2]]", "[[6]]")

        return data.lines().filter { it.isNotBlank() }
            .plus(dividers)
            .map { it.toPacket() }
            .sorted()
            .mapIndexed { index, packet -> index + 1 to packet }
            .filter { dividers.contains(it.second.toString()) }
            .map { it.first }
            .reduce { acc, i -> acc * i }
    }
}

fun main() {
    Day13().execute()
}