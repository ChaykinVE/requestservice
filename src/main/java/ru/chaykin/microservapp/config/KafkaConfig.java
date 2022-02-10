package ru.chaykin.microservapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import config.BaseProducers;
import config.DefaultKafkaConfig;
import config.Producer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class KafkaConfig extends DefaultKafkaConfig {

    private Producers producers;

    @Data
    public static class Producers extends BaseProducers {
        private Producer requestprocessingservice;
    }
}
