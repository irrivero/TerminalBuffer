fun main() {
    val buffer = TerminalBuffer(width = 40, height = 10, maxScrollback = 100)
    buffer.writeText("Hello from TerminalBuffer!")
    buffer.setCursor(0, 1)
    buffer.writeText("Second line here")
    println(buffer.getScreenContent())
}