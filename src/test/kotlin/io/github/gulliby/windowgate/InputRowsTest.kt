package io.github.gulliby.windowgate

import kotlin.test.Test

class InputRowsTest {
    @Test
    fun rows() {
        checkRows(rows)
    }
}

private val rows =
    listOf(
        row(listOf("check"), "", 0, "ok 0 requests\n", ""),
        row(listOf("check"), "a 0 1\n", 0, "ok 1 request\n", ""),
        row(listOf("check"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "ok 3 requests\n", ""),
        row(listOf("check"), "# note\n\n  \na 0 1\n\t\n#b 1 1\n", 0, "ok 1 request\n", ""),
        row(listOf("check"), "a 0 1\r\nb 1 1\r\n", 0, "ok 2 requests\n", ""),
        row(listOf("check"), "a 0 1", 0, "ok 1 request\n", ""),
        row(listOf("check"), "\ta\t0 \t 1 \t\n", 0, "ok 1 request\n", ""),
        row(listOf("check"), "a 0 1 # no\n", 1, "", "windowgate: line 1: expected ID TIME TOKENS\n"),
        row(listOf("check"), "A 0 1\n", 1, "", "windowgate: line 1: bad id A\n"),
        row(listOf("check"), "1a 0 1\n", 1, "", "windowgate: line 1: bad id 1a\n"),
        row(listOf("check"), "a-b 0 1\n", 1, "", "windowgate: line 1: bad id a-b\n"),
        row(listOf("check"), "a 0\n", 1, "", "windowgate: line 1: expected ID TIME TOKENS\n"),
        row(listOf("check"), "a 0 1 2\n", 1, "", "windowgate: line 1: expected ID TIME TOKENS\n"),
        row(listOf("check"), "a 00 1\n", 1, "", "windowgate: line 1: bad time 00\n"),
        row(listOf("check"), "a x 1\n", 1, "", "windowgate: line 1: bad time x\n"),
        row(listOf("check"), "a -1 1\n", 1, "", "windowgate: line 1: bad time -1\n"),
        row(listOf("check"), "a 86400 1\n", 1, "", "windowgate: line 1: bad time 86400\n"),
        row(listOf("check"), "a 0 0\n", 1, "", "windowgate: line 1: bad tokens 0\n"),
        row(listOf("check"), "a 0 01\n", 1, "", "windowgate: line 1: bad tokens 01\n"),
        row(listOf("check"), "a 0 x\n", 1, "", "windowgate: line 1: bad tokens x\n"),
        row(listOf("check"), "a 0 1\na 1 1\n", 1, "", "windowgate: line 2: duplicate id a\n"),
        row(listOf("check"), "a 5 1\nb 4 1\n", 1, "", "windowgate: line 2: time goes back\n"),
        row(listOf("check"), "a 5 1\nb 5 1\n", 0, "ok 2 requests\n", ""),
        row(listOf("check"), "a 0 1\nb 1 2000\n", 1, "", "windowgate: line 2: tokens exceed --tpm\n"),
        row(listOf("check"), "a 0 5\nb 1 1001\n", 1, "", "windowgate: line 2: tokens exceed --tpm\n"),
        row(listOf("check", "--tpm", "5"), "a 0 5\n", 0, "ok 1 request\n", ""),
        row(listOf("check", "--tpm", "5"), "a 0 6\n", 1, "", "windowgate: line 1: tokens exceed --tpm\n"),
    )
