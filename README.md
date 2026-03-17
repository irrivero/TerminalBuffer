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

## TODO

### Setup
- [ ] Project structure with Kotlin + Gradle
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