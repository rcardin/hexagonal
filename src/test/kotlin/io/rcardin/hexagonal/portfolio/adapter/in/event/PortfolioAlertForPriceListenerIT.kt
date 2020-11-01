package io.rcardin.hexagonal.portfolio.adapter.`in`.event

import `in`.rcard.kafkaesque.Kafkaesque
import `in`.rcard.kafkaesque.KafkaesqueProducer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioAlertForPriceUseCase
import io.rcardin.hexagonal.portfolio.application.port.`in`.PortfolioAlertForPriceUseCase.PortfolioAlertForPriceCommand
import kotlinx.coroutines.runBlocking
import org.apache.kafka.common.serialization.DoubleSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = [PortfolioAlertForPriceListener::class],
        properties = [
            "spring.kafka.consumer.auto-offset-reset=earliest"
        ]
)
@EmbeddedKafka(
        topics = ["prices"],
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
@EnableAutoConfiguration(
        exclude = [
            EmbeddedMongoAutoConfiguration::class,
            MongoAutoConfiguration::class,
            MongoReactiveAutoConfiguration::class
        ]
)
internal class PortfolioAlertForPriceListenerIT {
    @Autowired
    lateinit var broker: EmbeddedKafkaBroker

    @MockBean
    lateinit var useCase: PortfolioAlertForPriceUseCase

    @Test
    fun `The listener should read update stock messages and properly process them`() {
        Kafkaesque.usingBroker(broker)
                .produce<String, Double>()
                .toTopic("prices")
                .withSerializers(StringSerializer(), DoubleSerializer())
                .messages(buildMessages())
                .expecting()
                .assertingAfterAll {
                    runBlocking {
                        verify(useCase, times(1))
                                .alertPriceFalling(PortfolioAlertForPriceCommand("AAPL", 1234.56))
                    }
                }
    }

    private fun buildMessages(): List<KafkaesqueProducer.Record<String, Double>>? {
        return listOf(
                KafkaesqueProducer.Record.of("AAPL", 1234.56),
                KafkaesqueProducer.Record.of("TSLA", 123.45)
        )
    }
}