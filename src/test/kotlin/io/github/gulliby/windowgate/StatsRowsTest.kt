package io.github.gulliby.windowgate

import kotlin.test.Test

class StatsRowsTest {
    @Test
    fun rows() {
        checkRows(rows)
    }
}

private val rows =
    listOf(
        row(listOf("stats"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "requests 3\ndelayed 0\nwait total 0\nwait max 0\nlast send 10\npeak requests 3\npeak tokens 300\n", ""),
        row(listOf("stats"), "", 0, "requests 0\ndelayed 0\nwait total 0\nwait max 0\nlast send 0\npeak requests 0\npeak tokens 0\n", ""),
        row(listOf("stats"), "a 0 50\nb 30 50\nc 61 50\nd 90 50\ne 150 50\n", 0, "requests 5\ndelayed 0\nwait total 0\nwait max 0\nlast send 150\npeak requests 2\npeak tokens 100\n", ""),
        row(listOf("stats"), "a 0 900\nb 1 150\nc 2 50\n", 0, "requests 3\ndelayed 2\nwait total 117\nwait max 59\nlast send 60\npeak requests 2\npeak tokens 900\n", ""),
        row(listOf("stats"), "a 0 1\nb 59 1\nc 60 1\nd 61 1\ne 119 1\nf 120 1\n", 0, "requests 6\ndelayed 0\nwait total 0\nwait max 0\nlast send 120\npeak requests 3\npeak tokens 3\n", ""),
        row(listOf("stats", "--rpm", "2"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "requests 3\ndelayed 1\nwait total 50\nwait max 50\nlast send 60\npeak requests 2\npeak tokens 200\n", ""),
        row(listOf("stats", "--rpm", "1"), "a 0 1\nb 59 1\nc 60 1\nd 61 1\ne 119 1\nf 120 1\n", 0, "requests 6\ndelayed 5\nwait total 481\nwait max 180\nlast send 300\npeak requests 1\npeak tokens 1\n", ""),
        row(listOf("stats", "--tpm", "300"), "a 0 200\nb 0 200\nc 0 100\n", 0, "requests 3\ndelayed 2\nwait total 120\nwait max 60\nlast send 60\npeak requests 2\npeak tokens 300\n", ""),
        row(listOf("stats", "--tpm", "500"), "a 0 400\nb 10 400\nc 20 400\nd 30 400\n", 0, "requests 4\ndelayed 3\nwait total 300\nwait max 150\nlast send 180\npeak requests 1\npeak tokens 400\n", ""),
    )
