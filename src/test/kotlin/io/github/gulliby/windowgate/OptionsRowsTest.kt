package io.github.gulliby.windowgate

import kotlin.test.Test

class OptionsRowsTest {
    @Test
    fun rows() {
        checkRows(rows)
    }
}

private val rows =
    listOf(
        row(listOf("check", "x"), "", 2, "", "windowgate: unexpected argument x\n"),
        row(listOf("check", ""), "", 2, "", "windowgate: unexpected argument \n"),
        row(listOf("plan", "-"), "", 2, "", "windowgate: unexpected argument -\n"),
        row(listOf("check", "--nope"), "", 2, "", "windowgate: unknown option --nope\n"),
        row(listOf("plan", "--rpm"), "", 2, "", "windowgate: missing value for --rpm\n"),
        row(listOf("plan", "--rpm", "0"), "", 2, "", "windowgate: bad value for --rpm\n"),
        row(listOf("plan", "--rpm", "007"), "", 2, "", "windowgate: bad value for --rpm\n"),
        row(listOf("plan", "--rpm", "61"), "", 2, "", "windowgate: bad value for --rpm\n"),
        row(listOf("plan", "--rpm", "60"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "a1 arrive 0 send 0 wait 0\na2 arrive 5 send 5 wait 0\na3 arrive 10 send 10 wait 0\nlast send 10\n", ""),
        row(listOf("plan", "--tpm", "0"), "", 2, "", "windowgate: bad value for --tpm\n"),
        row(listOf("plan", "--tpm", "10000"), "", 2, "", "windowgate: bad value for --tpm\n"),
        row(listOf("plan", "--tpm", "9999"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "a1 arrive 0 send 0 wait 0\na2 arrive 5 send 5 wait 0\na3 arrive 10 send 10 wait 0\nlast send 10\n", ""),
        row(listOf("plan", "--rpm", "x"), "", 2, "", "windowgate: bad value for --rpm\n"),
        row(listOf("plan", "--tpm", "-5"), "", 2, "", "windowgate: bad value for --tpm\n"),
        row(listOf("plan", "--rpm", "2", "--rpm", "3"), "", 2, "", "windowgate: given twice: --rpm\n"),
        row(listOf("stats", "--tpm", "5", "--tpm", "6"), "", 2, "", "windowgate: given twice: --tpm\n"),
        row(listOf("plan", "--rpm", "--tpm"), "", 2, "", "windowgate: bad value for --rpm\n"),
        row(listOf("plan", "--tpm", "--rpm", "5"), "", 2, "", "windowgate: bad value for --tpm\n"),
        row(listOf("plan", "--rpm", "5", "x"), "", 2, "", "windowgate: unexpected argument x\n"),
        row(listOf("plan", "--rpm", "5", "--tpm", "500"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "a1 arrive 0 send 0 wait 0\na2 arrive 5 send 5 wait 0\na3 arrive 10 send 10 wait 0\nlast send 10\n", ""),
        row(listOf("plan", "--bogus", "--rpm", "0"), "", 2, "", "windowgate: unknown option --bogus\n"),
        row(listOf("plan", "--rpm", "0", "--bogus"), "", 2, "", "windowgate: bad value for --rpm\n"),
    )
