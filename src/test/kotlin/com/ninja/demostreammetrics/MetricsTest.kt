package com.ninja.demostreammetrics

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
    private val webClient: WebTestClient = WebTestClient.bindToServer().build()

    @Value("\${local.management.port}")
    private val managePort: Int = 0

    @Test
    fun test() {
        webClient.get()
                .uri("http://localhost:$managePort/actuator/metrics")
                .exchange()
                .expectBody<MetricsResult>()
                .consumeWith { response -> log.info(response.responseBody.toString()) }

        val metricsResult: MetricsResult? = WebClient.create()
                .get()
                .uri("http://localhost:$managePort/actuator/metrics")
                .retrieve()
                .bodyToMono(MetricsResult::class.java)
                .block()

        log.info("response: $metricsResult")
        log.info("demoListener: ${demoListener}")
    }
}

data class MetricsResult (
        val names: List<String>
)