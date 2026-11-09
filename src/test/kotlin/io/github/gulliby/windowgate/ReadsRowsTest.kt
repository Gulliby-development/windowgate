package io.github.gulliby.windowgate

import kotlin.test.Test

class ReadsRowsTest {
    @Test
    fun rows() {
        checkReads(rows)
    }
}

private val rows =
    listOf(
        row(listOf("check"), "", 1, "", ""),
        row(listOf("plan"), "", 1, "", ""),
        row(listOf("waits"), "", 1, "", ""),
        row(listOf("stats"), "", 1, "", ""),
        row(listOf("plan", "--rpm", "5"), "", 1, "", ""),
        row(listOf("plan", "--rpm", "60", "--tpm", "9999"), "", 1, "", ""),
        row(listOf("help"), "", 0, "", ""),
        row(listOf("-h"), "", 0, "", ""),
        row(listOf("--help"), "", 0, "", ""),
        row(listOf("--version"), "", 0, "", ""),
        row(emptyList(), "", 0, "", ""),
        row(listOf("frob"), "", 0, "", ""),
        row(listOf(""), "", 0, "", ""),
        row(listOf("check", "x"), "", 0, "", ""),
        row(listOf("plan", "--rpm"), "", 0, "", ""),
        row(listOf("plan", "--rpm", "0"), "", 0, "", ""),
        row(listOf("plan", "--rpm", "61"), "", 0, "", ""),
        row(listOf("plan", "--tpm", "10000"), "", 0, "", ""),
        row(listOf("plan", "--rpm", "2", "--rpm", "3"), "", 0, "", ""),
        row(listOf("Plan"), "", 0, "", ""),
        row(listOf("--rpm", "5"), "", 0, "", ""),
        row(listOf("stats", "--tpm", "007"), "", 0, "", ""),
        row(listOf("waits", "--bogus"), "", 0, "", ""),
        row(listOf("check", "--tpm", "1", "--rpm", "1"), "", 1, "", ""),
    )
