import kotlin.random.Random
import kotlin.reflect.typeOf


fun main() {

    val calculator = Calculator()

//    println( calculator.calculateAverageStates(20))
//    println( calculator.calculateAverageSteps(20))
//    println( calculator.calculateAverageDeadEnds(20))
//    println( calculator.calculateAverageSavedStates(20))

    // Create a grid
    val grid = Grid(16, 16)
    // Create DFS Algorithm
//    val dfsObject = DFS(
//        ((2..(grid.rows - 1) / 2).random() * 2),
//        ((2..(grid.cols - 1) / 2).random() * 2),
//        ((2..(grid.rows - 1) / 2).random() * 2),
//        ((2..(grid.cols - 1) / 2).random() * 2),
//        grid
//    )
//
//    dfsObject.getWay()
//    println( calculator.calculateDFS(20))
//
    val astar = AStarSearchingAlgorithm(
        ((2..(grid.rows - 1) / 2).random() * 2),
        ((2..(grid.cols - 1) / 2).random() * 2),
        ((2..(grid.rows - 1) / 2).random() * 2),
        ((2..(grid.cols - 1) / 2).random() * 2),
        grid
    )
    astar.aStar()
//
//    println(calculator.calculateAll(20))
}