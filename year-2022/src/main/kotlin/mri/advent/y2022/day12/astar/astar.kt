package mri.advent.y2022.day12.astar

const val MAX_SCORE = 999999999

/**
 * Backtrack path: use the cameFrom values to Backtrack to the start position to generate the path
 */
fun backtrackPath(currentPos: Position, cameFrom: Map<Position, Position>): List<Position> {
    val path = mutableListOf(currentPos)
    var current = currentPos
    while (cameFrom.containsKey(current)) {
        current = cameFrom.getValue(current)
        path.add(0, current)
    }
    return path.toList()
}

/**
 * A* algorithm
 */
fun aStarSearch(grid: Grid, start: Position, target: Position, moveCostFct: (Movement) -> Int): Pair<List<Position>, Int> {
    val openVertices = mutableSetOf(start)
    val closedVertices = mutableSetOf<Position>()
    val costFromStart = mutableMapOf(start to 0)
    val estimatedTotalCost = mutableMapOf(start to start.distance(target))

    val cameFrom = mutableMapOf<Position, Position>()  // Used to generate path by back tracking

    while (openVertices.size > 0) {
        val currentPos = openVertices.minByOrNull { estimatedTotalCost.getValue(it) }!!

        // Check if we have reached the finish
        if (currentPos == target) {
            // Backtrack to generate the most efficient path
            val path = backtrackPath(currentPos, cameFrom)
            return Pair(path, estimatedTotalCost.getValue(target)) // First Route to finish will be optimum route
        }

        // Mark the current vertex as closed
        openVertices.remove(currentPos)
        closedVertices.add(currentPos)

        grid.neighbours(currentPos)
            .filterNot { closedVertices.contains(it) }  // Exclude previous visited vertices
            .forEach { neighbour ->
                val score = costFromStart.getValue(currentPos)  + moveCostFct.invoke(Movement(currentPos, neighbour))  //+ moveCost(grid, currentPos, neighbour)
                if (score < costFromStart.getOrDefault(neighbour, MAX_SCORE)) {
                    if (!openVertices.contains(neighbour)) {
                        openVertices.add(neighbour)
                    }
                    cameFrom[neighbour] = currentPos
                    costFromStart[neighbour] = score
                    estimatedTotalCost[neighbour] = score + neighbour.distance(target)
                }
            }
    }
    throw IllegalArgumentException("No Path from Start $start to Finish $target")
}
