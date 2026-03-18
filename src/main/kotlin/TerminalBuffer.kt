class TerminalBuffer(
    val width: Int,
    val height: Int,
    val maxScrollback: Int
) {
    private val screen = Screen(width, height)
    private val scrollback = Scrollback(maxScrollback)
    private val cursor = Cursor(width, height)
    private var attributes = TextAttributes()

    fun setAttributes(newAttributes: TextAttributes) {
        attributes = newAttributes
    }

    fun getCursorColumn(): Int = cursor.column
    fun getCursorRow(): Int = cursor.row

    fun setCursor(column: Int, row: Int) = cursor.set(column, row)
    fun moveCursorRight(n: Int = 1) = cursor.moveRight(n)
    fun moveCursorLeft(n: Int = 1) = cursor.moveLeft(n)
    fun moveCursorUp(n: Int = 1) = cursor.moveUp(n)
    fun moveCursorDown(n: Int = 1) = cursor.moveDown(n)

    fun writeText(text: String) {
        for (char in text) {
            screen.setCell(cursor.column, cursor.row, Cell(char, attributes))
            if (cursor.column < width - 1) {
                cursor.moveRight()
            }
        }
    }

    fun fillLine(char: Char?) {
        for (col in 0 until width)
            screen.setCell(col, cursor.row, Cell(char, attributes))
    }

    fun insertEmptyLine() {
        val topLine = screen.getTopLineAndScroll()
        scrollback.push(topLine)
    }

    fun clearScreen() = screen.clear()

    fun clearAll() {
        screen.clear()
        scrollback.clear()
    }

    fun getCell(column: Int, row: Int): Cell {
        return if (row >= 0) screen.getCell(column, row)
        else scrollback.getLine(scrollback.size() + row)[column]
    }

    fun getLine(row: Int): String {
        val line = if (row >= 0) screen.getLine(row)
        else scrollback.getLine(scrollback.size() + row)
        return line.joinToString("") { it.char?.toString() ?: " " }
    }

    fun getScreenContent(): String {
        return (0 until height).joinToString("\n") { getLine(it) }
    }

    fun getAllContent(): String {
        val scrollbackContent = (0 until scrollback.size()).joinToString("\n") {
            scrollback.getLine(it).joinToString("") { cell -> cell.char?.toString() ?: " " }
        }
        return if (scrollbackContent.isEmpty()) getScreenContent()
        else "$scrollbackContent\n${getScreenContent()}"
    }
    fun insertText(text: String) {
        for (char in text) {
            screen.setCell(cursor.column, cursor.row, Cell(char, attributes))
            if (cursor.column < width - 1) {
                cursor.moveRight()
            } else {
                if (cursor.row < height - 1) {
                    cursor.set(0, cursor.row + 1)
                } else {
                    val topLine = screen.getTopLineAndScroll()
                    scrollback.push(topLine)
                    cursor.set(0, cursor.row)
                }
            }
        }
    }
}