package com.ninja.demostreammetrics

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["management.port=0"])
class MetricsTest {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val webClient: WebTestClient = WebTestClient.bindToServer().build()

    @LocalServerPort
    private val port: Int = 0

    @Value("\${local.management.port}")
    private val managePort: Int = 0

    @Test
    fun test() {
        webClient.get()
                .uri("http://localhost:$managePort/actuator/metrics")
                .exchange()
                .expectBody<MetricsResult>()
                .consumeWith { response -> log.info(response.responseBody.toString()) }

    }
}

data class MetricsResult (
        val names: List<String>
)