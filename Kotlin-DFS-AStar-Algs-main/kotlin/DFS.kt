import java.util.*

class DFS(private var startX: Int, private var startY: Int, private var endX: Int, private var endY: Int, private val gridObject: Grid) {
    // Number of rows
    private val M = gridObject.rows

    // Number of columns
    private val N = gridObject.cols

    // Maze
    private val maze = gridObject.grid

    // 4 Directions: 0-Right, 1-Left, 2-Down, 3-Up
    private val directions: List<List<Int>> = listOf(
        listOf(0, 2),
        listOf(0, -2),
        listOf(2, 0),
        listOf(-2, 0)
    )

    var totalStepsCounter = 0
    var deadEndCounter = 0
    var fromEndToStartStepsCounter = 0

    var pushTrigger = false

    // Iterative DFS Algorithm
    fun dfsIteration() {
        // Create a Stack
        val stack = Stack<List<Int>>()
        // Push start position to the stack
        stack.push(listOf(startX, startY))
        // Mark start cell as visited
        maze[startX][startY] = true
        // Print Maze
        gridObject.printGrid()
        // Var to check if we get to end point
        var endPoint = false

        // As long as we have an element in the Stack or we don't get to end point
        while (!stack.empty() && !endPoint) {
            // Get the current element top of the stack
            val cur = stack.peek()
            // Get a random available direction
            val randomWay = getRandomDirection(cur[0], cur[1])

            // AS long as we have a way and we didn't exceed limit
            if (randomWay != -1) {
                // Get new position
                val newX = cur[0] + directions[randomWay][0]
                val newY = cur[1] + directions[randomWay][1]
                // check if we have the way been built
                if (!maze[newX][newY]) {
                    // Remove the walls between current and new cell
                    wallRemover(randomWay, newX, newY)
                    // Mark new cell as visited
                    maze[newX][newY] = true
                    // Print Maze
                    gridObject.printGrid()
                    // Push next cell to stack
                    stack.push(listOf(newX, newY))
                    // increment step counter
                    totalStepsCounter++
                }
                // check if we get to end point by this way
                if (maze[newX][newY] == maze[endX][endY]){
                    endPoint = true
                    println("Got to end point")
                    // Count steps from end to start
                    while (!stack.empty()) {
                        fromEndToStartStepsCounter++
                        stack.pop()
                    }
                }
                // set pushTrigger is true when we are searching
                pushTrigger = true
            }
            // Pop current cell for backtracking
            else {
                stack.pop()
                // If we are making pop when pushTrigger is true that means that we are in dead end
                if (pushTrigger) {
                    deadEndCounter++
                    // set pushTrigger to false as we can make some pops until we can move on
                    pushTrigger = false
                }
            }
        }
    }

    // Removes all the walls between current and newly found position by looking at the direction index
    private fun wallRemover(index: Int, newX: Int, newY: Int) {
        // 0-Right, 1-Left, 2-Down, 3-Up
        when (index) {
            0 -> {
                maze[newX][newY - 2] = true
                maze[newX][newY - 1] = true
            }
            1 ->  {
                maze[newX][newY + 2] = true
                maze[newX][newY + 1] = true
            }
            2 ->  {
                maze[newX - 2][newY] = true
                maze[newX - 1][newY] = true
            }
            3 ->  {
                maze[newX + 2][newY] = true
                maze[newX + 1][newY] = true
            }
        }
    }

    // Creates a list of all reachable neighbors of given x and y, then Shuffles and returns the list
    private fun getShuffledAvailableDirectionsIndices(x: Int, y: Int): List<Int> {
        // Available neighbors
        val availableWays: MutableList<Int> = mutableListOf()
        // Find unvisited neighbors
        for ((index, direction) in directions.withIndex()) {
            val newX = x + direction[0]
            val newY = y + direction[1]
            if (inside(newX, newY) && !maze[newX][newY]) {
                availableWays.add(index)
            }
        }
        return availableWays.shuffled()
    }

    // Returns a random available index from directions list, -1 otherwise
    private fun getRandomDirection(x: Int, y: Int): Int {
        // Get all available neighbors that we can reach from the current x and y position
        val availableWays = getShuffledAvailableDirectionsIndices(x, y)
        // Get random direction
        return if (availableWays.isNotEmpty()) availableWays.random() else -1
    }

    // Checks if x and y position is inside the maze
    private fun inside(x: Int, y: Int): Boolean {
        if (x <= 0 || x >= M-1 || y <= 0 || y >= N-1) {
            return false
        }
        return true
    }
}