package ru.chaykin.microservapp.config;

import common.CustomRecordInterceptor;
import common.Message;
import common.simple.SimpleKafkaTemplate;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    @Autowired
    public KafkaConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    @Bean
    public SimpleKafkaTemplate<String, Message> simpleKafkaTemplate() {
        return new SimpleKafkaTemplate<>(kafkaTemplate());
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        if (Optional.ofNullable(kafkaProperties.getProperties()).isPresent()) {
            props.putAll(kafkaProperties.getProperties());
        }
        return props;
    }

    @Bean
    public Map<String, Object> groupConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaProperties.getSpecificConsumer().getGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        if (Optional.ofNullable(kafkaProperties.getProperties()).isPresent()) {
            props.putAll(kafkaProperties.getProperties());
        }
        return props;
    }

    @Bean
    public Map<String, Object> specificConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaProperties.getSpecificConsumer().getGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, true);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        if (Optional.ofNullable(kafkaProperties.getProperties()).isPresent()) {
            props.putAll(kafkaProperties.getProperties());
        }
        return props;
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Message>> specificKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(specificConsumerFactory());
        factory.setConcurrency(this.kafkaProperties.getSpecificConsumer().getConcurrency());
        factory.getContainerProperties().setPollTimeout(this.kafkaProperties.getSpecificConsumer().getPollTimeOut());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setReplyTemplate(kafkaTemplate());
        factory.setRecordInterceptor(new CustomRecordInterceptor<>());
        return factory;
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Message>> groupKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(groupConsumerFactory());
        factory.setConcurrency(this.kafkaProperties.getGroupConsumer().getConcurrency());
        factory.getContainerProperties().setPollTimeout(this.kafkaProperties.getGroupConsumer().getPollTimeOut());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setReplyTemplate(kafkaTemplate());
        factory.setRecordInterceptor(new CustomRecordInterceptor<>());
        return factory;
    }

    private ConsumerFactory<String, Message> groupConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(groupConsumerConfigs());
    }

    private ConsumerFactory<String, Message> specificConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(specificConsumerConfigs());
    }
}
