package ru.chaykin.microservapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Configuration
public class KafkaConfigConfig {
    @Bean
    public KafkaConfig kafkaConfig() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/resources/kafka.yml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(new Constructor(KafkaConfig.class), representer);
        return yaml.loadAs(inputStream, KafkaConfig.class);
    }
}
