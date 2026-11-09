package io.github.gulliby.windowgate

/** The version, the limits, the usage text and the list of commands. */
object Registry {
    const val VERSION = "0.1.0"
    const val MAX_LINES = 40
    const val MAX_TIME = 86399
    const val MAX_TOKENS = 9999
    const val MAX_RPM = 60
    const val MAX_TPM = 9999
    const val DEFAULT_RPM = 10
    const val DEFAULT_TPM = 1000
    const val WINDOW = 60
    const val USAGE =
        "usage: windowgate check [--rpm N] [--tpm N]\n" +
            "       windowgate plan [--rpm N] [--tpm N]\n" +
            "       windowgate waits [--rpm N] [--tpm N]\n" +
            "       windowgate stats [--rpm N] [--tpm N]\n"
    val COMMANDS = listOf("check", "plan", "waits", "stats")
}

/** A failure that ends the command: status 1 for the input, 2 for the command line. */
class Failure(val status: Int, override val message: String) : RuntimeException(message)

fun fail(status: Int, message: String): Nothing = throw Failure(status, message)

data class Reply(val status: Int, val out: String, val err: String)

data class Options(val rpm: Int, val tpm: Int)

/** One line of the input: an id, the second it arrives and its tokens. */
data class Request(val id: String, val time: Int, val tokens: Int)
