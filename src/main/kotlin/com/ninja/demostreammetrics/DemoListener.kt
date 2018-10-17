package com.ninja.demostreammetrics

import org.slf4j.LoggerFactory
import org.springframework.amqp.AmqpRejectAndDontRequeueException
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.stereotype.Component

@Component
class DemoListener {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @StreamListener("test_channel")
    fun listenMessage(demoMessage: DemoMessage) {
        if (demoMessage.isDlq) {
            throw AmqpRejectAndDontRequeueException("go to DLQ")
        } else {
            log.info("$demoMessage acknowledged")
        }
    }
}

data class DemoMessage (
        val message: String,
        val isDlq: Boolean = false
)
