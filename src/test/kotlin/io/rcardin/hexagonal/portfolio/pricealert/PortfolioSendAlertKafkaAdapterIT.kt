package io.rcardin.hexagonal.portfolio.pricealert

import `in`.rcard.kafkaesque.Kafkaesque
import kotlinx.coroutines.runBlocking
import org.apache.kafka.common.serialization.DoubleDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit.jupiter.SpringExtension
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = [PortfolioSendAlertKafkaAdapter::class],
        properties = [
            "spring.kafka.consumer.auto-offset-reset=earliest"
        ]
)
@EmbeddedKafka(
        topics = ["price-alerts"],
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
@EnableAutoConfiguration(
        exclude = [
            EmbeddedMongoAutoConfiguration::class,
            MongoAutoConfiguration::class,
            MongoReactiveAutoConfiguration::class
        ]
)
internal class PortfolioSendAlertKafkaAdapterIT {

    @Autowired
    lateinit var broker: EmbeddedKafkaBroker

    @Autowired
    lateinit var kafkaAdapter: PortfolioSendAlertKafkaAdapter

    @Test
    fun `The adapter should send and alert to the price-alerts topic`() {
        runBlocking {
            kafkaAdapter.sendAlert("p1", "AAPL", 345.6)
            kafkaAdapter.sendAlert("p1", "GOOGL", 123.4)
            Kafkaesque.usingBroker(broker)
                    .consume<String, Double>()
                    .fromTopic("price-alerts")
                    .withDeserializers(StringDeserializer(), DoubleDeserializer())
                    .expecting()
                    .havingRecordsSize(2)
                    .havingKeys { keys ->
                        expectThat(keys).containsExactlyInAnyOrder("p1-AAPL", "p1-GOOGL")
                    }
                    .havingPayloads { payloads ->
                        expectThat(payloads).containsExactlyInAnyOrder(345.6, 123.4)
                    }
                    .andCloseConsumer()
        }
    }
}