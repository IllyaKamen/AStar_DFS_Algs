class Node(var x: Int, var y: Int) {
    var nodeX = x
    var nodeY = y
    var hValue = 0
    var gValue = 0
    var fValue = 0
    var parent: Node? = null
}