import kotlin.random.Random

class Calculator() {
    // Create a grid

    // Create DFS Algorithm

    fun calculateAll (amount: Int): List<Int>{
        val resultList = mutableListOf<Int>()

        var stepNum = 0
        var totalSteps = 0
        var totalDeadends = 0
        var totalStates = 0
        var totalActiveStates = 0

        // Run Iterative DFS Algorithm
        while (stepNum != amount) {
            val grid = Grid(16, 16)
            val dfsObject = DFS(
                ((2..(grid.rows - 1) / 2).random() * 2),
                ((2..(grid.cols - 1) / 2).random() * 2),
                ((2..(grid.rows - 1) / 2).random() * 2),
                ((2..(grid.cols - 1) / 2).random() * 2),
                grid
            )
            dfsObject.getWay()
            totalSteps += dfsObject.totalStepsCounter
            totalDeadends += dfsObject.deadEndCounter
            totalStates += dfsObject.totalStepsCounter+1
            totalActiveStates += dfsObject.fromEndToStartStepsCounter
            stepNum++
        }

//        while (stepNum != amount) {
//            val grid = Grid(16, 16)
//            // Create A* Algorithm
//            val astarObject = AStarSearchingAlgorithm(
//                ((2..(grid.rows - 1) / 2).random() * 2),
//                ((2..(grid.cols - 1) / 2).random() * 2),
//                ((2..(grid.rows - 1) / 2).random() * 2),
//                ((2..(grid.cols - 1) / 2).random() * 2),
//                grid
//            )
//            astarObject.aStar()
//            totalSteps += astarObject.totalStepsCounter
//            totalDeadends += astarObject.deadEndCounter
//            totalStates += astarObject.totalStepsCounter+1
//            totalActiveStates += astarObject.fromEndToStartStepsCounter
//            stepNum++
//        }
        resultList.add(totalSteps / amount)
        resultList.add(totalDeadends / amount)
        resultList.add(totalStates / amount)
        resultList.add(totalActiveStates / amount)
        return resultList
    }
//    fun calculateAverageSteps (amount: Int): Int{
//
//        var stepNum = 0
//        var total = 0
//        // Run Iterative DFS Algorithm
//        while (stepNum != amount) {
//            dfsObject.dfsIteration()
//            total += dfsObject.totalStepsCounter
//            stepNum++
//        }
//        return total / amount
//    }
//
//    fun calculateAverageDeadEnds (amount: Int): Int{
//        var stepNum = 0
//        var total = 0
//        while (stepNum != amount) {
//            // Run Iterative DFS Algorithm
//            dfsObject.dfsIteration()
//            total += dfsObject.deadEndCounter
//            stepNum++
//        }
//        return total / amount
//    }
//
//    fun calculateAverageStates (amount: Int): Int{
//        var stepNum = 0
//        var total = 0
//        while (stepNum != amount) {
//            // Run Iterative DFS Algorithm
//            dfsObject.dfsIteration()
//            total += dfsObject.totalStepsCounter+1
//            stepNum++
//            }
//        return total / amount
//    }
//
//    fun calculateAverageSavedStates (amount: Int): Int{
//        var stepNum = 0
//        var total = 0
//        while (stepNum != amount) {
//            // Run Iterative DFS Algorithm
//            dfsObject.dfsIteration()
//            total += dfsObject.fromEndToStartStepsCounter
//            stepNum++
//        }
//        return total / amount
//    }

}