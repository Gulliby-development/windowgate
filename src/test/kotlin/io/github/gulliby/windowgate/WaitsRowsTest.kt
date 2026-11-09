package io.github.gulliby.windowgate

import kotlin.test.Test

class WaitsRowsTest {
    @Test
    fun rows() {
        checkRows(rows)
    }
}

private val rows =
    listOf(
        row(listOf("waits"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "delayed 0 of 3\n", ""),
        row(listOf("waits"), "r1 0 10\nr2 0 10\nr3 0 10\nr4 0 10\nr5 0 10\nr6 0 10\nr7 0 10\nr8 0 10\nr9 0 10\nr10 0 10\nr11 0 10\nr12 0 10\n", 0, "r11 waited 60 s\nr12 waited 60 s\ndelayed 2 of 12\n", ""),
        row(listOf("waits"), "", 0, "delayed 0 of 0\n", ""),
        row(listOf("waits"), "a 0 50\nb 30 50\nc 61 50\nd 90 50\ne 150 50\n", 0, "delayed 0 of 5\n", ""),
        row(listOf("waits"), "a 0 900\nb 1 150\nc 2 50\n", 0, "b waited 59 s\nc waited 58 s\ndelayed 2 of 3\n", ""),
        row(listOf("waits"), "a 0 1\nb 59 1\nc 60 1\nd 61 1\ne 119 1\nf 120 1\n", 0, "delayed 0 of 6\n", ""),
        row(listOf("waits"), "z1 86399 5\nz2 86399 5\nz3 86399 5\nz4 86399 5\nz5 86399 5\nz6 86399 5\nz7 86399 5\nz8 86399 5\nz9 86399 5\nz10 86399 5\nz11 86399 5\n", 0, "z11 waited 60 s\ndelayed 1 of 11\n", ""),
        row(listOf("waits", "--rpm", "2"), "a1 0 100\na2 5 100\na3 10 100\n", 0, "a3 waited 50 s\ndelayed 1 of 3\n", ""),
        row(listOf("waits", "--rpm", "1"), "a 0 1\nb 59 1\nc 60 1\nd 61 1\ne 119 1\nf 120 1\n", 0, "b waited 1 s\nc waited 60 s\nd waited 119 s\ne waited 121 s\nf waited 180 s\ndelayed 5 of 6\n", ""),
        row(listOf("waits", "--tpm", "300"), "a 0 200\nb 0 200\nc 0 100\n", 0, "b waited 60 s\nc waited 60 s\ndelayed 2 of 3\n", ""),
        row(listOf("waits", "--tpm", "500"), "a 0 400\nb 10 400\nc 20 400\nd 30 400\n", 0, "b waited 50 s\nc waited 100 s\nd waited 150 s\ndelayed 3 of 4\n", ""),
        row(listOf("waits", "--rpm", "60"), "r1 0 10\nr2 0 10\nr3 0 10\nr4 0 10\nr5 0 10\nr6 0 10\nr7 0 10\nr8 0 10\nr9 0 10\nr10 0 10\nr11 0 10\nr12 0 10\n", 0, "delayed 0 of 12\n", ""),
        row(listOf("waits", "--rpm", "5"), "a 5 10\nb 5 10\nc 5 10\nd 5 10\n", 0, "delayed 0 of 4\n", ""),
    )
