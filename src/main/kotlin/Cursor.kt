class Cursor(private val width: Int, private val height: Int) {
    var column: Int = 0
        private set
    var row: Int = 0
        private set

    fun set(column: Int, row: Int) {
        this.column = column.coerceIn(0, width - 1)
        this.row = row.coerceIn(0, height - 1)
    }

    fun moveRight(n: Int = 1) { set(column + n, row) }
    fun moveLeft(n: Int = 1) { set(column - n, row) }
    fun moveDown(n: Int = 1) { set(column, row + n) }
    fun moveUp(n: Int = 1) { set(column, row - n) }
}