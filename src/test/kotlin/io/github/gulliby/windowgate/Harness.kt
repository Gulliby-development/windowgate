package io.github.gulliby.windowgate

import kotlin.test.assertEquals

/** The helper of the row files: a row is row(args, input, status, out, err) or, for checkReads, row(args, "", reads, "", ""). */
data class Row(val args: List<String>, val input: String, val status: Int, val out: String, val err: String)

fun row(args: List<String>, input: String, status: Int, out: String, err: String): Row = Row(args, input, status, out, err)

fun checkRows(rows: List<Row>) {
    for ((index, row) in rows.withIndex()) {
        val answer = run(row.args, row.input)
        assertEquals(Triple(row.status, row.out, row.err), Triple(answer.status, answer.out, answer.err), "row ${index + 1}")
    }
}

fun checkReads(rows: List<Row>) {
    for ((index, row) in rows.withIndex()) {
        assertEquals(row.status == 1, readsStdin(row.args), "row ${index + 1}")
    }
}
