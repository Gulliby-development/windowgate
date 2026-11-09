package io.github.gulliby.windowgate

data class ScheduledRequest(val request: Request, val send: Int) {
    val wait: Int
        get() = send - request.time
}

data class Schedule(
    val entries: List<ScheduledRequest>,
    val peakRequests: Int,
    val peakTokens: Int,
)

fun simulate(requests: List<Request>, options: Options): Schedule {
    val entries = mutableListOf<ScheduledRequest>()
    val active = ArrayDeque<ScheduledRequest>()
    var previousSend = 0
    var peakRequests = 0
    var peakTokens = 0

    for (request in requests) {
        var candidate = maxOf(request.time, previousSend)
        while (true) {
            while (active.isNotEmpty() && active.first().send <= candidate - Registry.WINDOW) {
                active.removeFirst()
            }
            val activeTokens = active.sumOf { it.request.tokens }
            if (active.size < options.rpm && activeTokens + request.tokens <= options.tpm) {
                val scheduled = ScheduledRequest(request, candidate)
                entries.add(scheduled)
                active.addLast(scheduled)
                previousSend = candidate
                peakRequests = maxOf(peakRequests, active.size)
                peakTokens = maxOf(peakTokens, activeTokens + request.tokens)
                break
            }
            candidate = active.first().send + Registry.WINDOW
        }
    }

    return Schedule(entries, peakRequests, peakTokens)
}
