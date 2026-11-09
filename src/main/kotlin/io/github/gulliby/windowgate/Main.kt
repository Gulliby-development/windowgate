package io.github.gulliby.windowgate

import kotlin.system.exitProcess

/** The only file that reads or writes a stream. */
fun main(args: Array<String>) {
    val words = args.toList()
    val input = if (readsStdin(words)) System.`in`.readBytes().toString(Charsets.UTF_8) else ""
    val answer = run(words, input)
    System.out.write(answer.out.toByteArray(Charsets.UTF_8))
    System.out.flush()
    System.err.write(answer.err.toByteArray(Charsets.UTF_8))
    System.err.flush()
    exitProcess(answer.status)
}
