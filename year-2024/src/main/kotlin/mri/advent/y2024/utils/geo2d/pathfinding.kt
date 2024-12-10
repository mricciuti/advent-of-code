package mri.advent.y2024.utils.geo2d

data class Path(private val steps: List<Vec2D>) {
    fun start() = steps.first()
    fun end() = steps.last()
    fun contains(position: Vec2D) = steps.contains(position)
    fun expand(to: Vec2D) = Path(steps.toList() + to)
    override fun toString() = steps.joinToString(" -> ") { "${it.x},${it.y}" }
}

/**
 * Pathfinding: returns all possible paths from a given start position to a target position.
 * @param pathCompletedPredicate function to determine if a path is final (target reached)
 * @param validMovePredicate: function to provide neighbors to be visited from a given current position.
 * @param directions : valid directions in grid (default: Cardinal directions)
 */
fun <T> Grid<T>.findAllPaths(startPosition: Vec2D,
                             pathCompletedPredicate: (path: Path) -> Boolean,
                             validMovePredicate: (from: Vec2D, to: Vec2D) -> Boolean,
                             directions: List<Direction> = Direction.CARDINALS): List<Path> {

    val resultPaths = mutableListOf<Path>()

    val queue = ArrayDeque<Path>()
    queue.add(Path(listOf(startPosition)))

    while (queue.isNotEmpty()) {
        val currentPath = queue.removeFirst()
        val currentPosition = currentPath.end()

        if (pathCompletedPredicate(currentPath)) {
            resultPaths.add(currentPath)
            continue
        }

        // expand path in all valid directions
        directions.map { currentPosition to currentPosition.move(it) }
            .filter { inGrid(it.second) }
            .filter { validMovePredicate(it.first, it.second) }
            .filter { !currentPath.contains(it.second) } // avoid cycles
            .forEach { queue.add(currentPath.expand(it.second)) }
    }
    return resultPaths
}