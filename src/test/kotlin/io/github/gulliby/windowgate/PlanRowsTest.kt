package io.github.gulliby.windowgate

import kotlin.test.Test

class PlanRowsTest {
    @Test
    fun rows() {
        checkRows(rows)
    }
}

private val rows =
    listOf(
        row(listOf("plan"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "a1 arrive 0 send 0 wait 0\na2 arrive 5 send 5 wait 0\na3 arrive 10 send 10 wait 0\nlast send 10\n", ""),
        row(listOf("plan"), "", 0, "last send 0\n", ""),
        row(listOf("plan"), "only 7 1\n", 0, "only arrive 7 send 7 wait 0\nlast send 7\n", ""),
        row(listOf("plan"), "a 0 50\nb 30 50\nc 61 50\nd 90 50\ne 150 50\n", 0, "a arrive 0 send 0 wait 0\nb arrive 30 send 30 wait 0\nc arrive 61 send 61 wait 0\nd arrive 90 send 90 wait 0\ne arrive 150 send 150 wait 0\nlast send 150\n", ""),
        row(listOf("plan"), "a 0 200\nb 0 200\nc 0 100\n", 0, "a arrive 0 send 0 wait 0\nb arrive 0 send 0 wait 0\nc arrive 0 send 0 wait 0\nlast send 0\n", ""),
        row(listOf("plan"), "a 0 900\nb 1 150\nc 2 50\n", 0, "a arrive 0 send 0 wait 0\nb arrive 1 send 60 wait 59\nc arrive 2 send 60 wait 58\nlast send 60\n", ""),
        row(listOf("plan"), "a 0 1\nb 59 1\nc 60 1\nd 61 1\ne 119 1\nf 120 1\n", 0, "a arrive 0 send 0 wait 0\nb arrive 59 send 59 wait 0\nc arrive 60 send 60 wait 0\nd arrive 61 send 61 wait 0\ne arrive 119 send 119 wait 0\nf arrive 120 send 120 wait 0\nlast send 120\n", ""),
        row(listOf("plan", "--rpm", "2"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "a1 arrive 0 send 0 wait 0\na2 arrive 5 send 5 wait 0\na3 arrive 10 send 60 wait 50\nlast send 60\n", ""),
        row(listOf("plan", "--rpm", "1"), "a 0 1\nb 59 1\nc 60 1\nd 61 1\ne 119 1\nf 120 1\n", 0, "a arrive 0 send 0 wait 0\nb arrive 59 send 60 wait 1\nc arrive 60 send 120 wait 60\nd arrive 61 send 180 wait 119\ne arrive 119 send 240 wait 121\nf arrive 120 send 300 wait 180\nlast send 300\n", ""),
        row(listOf("plan", "--rpm", "3"), "a 0 50\nb 30 50\nc 61 50\nd 90 50\ne 150 50\n", 0, "a arrive 0 send 0 wait 0\nb arrive 30 send 30 wait 0\nc arrive 61 send 61 wait 0\nd arrive 90 send 90 wait 0\ne arrive 150 send 150 wait 0\nlast send 150\n", ""),
        row(listOf("plan", "--tpm", "300"), "a 0 200\nb 0 200\nc 0 100\n", 0, "a arrive 0 send 0 wait 0\nb arrive 0 send 60 wait 60\nc arrive 0 send 60 wait 60\nlast send 60\n", ""),
        row(listOf("plan", "--tpm", "500"), "a 0 400\nb 10 400\nc 20 400\nd 30 400\n", 0, "a arrive 0 send 0 wait 0\nb arrive 10 send 60 wait 50\nc arrive 20 send 120 wait 100\nd arrive 30 send 180 wait 150\nlast send 180\n", ""),
    )
