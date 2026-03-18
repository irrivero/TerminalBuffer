import kotlin.test.*

class TerminalBufferTest {

    private fun createBuffer() = TerminalBuffer(width = 80, height = 24, maxScrollback = 100)

    @Test
    fun `cursor starts at 0,0`() {
        val buffer = createBuffer()
        assertEquals(0, buffer.getCursorColumn())
        assertEquals(0, buffer.getCursorRow())
    }

    @Test
    fun `cursor moves right`() {
        val buffer = createBuffer()
        buffer.moveCursorRight(3)
        assertEquals(3, buffer.getCursorColumn())
    }

    @Test
    fun `cursor does not go beyond right edge`() {
        val buffer = createBuffer()
        buffer.moveCursorRight(100)
        assertEquals(79, buffer.getCursorColumn())
    }

    @Test
    fun `cursor does not go beyond left edge`() {
        val buffer = createBuffer()
        buffer.moveCursorLeft(10)
        assertEquals(0, buffer.getCursorColumn())
    }

    @Test
    fun `cursor does not go beyond bottom edge`() {
        val buffer = createBuffer()
        buffer.moveCursorDown(100)
        assertEquals(23, buffer.getCursorRow())
    }

    @Test
    fun `write text appears on screen`() {
        val buffer = createBuffer()
        buffer.writeText("Hello")
        assertEquals("Hello", buffer.getLine(0).trim())
    }

    @Test
    fun `write text moves cursor`() {
        val buffer = createBuffer()
        buffer.writeText("Hello")
        assertEquals(5, buffer.getCursorColumn())
    }

    @Test
    fun `clear screen removes all content`() {
        val buffer = createBuffer()
        buffer.writeText("Hello")
        buffer.clearScreen()
        assertEquals("", buffer.getLine(0).trim())
    }

    @Test
    fun `fill line fills entire row`() {
        val buffer = createBuffer()
        buffer.fillLine('X')
        assertEquals("X".repeat(80), buffer.getLine(0))
    }

    @Test
    fun `insert empty line pushes content to scrollback`() {
        val buffer = createBuffer()
        buffer.writeText("Hello")
        buffer.insertEmptyLine()
        assertEquals("Hello", buffer.getLine(-1).trim())
    }

    @Test
    fun `attributes are applied to written text`() {
        val buffer = createBuffer()
        val attrs = TextAttributes(foreground = Color.RED, bold = true)
        buffer.setAttributes(attrs)
        buffer.writeText("A")
        val cell = buffer.getCell(0, 0)
        assertEquals(Color.RED, cell.attributes.foreground)
        assertEquals(true, cell.attributes.bold)
    }

    @Test
    fun `clear all removes screen and scrollback`() {
        val buffer = createBuffer()
        buffer.writeText("Hello")
        buffer.insertEmptyLine()
        buffer.clearAll()
        assertEquals("", buffer.getLine(0).trim())
    }
}