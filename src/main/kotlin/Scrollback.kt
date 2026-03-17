class Scrollback(private val maxSize: Int) {
    private val lines: ArrayDeque<Array<Cell>> = ArrayDeque()

    fun push(line: Array<Cell>) {
        if (lines.size >= maxSize) lines.removeFirst()
        lines.addLast(line.copyOf())
    }

    fun getLine(index: Int): Array<Cell> = lines[index].copyOf()

    fun size(): Int = lines.size

    fun clear() = lines.clear()
}