package io.github.gulliby.windowgate

fun readsStdin(args: List<String>): Boolean {
    if (args.isEmpty() || args[0] !in Registry.COMMANDS) return false
    return try {
        parseOptions(args.drop(1))
        true
    } catch (_: Failure) {
        false
    }
}

fun run(args: List<String>, input: String): Reply {
    if (args.isEmpty()) return Reply(2, "", Registry.USAGE)

    val command = args[0]
    if (command == "help" || command == "-h" || command == "--help") {
        return Reply(0, Registry.USAGE, "")
    }
    if (command == "--version") {
        return Reply(0, "windowgate ${Registry.VERSION}\n", "")
    }
    if (command !in Registry.COMMANDS) {
        return Reply(2, "", "windowgate: unknown command $command\n")
    }

    return try {
        val options = parseOptions(args.drop(1))
        when (command) {
            "check" -> checkCommand(options, input)
            "plan" -> planCommand(options, input)
            "waits" -> waitsCommand(options, input)
            "stats" -> statsCommand(options, input)
            else -> error("unreachable command")
        }
    } catch (failure: Failure) {
        Reply(failure.status, "", "windowgate: ${failure.message}\n")
    }
}

private fun parseOptions(words: List<String>): Options {
    var rpm = Registry.DEFAULT_RPM
    var tpm = Registry.DEFAULT_TPM
    var sawRpm = false
    var sawTpm = false
    var index = 0

    while (index < words.size) {
        val word = words[index]
        if (word != "--rpm" && word != "--tpm") {
            if (word.isEmpty() || word == "-" || !word.startsWith("-")) {
                fail(2, "unexpected argument $word")
            }
            fail(2, "unknown option $word")
        }

        if (word == "--rpm") {
            if (sawRpm) fail(2, "given twice: --rpm")
            if (index + 1 >= words.size) fail(2, "missing value for --rpm")
            val value = parseOptionValue(words[index + 1], 1, Registry.MAX_RPM)
                ?: fail(2, "bad value for --rpm")
            rpm = value
            sawRpm = true
        } else {
            if (sawTpm) fail(2, "given twice: --tpm")
            if (index + 1 >= words.size) fail(2, "missing value for --tpm")
            val value = parseOptionValue(words[index + 1], 1, Registry.MAX_TPM)
                ?: fail(2, "bad value for --tpm")
            tpm = value
            sawTpm = true
        }
        index += 2
    }

    return Options(rpm, tpm)
}

private fun parseOptionValue(word: String, minimum: Int, maximum: Int): Int? {
    if (word.isEmpty() || (word.length > 1 && word[0] == '0') ||
        word.any { it !in '0'..'9' }
    ) {
        return null
    }
    val value = word.toIntOrNull() ?: return null
    return value.takeIf { it in minimum..maximum }
}
