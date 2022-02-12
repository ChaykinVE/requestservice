package ru.chaykin.microservapp.listeners;

import common.KafkaHeaderAccessor;
import common.Message;
import common.simple.SimpleKafkaListener;
import dto.requestservice.CreateRequestDto;
import dto.requestservice.DeleteRequestDto;
import dto.requestservice.GetRequestDto;
import dto.requestservice.UpdateRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import ru.chaykin.microservapp.config.KafkaProperties;
import ru.chaykin.microservapp.services.RequestService;
import utils.KafkaUtils;

import java.util.Map;

@Service
@KafkaListener(containerFactory = "groupKafkaListenerContainerFactory", topics = {"requestservice_group"})
@RequiredArgsConstructor
@Slf4j
public class GroupListener extends SimpleKafkaListener {

    private final RequestService requestService;
    private final KafkaProperties kafkaProperties;

    @KafkaHandler
    public void createRequest(@NotNull CreateRequestDto requestDto, @Headers Map<String, Object> headers,
                              Acknowledgment acknowledgment) {
        KafkaHeaderAccessor kafkaHeaderAccessor = KafkaHeaderAccessor.ofMap(headers);
        log.info("Received {}, headers: {}, message: {}", CreateRequestDto.class.getSimpleName(), kafkaHeaderAccessor,
                requestDto.toString());
        requestService.createRequest(requestDto).thenAccept(result -> sendAnswer(result, kafkaHeaderAccessor));
        acknowledgment.acknowledge();
    }

    @KafkaHandler
    public void getRequest(@NotNull GetRequestDto requestDto, @Headers Map<String, Object> headers,
                           Acknowledgment acknowledgment) {
        KafkaHeaderAccessor kafkaHeaderAccessor = KafkaHeaderAccessor.ofMap(headers);
        log.info("Received {}, headers: {}, message: {}", GetRequestDto.class.getSimpleName(), kafkaHeaderAccessor,
                requestDto.toString());
        requestService.getRequest(requestDto).thenAccept(result -> sendAnswer(result, kafkaHeaderAccessor));
        acknowledgment.acknowledge();
    }

    @KafkaHandler
    public void updateRequest(@NotNull UpdateRequestDto requestDto, @Headers Map<String, Object> headers,
                              Acknowledgment acknowledgment) {
        KafkaHeaderAccessor kafkaHeaderAccessor = KafkaHeaderAccessor.ofMap(headers);
        log.info("Received {}, headers: {}, message: {}", GetRequestDto.class.getSimpleName(), kafkaHeaderAccessor,
                requestDto.toString());
        requestService.updateRequest(requestDto).thenAccept(result -> sendAnswer(result, kafkaHeaderAccessor));
        acknowledgment.acknowledge();
    }

    @KafkaHandler
    public void deleteRequest(@NotNull DeleteRequestDto requestDto, @Headers Map<String, Object> headers,
                              Acknowledgment acknowledgment) {
        KafkaHeaderAccessor kafkaHeaderAccessor = KafkaHeaderAccessor.ofMap(headers);
        log.info("Received {}, headers: {}, message: {}", GetRequestDto.class.getSimpleName(), kafkaHeaderAccessor,
                requestDto.toString());
        requestService.deleteRequest(requestDto)
                .thenAccept(result -> sendAnswer(result, kafkaHeaderAccessor));
        acknowledgment.acknowledge();
    }

    private void sendAnswer(Message message, KafkaHeaderAccessor kafkaHeaderAccessor) {
        ProducerRecord<String, Message> record = KafkaUtils.generateProducerRecord(kafkaHeaderAccessor.requestId(),
                kafkaHeaderAccessor.replyTopic(),
                kafkaHeaderAccessor.sourceInstance(), kafkaConfig, message);
        simpleKafkaTemplate().sendMessage(record);
    }
}
