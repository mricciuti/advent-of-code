package mri.advent.y2022.day15

import mri.advent.y2022.BaseDay
import mri.codingame.utils.ranges.*
import kotlin.math.abs

/** --- Day 15:  --- */
class Day15(inFile: String = "/day15.in") : BaseDay(inFile, debug = false) {

    data class Point(val x: Int, val y: Int) {
        //  Manhattan distance
        fun distanceTo(other: Point) = abs(other.x - x) + abs(other.y - y)
    }

    data class SensorWithBeacon(val sensor: Point, val beacon: Point) {
        val distance = sensor.distanceTo(beacon)

        // Range scanné par ce sensor sur la ligne donnée
        fun scannedRange(rowNum: Int): IntRange {
            val deltaY = this.sensor.y - rowNum
            val deltaX = this.distance - abs(deltaY)
            if (deltaX >= 0) {
                return this.sensor.x - deltaX..this.sensor.x + deltaX
            }
            return VOID_RANGE
        }
    }

    private fun String.toPoint(): Point {
        val split = this.split(", ")
        return Point(split[0].replace("x=", "").toInt(), split[1].replace("y=", "").toInt())
    }

    private fun String.toSensorAndBeacon(): SensorWithBeacon {
        val str = this.replace("Sensor at ", "").split(": closest beacon is at ")
        val (sensor, beacon) = str[0].toPoint() to str[1].toPoint()
        return SensorWithBeacon(sensor, beacon)
    }

    fun solve1(searchRow: Int): Any {
        val sensorWithBeacons = data.lines().map { it.toSensorAndBeacon() }
        val scannedPlotsAtRow = mutableSetOf<Point>()
        sensorWithBeacons
            .flatMap { sAndB -> sAndB.scannedRange(searchRow) }
            .forEach { scannedPlotsAtRow.add(Point(it, searchRow)) }

        // !!!! retrancher le nb de beacons sur cette ligne
        val beaconsOnRow = sensorWithBeacons.map { it.beacon }.filter { it.y == searchRow }.distinct().count()
        return scannedPlotsAtRow.size - beaconsOnRow
    }

    fun solve2(maxCoord: Int): Any {
        var targetBeacon: Point? = null
        val sensorWithBeacons = data.lines().map { it.toSensorAndBeacon() }

        // zone de recherche sur la ligne
        val searchRangeX = (0..maxCoord)

        // scan chaque ligne,
        // on s'arrête dès qu'on trouve une position non couverte par un sensor sur cette ligne
        for (rowNum in 0..maxCoord) {
            debug("processing row $rowNum")
            val xRanges = mutableListOf<IntRange>()

            sensorWithBeacons
                .map { it.scannedRange(rowNum) }
                .filter { it.overlaps(searchRangeX) }
                .forEach { xRanges.add(it.intersection(searchRangeX)) }

            val reducedRanges = xRanges.reduce()
            // si toute la ligne était couverte, on n'aura qu'une seule range 0..maxCoord,
            // => si plusieurs ranges, on a trouvé un "trou"
            if (reducedRanges.size > 1) {
                debug(" GOTCHA !!")
                targetBeacon = Point(reducedRanges[0].last + 1, rowNum)
            }
        }
        if (targetBeacon != null) {
            return targetBeacon.x * 4000000L + targetBeacon.y
        }
        throw RuntimeException("beacon introuvable!")
    }

    override fun part1() = solve1(2000000)
    override fun part2() = solve2(4000000)

    /*
Part 1: 4961647
  => time taken: 1375 ms
Part 2: 12274327017867
  => time taken: 4759 ms
     */
}

fun main() {
    Day15().execute()
}