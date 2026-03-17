# Terminal Buffer

Implementation of a terminal text buffer in Kotlin for JetBrains internship task.

## Requirements

- Kotlin
- Gradle

## Build
```bash
./gradlew build
```

## Run tests
```bash
./gradlew test
```
## Flowchart
``` mermaid
flowchart TD
    A[Shell sends text] --> B[TerminalBuffer.writeText]
    B --> C[Read cursor position]
    C --> D[For each character]
    D --> E[Create Cell with char + current attributes]
    E --> F[Place Cell in Screen at cursor position]
    F --> G[Move cursor right by 1]
    G --> H{Cursor at end of line?}
    H -->|No| D
    H -->|Yes| I{Wrap enabled?}
    I -->|Yes| J[Move cursor to next line]
    J --> K{Screen full?}
    K -->|No| D
    K -->|Yes| L[Push top line to Scrollback]
    L --> D
    I -->|No| M[Stop writing]
```
``` mermaid
classDiagram
    class TextAttributes {
        +foreground: Color
        +background: Color
        +bold: Boolean
        +italic: Boolean
        +underline: Boolean
    }

    class Cell {
        +char: Char?
        +attributes: TextAttributes
        +wide: Boolean
    }

    class Cursor {
        +column: Int
        +row: Int
        +width: Int
        +height: Int
        +move(direction, n)
        +set(column, row)
        +get() Pair
    }

    class Screen {
        +width: Int
        +height: Int
        -grid: Array of Array of Cell
        +getCell(col, row) Cell
        +setCell(col, row, cell)
        +insertEmptyLine()
        +clear()
    }

    class Scrollback {
        +maxSize: Int
        -lines: List of Array of Cell
        +push(line)
        +getLine(index) Array of Cell
        +size() Int
    }

    class TerminalBuffer {
        +width: Int
        +height: Int
        +maxScrollback: Int
        -screen: Screen
        -scrollback: Scrollback
        -cursor: Cursor
        -attributes: TextAttributes
        +writeText(text)
        +insertText(text)
        +fillLine(char)
        +moveCursor(direction, n)
        +clearScreen()
        +clearAll()
        +getLine(index) String
        +getScreenContent() String
        +getAllContent() String
    }

    TerminalBuffer --> Screen
    TerminalBuffer --> Scrollback
    TerminalBuffer --> Cursor
    TerminalBuffer --> TextAttributes
    Screen --> Cell
    Scrollback --> Cell
    Cell --> TextAttributes
```

## TODO

### Setup
- [x] Project structure with Kotlin + Gradle
- [ ] Cell data class (character, foreground, background, style flags)
- [ ] TerminalBuffer class with width, height, scrollback

### Cursor
- [ ] Get/set cursor position
- [ ] Move cursor up, down, left, right by N cells
- [ ] Cursor bounds checking

### Attributes
- [ ] Set foreground color
- [ ] Set background color
- [ ] Set style flags (bold, italic, underline)

### Editing
- [ ] Write text at cursor position
- [ ] Insert text with wrapping
- [ ] Fill line with character
- [ ] Insert empty line at bottom
- [ ] Clear screen
- [ ] Clear screen and scrollback

### Content Access
- [ ] Get character at position (screen and scrollback)
- [ ] Get attributes at position (screen and scrollback)
- [ ] Get line as string
- [ ] Get entire screen as string
- [ ] Get screen + scrollback as string

### Tests
- [ ] Basic operations
- [ ] Edge cases and boundary conditions

### Bonus
- [ ] Wide characters (CJK, emoji)
- [ ] Resize