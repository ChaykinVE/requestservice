package ru.chaykin.microservapp.config;

import config.BaseProducers;
import config.DefaultKafkaConfig;
import config.Producer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class KafkaProperties extends DefaultKafkaConfig {

    private Producers producers;

    @Data
    public static class Producers extends BaseProducers {
        private Producer requestprocessingservice;
    }
}
