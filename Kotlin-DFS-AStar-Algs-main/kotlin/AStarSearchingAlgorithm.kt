import java.util.*
import javax.swing.text.html.HTML.Attribute.N
import kotlin.math.abs
import kotlin.random.Random

class AStarSearchingAlgorithm(private var startX: Int, private var startY: Int, private var endX: Int, private var endY: Int, private val gridObject: Grid){

    val startNode = Node(startX, startY)
    val wayList = mutableListOf(startNode)

    // 0-Right, 1-Left, 2-Down, 3-Up
    private val directions: List<List<Int>> = listOf(
        listOf(0, 2),
        listOf(0, -2),
        listOf(2, 0),
        listOf(-2, 0)
    )

    // Number of rows
    private val M = gridObject.rows

    // Number of columns
    private val N = gridObject.cols

    val maze = gridObject.grid

    var totalStepsCounter = 0
    var deadEndCounter = 0
    var fromEndToStartStepsCounter = 0
    var pushTrigger = false

    fun aStar() {
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
            val way = getBestWayIndex(cur[0], cur[1])

            // AS long as we have a way and we didn't exceed limit
            if (way != -1) {
                // Get new position
                val newX = cur[0] + directions[way][0]
                val newY = cur[1] + directions[way][1]
                // check if we have the way been built
                if (!maze[newX][newY]) {
                    // Remove the walls between current and new cell
                    wallRemover(way, newX, newY)
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

    private fun wallRemover(index: Int, newX: Int, newY: Int) {
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
    fun getBestWayIndex(x: Int, y: Int): Int{
        // Get all nodes with calculated values
        val availableNodes = calcNodesCost(x, y)
        // Get index of node with lowest f(x) value if nodes exists
        return if (availableNodes.isNotEmpty()) availableNodes.indexOf(availableNodes.minBy { it.fValue })  else -1
    }

    // Calculates f(x), h(x), g(x) for a list of all reachable neighbors of given x and y
    fun calcNodesCost(x: Int, y: Int): List<Node>{
        // Get available neighbors
        val availableNodes = getAvailableNodes(x, y)
        // Calc functions for each node in list
        for ((index) in availableNodes.withIndex()){
            availableNodes[index].gValue = Random.nextInt(1,30)
            availableNodes[index].hValue = abs(x - availableNodes[index].x) + abs(y - availableNodes[index].y)
            availableNodes[index].fValue = availableNodes[index].gValue + availableNodes[index].hValue
//            println(availableNodes[index].fValue)
        }
        return  availableNodes
    }
    // Creates and returns a list of all reachable neighbors of given x and y
    fun getAvailableNodes(x: Int, y: Int): List<Node> {
        // Available neighbors
        val availableWays: MutableList<Node> = mutableListOf()
        // Find unvisited neighbors
        for ((index, direction) in directions.withIndex()) {
            val newX = x + direction[0]
            val newY = y + direction[1]
            val newNode = Node(newX, newY)
            if (inside(newX, newY) && !maze[newX][newY]) {
                availableWays.add(newNode)
            }
        }
        return availableWays
    }



    private fun inside(x: Int, y: Int): Boolean {
        if (x <= 1 || x >= M-1 || y <= 1 || y >= N-1) {
            return false
        }
        return true
    }

}