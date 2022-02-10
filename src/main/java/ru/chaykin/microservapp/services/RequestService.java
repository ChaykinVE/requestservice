package ru.chaykin.microservapp.services;

import dto.requestservice.CreateRequestDto;
import dto.requestservice.CreateRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestsRepositoryService requestsRepositoryService;

    public CompletableFuture<CreateRequestResponseDto> createRequest(CreateRequestDto requestDto) {
        log.debug("Processing creatRequest request {}", requestDto);
        return CompletableFuture.completedFuture(requestDto)
                .thenApply(x -> requestsRepositoryService.createRequest(x.payload()))
                .thenApply(x ->
                        new CreateRequestResponseDto()
                                .requestId(x)
                                .success(true))
                .exceptionally(x ->
                        new CreateRequestResponseDto()
                                .success(false));
    }
}
