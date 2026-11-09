package io.github.gulliby.windowgate

fun formatCheck(count: Int): String =
    "ok $count ${if (count == 1) "request" else "requests"}\n"

fun formatPlan(schedule: Schedule): String = buildString {
    for (entry in schedule.entries) {
        append("${entry.request.id} arrive ${entry.request.time} send ${entry.send} wait ${entry.wait}\n")
    }
    append("last send ${schedule.entries.lastOrNull()?.send ?: 0}\n")
}

fun formatWaits(schedule: Schedule): String = buildString {
    var delayed = 0
    for (entry in schedule.entries) {
        if (entry.wait > 0) {
            append("${entry.request.id} waited ${entry.wait} s\n")
            delayed += 1
        }
    }
    append("delayed $delayed of ${schedule.entries.size}\n")
}

fun formatStats(schedule: Schedule): String {
    val entries = schedule.entries
    val delayed = entries.count { it.wait > 0 }
    val waitTotal = entries.sumOf { it.wait }
    val waitMax = entries.maxOfOrNull { it.wait } ?: 0
    val lastSend = entries.lastOrNull()?.send ?: 0
    return "requests ${entries.size}\n" +
        "delayed $delayed\n" +
        "wait total $waitTotal\n" +
        "wait max $waitMax\n" +
        "last send $lastSend\n" +
        "peak requests ${schedule.peakRequests}\n" +
        "peak tokens ${schedule.peakTokens}\n"
}
