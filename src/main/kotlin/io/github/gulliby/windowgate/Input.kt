package io.github.gulliby.windowgate

fun readRequests(input: String, options: Options): List<Request> {
    val lines = mutableListOf<String>()
    if (input.isNotEmpty()) {
        var start = 0
        for (index in input.indices) {
            if (input[index] == '\n') {
                lines.add(input.substring(start, index))
                start = index + 1
            }
        }
        if (start < input.length) {
            lines.add(input.substring(start))
        }
    }
    if (lines.size > Registry.MAX_LINES) {
        fail(1, "input is too long (max ${Registry.MAX_LINES} lines)")
    }

    val requests = mutableListOf<Request>()
    val ids = mutableSetOf<String>()
    var previousTime: Int? = null

    for ((lineIndex, rawLine) in lines.withIndex()) {
        val line = if (rawLine.endsWith('\r')) rawLine.dropLast(1) else rawLine
        val trimmed = line.trim(' ', '\t')
        if (trimmed.isEmpty()) continue

        val words = trimmed.split(Regex("[ \\t]+"))
        if (words.first().startsWith("#")) continue
        if (words.size != 3) {
            inputFailure(lineIndex, "expected ID TIME TOKENS")
        }

        val id = words[0]
        if (!id.matches(Regex("[a-z][a-z0-9_]{0,7}"))) {
            inputFailure(lineIndex, "bad id $id")
        }

        val time = parseCanonicalInt(words[1], 0, Registry.MAX_TIME)
        if (time == null) {
            inputFailure(lineIndex, "bad time ${words[1]}")
        }

        val tokens = parseCanonicalInt(words[2], 1, Registry.MAX_TOKENS)
        if (tokens == null) {
            inputFailure(lineIndex, "bad tokens ${words[2]}")
        }

        if (id in ids) {
            inputFailure(lineIndex, "duplicate id $id")
        }
        if (previousTime != null && time < previousTime) {
            inputFailure(lineIndex, "time goes back")
        }
        if (tokens > options.tpm) {
            inputFailure(lineIndex, "tokens exceed --tpm")
        }

        requests.add(Request(id, time, tokens))
        ids.add(id)
        previousTime = time
    }
    return requests
}

private fun inputFailure(lineIndex: Int, reason: String): Nothing =
    fail(1, "line ${lineIndex + 1}: $reason")

private fun parseCanonicalInt(word: String, minimum: Int, maximum: Int): Int? {
    if (word.isEmpty() || (word.length > 1 && word[0] == '0') ||
        word.any { it !in '0'..'9' }
    ) {
        return null
    }
    val value = word.toIntOrNull() ?: return null
    return value.takeIf { it in minimum..maximum }
}
