package ru.chaykin.microservapp.services;

import dto.requestservice.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.chaykin.microservapp.mappers.RequestServiceMapper;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestsRepositoryService requestsRepositoryService;
    private final RequestServiceMapper requestServiceMapper;

    public CompletableFuture<CreateRequestResponseDto> createRequest(CreateRequestDto requestDto) {
        log.debug("Processing creatRequest request {}", requestDto);
        return CompletableFuture.completedFuture(requestDto)
                .thenApply(requestsRepositoryService::createRequest)
                .thenApply(requestServiceMapper::toSuccessCreateRequestResponseDto)
                .exceptionally(requestServiceMapper::toErrorCreateRequestResponseDto);
    }

    public CompletableFuture<GetRequestResponseDto> getRequest(GetRequestDto requestDto) {
        log.debug("Processing getRequest request {}", requestDto);
        return CompletableFuture.completedFuture(requestDto)
                .thenApply(requestsRepositoryService::getRequest)
                .thenApply(requestServiceMapper::toSuccessGetRequestResponseDto)
                .exceptionally(requestServiceMapper::toErrorGetRequestResponseDto);
    }

    public CompletableFuture<UpdateRequestResponseDto> updateRequest(UpdateRequestDto requestDto) {
        log.debug("Processing updateRequest request {}", requestDto);
        return CompletableFuture.completedFuture(requestDto)
                .thenApply(requestsRepositoryService::updateRequest)
                .thenApply(requestServiceMapper::toSuccessUpdateRequestResponseDto)
                .exceptionally(requestServiceMapper::toErrorUpdateRequestResponseDto);
    }

    public CompletableFuture<DeleteRequestResponseDto> deleteRequest(DeleteRequestDto requestDto) {
        log.debug("Processing deleteRequest request {}", requestDto);
        return CompletableFuture.completedFuture(requestDto)
                .thenAccept(requestsRepositoryService::deleteRequest)
                .thenApply(x -> requestDto.requestId())
                .thenApply(requestServiceMapper::toSuccessDeleteRequestResponseDto)
                .exceptionally(requestServiceMapper::toErrorDeleteRequestResponseDto);
    }
}
