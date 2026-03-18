fun main() {
    val buffer = TerminalBuffer(width = 40, height = 10, maxScrollback = 100)

    // Write a title with red bold attributes
    buffer.setAttributes(TextAttributes(foreground = Color.RED, bold = true))
    buffer.writeText("=== Terminal Buffer Demo ===")

    // Normal text
    buffer.setAttributes(TextAttributes())
    buffer.setCursor(0, 2)
    buffer.writeText("Default text line")

    // Green text
    buffer.setAttributes(TextAttributes(foreground = Color.GREEN))
    buffer.setCursor(0, 3)
    buffer.writeText("Green text line")

    // Blue bold
    buffer.setAttributes(TextAttributes(foreground = Color.BLUE, bold = true))
    buffer.setCursor(0, 4)
    buffer.writeText("Blue bold text")

    // Fill a line with dashes
    buffer.setAttributes(TextAttributes())
    buffer.setCursor(0, 6)
    buffer.fillLine('-')

    // Insert wrapping text
    buffer.setCursor(0, 7)
    buffer.setAttributes(TextAttributes(foreground = Color.YELLOW))
    buffer.insertText("This text is long enough to wrap around the screen!")

    println("=== SCREEN CONTENT ===")
    println(buffer.getScreenContent())

    println("\n=== SCROLLBACK TEST ===")
    repeat(5) { i ->
        buffer.setAttributes(TextAttributes())
        buffer.insertEmptyLine()
        buffer.setCursor(0, 9)
        buffer.writeText("Scrollback line $i")
    }
    println(buffer.getAllContent())
}