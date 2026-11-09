package io.github.gulliby.windowgate

import kotlin.test.Test

class CliRowsTest {
    @Test
    fun rows() {
        checkRows(rows)
    }
}

private val rows =
    listOf(
        row(emptyList(), "", 2, "", "usage: windowgate check [--rpm N] [--tpm N]\n       windowgate plan [--rpm N] [--tpm N]\n       windowgate waits [--rpm N] [--tpm N]\n       windowgate stats [--rpm N] [--tpm N]\n"),
        row(listOf("help"), "", 0, "usage: windowgate check [--rpm N] [--tpm N]\n       windowgate plan [--rpm N] [--tpm N]\n       windowgate waits [--rpm N] [--tpm N]\n       windowgate stats [--rpm N] [--tpm N]\n", ""),
        row(listOf("-h", "extra"), "", 0, "usage: windowgate check [--rpm N] [--tpm N]\n       windowgate plan [--rpm N] [--tpm N]\n       windowgate waits [--rpm N] [--tpm N]\n       windowgate stats [--rpm N] [--tpm N]\n", ""),
        row(listOf("--help", "--bogus"), "", 0, "usage: windowgate check [--rpm N] [--tpm N]\n       windowgate plan [--rpm N] [--tpm N]\n       windowgate waits [--rpm N] [--tpm N]\n       windowgate stats [--rpm N] [--tpm N]\n", ""),
        row(listOf("--version"), "", 0, "windowgate 0.1.0\n", ""),
        row(listOf("--version", "x"), "", 0, "windowgate 0.1.0\n", ""),
        row(listOf("frob"), "", 2, "", "windowgate: unknown command frob\n"),
        row(listOf(""), "", 2, "", "windowgate: unknown command \n"),
        row(listOf("Plan"), "", 2, "", "windowgate: unknown command Plan\n"),
        row(listOf("--rpm"), "", 2, "", "windowgate: unknown command --rpm\n"),
        row(listOf("--tpm", "5"), "", 2, "", "windowgate: unknown command --tpm\n"),
        row(listOf("help", "plan"), "", 0, "usage: windowgate check [--rpm N] [--tpm N]\n       windowgate plan [--rpm N] [--tpm N]\n       windowgate waits [--rpm N] [--tpm N]\n       windowgate stats [--rpm N] [--tpm N]\n", ""),
        row(listOf("plan"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "a1 arrive 0 send 0 wait 0\na2 arrive 5 send 5 wait 0\na3 arrive 10 send 10 wait 0\nlast send 10\n", ""),
        row(listOf("check"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "ok 3 requests\n", ""),
    )
