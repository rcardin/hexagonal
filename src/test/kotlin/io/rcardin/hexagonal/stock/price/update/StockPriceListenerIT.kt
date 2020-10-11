package io.rcardin.hexagonal.stock.price.update

import `in`.rcard.kafkaesque.Kafkaesque
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.DoubleSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Ignore
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        classes = [StockPriceListener::class]
)
@EmbeddedKafka(topics = ["prices"])
internal class StockPriceListenerIT {

    @Autowired
    lateinit var broker: EmbeddedKafkaBroker

    @MockBean
    lateinit var useCase: StockPriceUpdateUseCase

    @Test
    @Ignore
    fun `The lister should read update stock messages and properly process them`() {
        Kafkaesque.usingBroker(broker)
                .produce<String, Double>()
                .toTopic("prices")
                .withSerializers(StringSerializer(), DoubleSerializer())
                .messages(buildMessages())
                .expecting()
                .assertingAfterAll {
                    runBlocking {
                        verify(useCase, times(1))
                                .updatePrice(StockPriceUpdateUseCase.StockPriceUpdateCommand("AAPL", 1234.56))
                    }
                }
    }

    private fun buildMessages(): List<ProducerRecord<String, Double>>? {
        return listOf(
                ProducerRecord<String, Double>("prices", "AAPL", 1234.56),
                ProducerRecord<String, Double>("prices", "TSLA", 123.45)
        )
    }
}