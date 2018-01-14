package com.p69.elma.litho

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext


fun <T> ReceiveChannel<T>.throttle(
        wait: Long = 300,
        context: CoroutineContext = DefaultDispatcher
): ReceiveChannel<T> = produce(context) {
    var throttlingJob = Job()
    consumeEach {
        throttlingJob.cancel()
        throttlingJob = launch(coroutineContext) {
            delay(wait)
            if (isActive) {
                offer(it)
            }
        }
    }
}