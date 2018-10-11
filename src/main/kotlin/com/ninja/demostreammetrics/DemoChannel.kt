package com.ninja.demostreammetrics

import org.springframework.cloud.stream.annotation.Input
import org.springframework.messaging.SubscribableChannel

interface DemoChannel {
    @Input("test_channel")
    fun getMessage(): SubscribableChannel
}
