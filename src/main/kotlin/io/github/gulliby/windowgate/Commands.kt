package io.github.gulliby.windowgate

fun checkCommand(options: Options, input: String): Reply {
    val requests = readRequests(input, options)
    return Reply(0, formatCheck(requests.size), "")
}

fun planCommand(options: Options, input: String): Reply {
    val requests = readRequests(input, options)
    val schedule = simulate(requests, options)
    return Reply(0, formatPlan(schedule), "")
}

fun waitsCommand(options: Options, input: String): Reply {
    val requests = readRequests(input, options)
    val schedule = simulate(requests, options)
    return Reply(0, formatWaits(schedule), "")
}

fun statsCommand(options: Options, input: String): Reply {
    val requests = readRequests(input, options)
    val schedule = simulate(requests, options)
    return Reply(0, formatStats(schedule), "")
}
