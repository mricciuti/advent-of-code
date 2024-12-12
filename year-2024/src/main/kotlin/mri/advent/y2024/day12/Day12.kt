import mri.advent.y2024.BaseDay
import mri.advent.y2024.utils.consecutiveRanges
import mri.advent.y2024.utils.geo2d.*

/* --- Day 12:  Garden Groups  https://adventofcode.com/2024/day/12  --- */

data class GardenPlot(val position: Vec2D, val type: Char) {
    val sides = mutableSetOf<Direction>()
    var region: Region? = null

    fun hasSide(direction: Direction) = this.sides.contains(direction)
    fun perimeter() = sides.size

    override fun toString() = "$type ($position)"
}

class Region(val type: Char, val plots: MutableSet<GardenPlot> = mutableSetOf()) {
    fun add(plot: GardenPlot) {
        plots.add(plot)
        plot.region = this
    }

    private fun area() = plots.size.toLong()
    private fun perimeter() = plots.sumOf { it.perimeter() }

    /**
     * Part 2 : create sides for this region.
     * algo: merge consecutive sides from each plot in same horizontal & vertical lines
     */
    fun sides(): Int {
        var sides = 0
        // scan region vertically, count vertical plot sides & merge
        (plots.minOf { it.position.x }..plots.maxOf { it.position.x }).forEach { x ->
            sides += plots.filter { it.position.x == x && it.hasSide(Direction.W) }.map { it.position.y }.consecutiveRanges().size
            sides += plots.filter { it.position.x == x && it.hasSide(Direction.E) }.map { it.position.y }.consecutiveRanges().size
        }
        // sscan region horizontally, count horizontal plot sides & merge
        (plots.minOf { it.position.y }..plots.maxOf { it.position.y }).forEach { y ->
            sides += plots.filter { it.position.y == y && it.hasSide(Direction.N) }.map { it.position.x }.consecutiveRanges().size
            sides += plots.filter { it.position.y == y && it.hasSide(Direction.S) }.map { it.position.x }.consecutiveRanges().size
        }
        return sides
    }

    /**
     * calculate fence prince:
     * - part 1 :  area * region perimeter
     * - part 2 :  area * region sides
     */
    fun fencingPrice(part2: Boolean = false) = if (part2) area() * sides() else area() * perimeter()

    override fun toString() = "region $type containing ${plots.size} plots"

    fun dump() {
        val (price1, price2) = fencingPrice() to fencingPrice(true)
        println("region type: $type plots:${plots.size}, area=${area()}, perimeter ${perimeter()}, sides=${sides()}, price1=$price1, price2=$price2")
    }
}

class Garden(lines: List<String>) : CharGrid(lines) {

    private val plots = cells.mapIndexed { y, row -> row.mapIndexed { x, c -> GardenPlot(Vec2D(x, y), c) } }
    val regions = createRegions()

    private fun neighbors(plot: GardenPlot) = Direction.CARDINALS.map { plot.position.move(it) }.filter { inGrid(it) }.map { cell -> plots[cell.y][cell.x] }

    private fun fillRegion(plot: GardenPlot, region: Region) {
        region.add(plot)
        // expand to  neighhor plots with same type
        val neighborsToAdd = neighbors(plot)
            .filter { neighbor -> neighbor.type == plot.type }
            .filter { it.region == null }
        neighborsToAdd.forEach { fillRegion(it, region) }
    }

    private fun createRegions(): List<Region> {
        val vRegions = mutableListOf<Region>()
        plots.forEach { row ->
            row.forEach { plot ->
                // part 1 : plot.perimeter = 4 - neighbors(plot).count { neighbor -> neighbor.type == plot.type }
                // part 2 : set plot's sides
                Direction.CARDINALS.forEach { direction ->
                    val next = plot.position.move(direction)
                    if (!inGrid(next) || plots[next.y][next.x].type != plot.type) {
                        plot.sides.add(direction)
                    }
                }
                if (plot.region == null) {
                    val region = Region(plot.type)
                    vRegions.add(region)
                    fillRegion(plot, region)
                }
            }
        }
        vRegions.forEach { it.dump() }
        return vRegions
    }

    fun fencingPrice(part2: Boolean = false) = regions.sumOf { it.fencingPrice(part2) }
}


class Day12(dataOrNull: String? = null) : BaseDay(dataOrNull) {

    private val garden = Garden(data.lines())

    override fun partOne() = garden.fencingPrice()
    override fun partTwo() = garden.fencingPrice(part2 = true)
}

fun main() {
    Day12().run()
}