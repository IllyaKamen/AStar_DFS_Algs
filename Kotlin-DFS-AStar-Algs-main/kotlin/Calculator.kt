import kotlin.random.Random

class Calculator {
    // Create a grid
    val grid = Grid(10, 10)
    // Create DFS Algorithm
    val dfsObject = DFS(
        0,
        Random.nextInt(1, grid.cols - 1),
        ((3..(grid.rows - 1) / 3).random() * 3),
        Random.nextInt(1, grid.cols - 1),
        grid
    )
    fun calculateAverageSteps (amount: Int): Int{

        var stepNum = 0
        var total = 0
        // Run Iterative DFS Algorithm
        dfsObject.dfsIteration()
        while (stepNum != amount) {

            if (dfsObject.fromEndToStartStepsCounter != 0){
                total += dfsObject.totalStepsCounter
                stepNum++
            }
        }
        return total / amount
    }

    fun calculateAverageDeadEnds (amount: Int): Int{
        var stepNum = 0
        var total = 0
        while (stepNum != amount) {
            // Run Iterative DFS Algorithm
            dfsObject.dfsIteration()
            if (dfsObject.fromEndToStartStepsCounter != 0){
                total += dfsObject.deadEndCounter
                stepNum++
            }
        }
        return total / amount
    }

    fun calculateAverageStates (amount: Int): Int{
        var stepNum = 0
        var total = 0
        while (stepNum != amount) {
            // Run Iterative DFS Algorithm
            dfsObject.dfsIteration()
            if (dfsObject.fromEndToStartStepsCounter != 0){
                total += dfsObject.totalStepsCounter+1
                stepNum++
            }
        }
        return total / amount
    }

    fun calculateAverageSavedStates (amount: Int): Int{
        var stepNum = 0
        var total = 0
        while (stepNum != amount) {
            // Run Iterative DFS Algorithm
            dfsObject.dfsIteration()
            if (dfsObject.fromEndToStartStepsCounter != 0){
                total += dfsObject.fromEndToStartStepsCounter
                stepNum++
            }
        }
        return total / amount
    }

}