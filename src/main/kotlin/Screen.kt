class Screen(val width: Int, val height: Int) {
    private val grid: Array<Array<Cell>> = Array(height) { Array(width) { Cell() } }

    fun getCell(column: Int, row: Int): Cell = grid[row][column]

    fun setCell(column: Int, row: Int, cell: Cell) {
        grid[row][column] = cell
    }

    fun setWideCell(column: Int, row: Int, cell: Cell) {
        grid[row][column] = cell.copy(wide = true)
        if (column + 1 < width) {
            grid[row][column + 1] = Cell(char = null, wide = true)
        }
    }

    fun getLine(row: Int): Array<Cell> = grid[row].copyOf()

    fun clear() {
        for (row in 0 until height)
            for (col in 0 until width)
                grid[row][col] = Cell()
    }

    fun insertEmptyLineAtBottom() {
        for (row in 0 until height - 1)
            grid[row] = grid[row + 1].copyOf()
        grid[height - 1] = Array(width) { Cell() }
    }

    fun getTopLineAndScroll(): Array<Cell> {
        val top = grid[0].copyOf()
        insertEmptyLineAtBottom()
        return top
    }
}