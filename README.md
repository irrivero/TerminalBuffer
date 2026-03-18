# Terminal Buffer

Implementation of a terminal text buffer in Kotlin for JetBrains internship task.

## Requirements

- Kotlin 1.9+
- Java 23
- Gradle 8.13

## Build
```bash
./gradlew build
```

## Run tests
```bash
./gradlew test
```

## Architecture
```mermaid
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
        +move(direction, n)
        +set(column, row)
    }
    class Screen {
        +width: Int
        +height: Int
        +getCell(col, row) Cell
        +setCell(col, row, cell)
        +clear()
    }
    class Scrollback {
        +maxSize: Int
        +push(line)
        +getLine(index) Array
        +size() Int
    }
    class TerminalBuffer {
        +width: Int
        +height: Int
        +maxScrollback: Int
        +writeText(text)
        +fillLine(char)
        +clearScreen()
        +getLine(index) String
        +getScreenContent() String
    }
    TerminalBuffer --> Screen
    TerminalBuffer --> Scrollback
    TerminalBuffer --> Cursor
    TerminalBuffer --> TextAttributes
    Screen --> Cell
    Scrollback --> Cell
    Cell --> TextAttributes
```

## Flow
```mermaid
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

## TODO

### Setup
- [x] Project structure with Kotlin + Gradle
- [x] Cell data class (character, foreground, background, style flags)
- [x] TerminalBuffer class with width, height, scrollback

### Cursor
- [x] Get/set cursor position
- [x] Move cursor up, down, left, right by N cells
- [x] Cursor bounds checking

### Attributes
- [x] Set foreground color
- [x] Set background color
- [x] Set style flags (bold, italic, underline)

### Editing
- [x] Write text at cursor position
- [ ] Insert text with wrapping
- [x] Fill line with character
- [x] Insert empty line at bottom
- [x] Clear screen
- [x] Clear screen and scrollback

### Content Access
- [x] Get character at position (screen and scrollback)
- [x] Get attributes at position (screen and scrollback)
- [x] Get line as string
- [x] Get entire screen as string
- [x] Get screen + scrollback as string

### Tests
- [x] Basic operations
- [ ] Edge cases and boundary conditions

### Bonus
- [ ] Wide characters (CJK, emoji)
- [ ] Resize