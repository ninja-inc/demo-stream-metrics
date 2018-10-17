package com.ninja.demostreammetrics

import com.fasterxml.jackson.databind.JsonNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.client.WebClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        classes = [ DemoStreamMetricsApplication::class ],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource(properties = ["management.port=0"])
class MetricsTest (
        @Autowired val demoListener: DemoListener
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Value("\${local.management.port}")
    private val managePort: Int = 0

    @Test
    fun test() {
        demoListener.listenMessage(DemoMessage("hello"))

        val metricsResult = WebClient.create()
                .get()
                .uri("http://localhost:$managePort/actuator/metrics/")
                .retrieve()
                .bodyToMono(JsonNode::class.java)
                //.bodyToMono(MetricsResult::class.java)
                .block()

        // I can't find spring.integration.send metrics..
        log.info("response: $metricsResult")
    }
}

data class MetricsResult (
        val names: List<String>
)