package com.ninja.demostreammetrics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@EnableBinding(DemoChannel::class)
class DemoStreamMetricsApplication

fun main(args: Array<String>) {
    runApplication<DemoStreamMetricsApplication>(*args)
}
