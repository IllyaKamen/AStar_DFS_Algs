import java.util.*

class DFS(private var startX: Int,
          private var startY: Int,
          private var endX: Int,
          private var endY: Int,
          private val gridObject: Grid) {

    private val M = gridObject.rows

    private val N = gridObject.cols

    private val maze = gridObject.grid

    var wayList: MutableList<Node> = mutableListOf()

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

    fun getWay(){

        val totalForwardCost = dfsIteration(startX, startY)

        val outMaze = gridObject.grid
        for ((index, cor) in wayList.withIndex()){
            outMaze[cor.nodeX][cor.nodeY] = true
        }
        println("The way from first node to end is:")
        gridObject.printGrid()
        wayList.clear()
    }

    // Iterative DFS Algorithm
    fun dfsIteration(startX: Int, startY: Int) {
        wayList.add(Node(startX, startY, 0))
        val stack = Stack<List<Int>>()
        stack.push(listOf(startX, startY))
        var endPoint = false
        maze[startX][startY] = true
        gridObject.printGrid()


        while (!stack.empty() && !endPoint) {
            val cur = stack.peek()
            val randomWay = getRandomDirection(cur[0], cur[1])

            if (randomWay != -1) {
                val newX = cur[0] + directions[randomWay][0]
                val newY = cur[1] + directions[randomWay][1]

                if (!maze[newX][newY]) {
                    wallRemover(randomWay, newX, newY)
                    maze[newX][newY] = true
                    gridObject.printGrid()
                    stack.push(listOf(newX, newY))

                    totalStepsCounter++
                }

                if (maze[newX][newY] == maze[endX][endY]){
                    endPoint = true
                    println("Got to end point")
                    while (!stack.empty()) {

                        fromEndToStartStepsCounter++
                        stack.pop()

                    }
                }
                wayList.add(Node(newX, newY, null))
                pushTrigger = true
            }
            else {
                stack.pop()
                wayList.removeLast()
                if (pushTrigger) {
                    deadEndCounter++
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
        val availableWays: MutableList<Int> = mutableListOf()
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
        val availableWays = getShuffledAvailableDirectionsIndices(x, y)
        // Get random direction
        return if (availableWays.isNotEmpty()) availableWays.random() else -1
    }

    private fun inside(x: Int, y: Int): Boolean {
        if (x <= 0 || x >= M-1 || y <= 0 || y >= N-1) {
            return false
        }
        return true
    }
}

