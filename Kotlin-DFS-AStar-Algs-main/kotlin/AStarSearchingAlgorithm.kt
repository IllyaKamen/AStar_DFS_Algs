import java.util.*
import kotlin.math.abs
import kotlin.random.Random

class AStarSearchingAlgorithm(private var startX: Int,
                              private var startY: Int,
                              private var endX: Int,
                              private var endY: Int,
                              private val gridObject: Grid){

    val bestWay: MutableList<Node> = mutableListOf()


    // 0-Right, 1-Left, 2-Down, 3-Up
    private val directions: List<List<Int>> = listOf(
        listOf(0, 2),
        listOf(0, -2),
        listOf(2, 0),
        listOf(-2, 0)
    )

    private val M = gridObject.rows

    private val N = gridObject.cols

    val maze = gridObject.grid

    // values for calculating averages
    var totalStepsCounter = 0
    var deadEndCounter = 0
    var fromEndToStartStepsCounter = 0
    // create trigger to check dead ends
    var pushTrigger = false

    fun aStar() {
        val totalForwardCost = search(startX, startY, endX, endY, maze)
        val newMaze = gridObject.grid
        val totalBackwardCost = search(endX, endY, startX, startY, newMaze)
        when {
            totalForwardCost>totalBackwardCost && totalForwardCost != -1 -> {
                println("The best way was build from start till end")
                search(startX, startY, endX, endY, maze)
            }
            totalForwardCost == totalBackwardCost && totalForwardCost != -1 -> {
                println("Best ways are equal")
            }
            totalForwardCost<totalBackwardCost && totalForwardCost != -1 -> {
                println("The best way was build from end till start")
            }
            else -> {
                println("Way wasn't found")
                return
            }
        }

        val outMaze = gridObject.grid
        for ((index, cor) in bestWay.withIndex()){
            outMaze[cor.nodeX][cor.nodeY] = true
        }
        gridObject.printGrid()
        bestWay.clear()

    }

    fun search(startX: Int, startY: Int, endX: Int, endY: Int, maze: Array<Array<Boolean>>): Int{

        var totalCost = 0
        bestWay.add(Node(startX, startY, 0))
        val stack = Stack<List<Int>>()

        stack.push(listOf(startX, startY))

        maze[startX][startY] = true

        gridObject.printGrid()

        var endPoint = false

        // As long as we have an element in the Stack or we don't get to end point
        while (!stack.empty() && !endPoint) {
            val cur = stack.peek()

            val way = getBestWayIndex(cur[0], cur[1])

            if (way != null) {
                // Get new position
                val newX = way.nodeX
                val newY = way.nodeY

                if (!maze[newX][newY]) {
                    wallRemover(way?.direction, newX, newY)

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
                    return totalCost
                }
                pushTrigger = true
                totalCost += Node(newX, newY, null).fValue
                bestWay.add(Node(newX, newY, null))
            }
            else {
                totalCost -= Node(cur[0], cur[1], null).fValue
                bestWay.remove(Node(cur[0], cur[1], null))
                stack.pop()
                if (pushTrigger) {
                    deadEndCounter++
                    pushTrigger = false
                }
            }
        }
        return -1
    }

    private fun wallRemover(index: Int?, newX: Int, newY: Int) {
        // 0-Right, 1-Left, 2-Down, 3-Up
        when (index) {
            0 -> {
                maze[newX][newY - 1] = true
            }
            1 ->  {
                maze[newX][newY + 1] = true
            }
            2 ->  {
                maze[newX - 1][newY] = true
            }
            3 ->  {
                maze[newX + 1][newY] = true
            }
        }
    }

    // Calculates f(x), h(x), g(x) for a list of all reachable neighbors of given x and y
    fun getBestWayIndex(x: Int, y: Int): Node?{
        val availableNodes = calcNodesCost(x, y)

        return if (availableNodes.isNotEmpty()) availableNodes.minBy { it.fValue }  else null
    }

    // Calculates f(x), h(x), g(x) for a list of all reachable neighbors of given x and y
    fun calcNodesCost(x: Int, y: Int): List<Node>{
        val availableNodes = getAvailableNodes(x, y)
        for ((index) in availableNodes.withIndex()){
            availableNodes[index].gValue = Random.nextInt(1, 10)
            availableNodes[index].hValue = abs(x - availableNodes[index].x) + abs(y - availableNodes[index].y)
            availableNodes[index].fValue = availableNodes[index].gValue + availableNodes[index].hValue
//            println(availableNodes[index].fValue)
        }
        return  availableNodes
    }
    // Creates and returns a list of all reachable neighbors of given x and y
    fun getAvailableNodes(x: Int, y: Int): List<Node> {
        val availableWays: MutableList<Node> = mutableListOf()
        for ((index, direction) in directions.withIndex()) {
            val newX = x + direction[0]
            val newY = y + direction[1]
            if (inside(newX, newY) && !maze[newX][newY]) {
                availableWays.add(Node(newX, newY, index))
            }
        }
        return availableWays
    }

    private fun inside(x: Int, y: Int): Boolean {
        if (x <= 0 || x >= M-1 || y <= 0 || y >= N-1) {
            return false
        }
        return true
    }

}