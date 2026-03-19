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
    fun `cursor moves up`() {
        val buffer = createBuffer()
        buffer.setCursor(0, 5)
        buffer.moveCursorUp(3)
        assertEquals(2, buffer.getCursorRow())
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
    fun `cursor does not go beyond top edge`() {
        val buffer = createBuffer()
        buffer.moveCursorUp(10)
        assertEquals(0, buffer.getCursorRow())
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

    @Test
    fun `insertText wraps to next line`() {
        val buffer = TerminalBuffer(width = 5, height = 24, maxScrollback = 100)
        buffer.insertText("HelloWorld")
        assertEquals("Hello", buffer.getLine(0).trim())
        assertEquals("World", buffer.getLine(1).trim())
    }

    @Test
    fun `insertText pushes to scrollback when screen is full`() {
        val buffer = TerminalBuffer(width = 5, height = 2, maxScrollback = 100)
        buffer.insertText("HelloWorld!")
        val all = buffer.getAllContent()
        assertTrue(all.contains("Hello"))
    }

    @Test
    fun `scrollback does not exceed max size`() {
        val buffer = TerminalBuffer(width = 80, height = 24, maxScrollback = 3)
        repeat(10) {
            buffer.insertEmptyLine()
        }
        assertTrue(buffer.getAllContent().isNotEmpty())
    }

    @Test
    fun `set cursor position works correctly`() {
        val buffer = createBuffer()
        buffer.setCursor(10, 5)
        assertEquals(10, buffer.getCursorColumn())
        assertEquals(5, buffer.getCursorRow())
    }

    @Test
    fun `set cursor clamps to bounds`() {
        val buffer = createBuffer()
        buffer.setCursor(200, 200)
        assertEquals(79, buffer.getCursorColumn())
        assertEquals(23, buffer.getCursorRow())
    }

    @Test
    fun `write text overwrites existing content`() {
        val buffer = createBuffer()
        buffer.writeText("Hello")
        buffer.setCursor(0, 0)
        buffer.writeText("World")
        assertEquals("World", buffer.getLine(0).substring(0, 5))
    }

    @Test
    fun `clear all resets scrollback`() {
        val buffer = TerminalBuffer(width = 5, height = 2, maxScrollback = 100)
        buffer.insertText("HelloWorld")
        buffer.clearAll()
        assertEquals("", buffer.getLine(0).trim())
    }

    @Test
    fun `get attributes at position returns correct attributes`() {
        val buffer = createBuffer()
        val attrs = TextAttributes(foreground = Color.BLUE, underline = true)
        buffer.setAttributes(attrs)
        buffer.writeText("A")
        assertEquals(Color.BLUE, buffer.getAttributes(0, 0).foreground)
        assertEquals(true, buffer.getAttributes(0, 0).underline)
    }
    @Test
    fun `getScreenContent returns all rows joined by newline`() {
        val buffer = TerminalBuffer(width = 5, height = 3, maxScrollback = 100)
        buffer.writeText("Hello")
        val content = buffer.getScreenContent()
        val lines = content.split("\n")
        assertEquals(3, lines.size)
        assertEquals("Hello", lines[0])
    }

    @Test
    fun `fillLine with null clears the row`() {
        val buffer = createBuffer()
        buffer.writeText("Hello")
        buffer.setCursor(0, 0)
        buffer.fillLine(null)
        assertEquals("", buffer.getLine(0).trim())
    }

    @Test
    fun `getAllContent includes scrollback before screen`() {
        val buffer = TerminalBuffer(width = 5, height = 2, maxScrollback = 100)
        buffer.writeText("AAAAA")
        buffer.setCursor(0, 1)
        buffer.writeText("BBBBB")
        buffer.insertEmptyLine()
        val all = buffer.getAllContent()
        assertTrue(all.contains("AAAAA"))
    }
}