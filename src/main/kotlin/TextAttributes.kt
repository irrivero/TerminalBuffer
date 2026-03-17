enum class Color {
    DEFAULT,
    BLACK, RED, GREEN, YELLOW,
    BLUE, MAGENTA, CYAN, WHITE,
    BRIGHT_BLACK, BRIGHT_RED, BRIGHT_GREEN, BRIGHT_YELLOW,
    BRIGHT_BLUE, BRIGHT_MAGENTA, BRIGHT_CYAN, BRIGHT_WHITE
}

data class TextAttributes(
    val foreground: Color = Color.DEFAULT,
    val background: Color = Color.DEFAULT,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val underline: Boolean = false
)