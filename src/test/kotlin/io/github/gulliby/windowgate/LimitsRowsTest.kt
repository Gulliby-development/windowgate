package io.github.gulliby.windowgate

import kotlin.test.Test

class LimitsRowsTest {
    @Test
    fun rows() {
        checkRows(rows)
    }
}

private val rows =
    listOf(
        row(listOf("check"), "q0 0 10\nq1 1 10\nq2 2 10\nq3 3 10\nq4 4 10\nq5 5 10\nq6 6 10\nq7 7 10\nq8 8 10\nq9 9 10\nq10 10 10\nq11 11 10\nq12 12 10\nq13 13 10\nq14 14 10\nq15 15 10\nq16 16 10\nq17 17 10\nq18 18 10\nq19 19 10\nq20 20 10\nq21 21 10\nq22 22 10\nq23 23 10\nq24 24 10\nq25 25 10\nq26 26 10\nq27 27 10\nq28 28 10\nq29 29 10\nq30 30 10\nq31 31 10\nq32 32 10\nq33 33 10\nq34 34 10\nq35 35 10\nq36 36 10\nq37 37 10\nq38 38 10\nq39 39 10\n", 0, "ok 40 requests\n", ""),
        row(listOf("check"), "q0 0 10\nq1 1 10\nq2 2 10\nq3 3 10\nq4 4 10\nq5 5 10\nq6 6 10\nq7 7 10\nq8 8 10\nq9 9 10\nq10 10 10\nq11 11 10\nq12 12 10\nq13 13 10\nq14 14 10\nq15 15 10\nq16 16 10\nq17 17 10\nq18 18 10\nq19 19 10\nq20 20 10\nq21 21 10\nq22 22 10\nq23 23 10\nq24 24 10\nq25 25 10\nq26 26 10\nq27 27 10\nq28 28 10\nq29 29 10\nq30 30 10\nq31 31 10\nq32 32 10\nq33 33 10\nq34 34 10\nq35 35 10\nq36 36 10\nq37 37 10\nq38 38 10\nq39 39 10\nq40 40 10\n", 1, "", "windowgate: input is too long (max 40 lines)\n"),
        row(listOf("check"), "abcdefgh 0 1\n", 0, "ok 1 request\n", ""),
        row(listOf("check"), "abcdefghi 0 1\n", 1, "", "windowgate: line 1: bad id abcdefghi\n"),
        row(listOf("check"), "a 86399 1\n", 0, "ok 1 request\n", ""),
        row(listOf("check"), "a 86400 1\n", 1, "", "windowgate: line 1: bad time 86400\n"),
        row(listOf("check", "--tpm", "9999"), "a 0 9999\n", 0, "ok 1 request\n", ""),
        row(listOf("check", "--tpm", "9999"), "a 0 10000\n", 1, "", "windowgate: line 1: bad tokens 10000\n"),
    )
