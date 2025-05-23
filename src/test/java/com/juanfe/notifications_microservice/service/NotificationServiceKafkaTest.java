package com.juanfe.notifications_microservice.service;

import com.juanfe.notifications_microservice.dto.NotificationEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"level-up"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class NotificationServiceKafkaTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Test
    void testLevelUpTopicReceivesMessage() {
        // Arrange
        String topic = "level-up";
        UUID recipient = UUID.randomUUID();
        NotificationEvent event = new NotificationEvent();
        event.setEventType("LEVEL_UP");
        event.setRecipient(recipient);
        event.setSender(java.util.Optional.empty());
        event.setEventDetails(java.util.Optional.of("5"));

        // Set up a consumer to listen to the topic
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        DefaultKafkaConsumerFactory<String, NotificationEvent> cf = new DefaultKafkaConsumerFactory<>(
                consumerProps, new StringDeserializer(), new JsonDeserializer<>(NotificationEvent.class, false));
        Consumer<String, NotificationEvent> consumer = cf.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, topic);

        // Act
        // Use KafkaTemplate with correct value serializer
        kafkaTemplate.send(topic, event);
        kafkaTemplate.flush();

        // Assert
        ConsumerRecords<String, NotificationEvent> records = consumer.poll(Duration.ofSeconds(5));
        boolean found = false;
        for (ConsumerRecord<String, NotificationEvent> record : records) {
            if (record.value() != null && record.value().getEventType().equals("LEVEL_UP") && record.value().getRecipient().equals(recipient)) {
                found = true;
                break;
            }
        }
        assertThat(found).isTrue();
        consumer.close();
    }

    // Add a test configuration to provide a KafkaTemplate with JsonSerializer for NotificationEvent
    @TestConfiguration
    static class KafkaTestConfig {
        @Bean
        public KafkaTemplate<String, NotificationEvent> kafkaTemplate(EmbeddedKafkaBroker embeddedKafkaBroker) {
            Map<String, Object> producerProps = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
            producerProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            producerProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            ProducerFactory<String, NotificationEvent> pf = new DefaultKafkaProducerFactory<>(producerProps);
            return new KafkaTemplate<>(pf);
        }
    }
}
