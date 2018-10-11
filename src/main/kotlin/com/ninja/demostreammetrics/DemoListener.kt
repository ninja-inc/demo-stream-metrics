package com.ninja.demostreammetrics

import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Component

@Component
class DemoListener {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @StreamListener("test_channel")
    fun listenMessage(demoMessage: DemoMessage) {
        log.info("message: $demoMessage")
    }
}

data class DemoMessage (
        val message: String
)
