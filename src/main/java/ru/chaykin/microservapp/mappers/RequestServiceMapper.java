package ru.chaykin.microservapp.mappers;

import dto.requestservice.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.chaykin.microservapp.jooq.tables.records.RequestsRecord;

import java.util.UUID;

@Component
@Slf4j
public class RequestServiceMapper {

    public CreateRequestResponseDto toSuccessCreateRequestResponseDto (RequestsRecord requestsRecord) {
        return new CreateRequestResponseDto()
                .success(true)
                .requestId(requestsRecord.getRequestId());
    }

    public CreateRequestResponseDto toErrorCreateRequestResponseDto (Throwable ex) {
        log.error("Ошибка создания запроса", ex);
        return new CreateRequestResponseDto()
                .success(false)
                .error(ex);
    }

    public GetRequestResponseDto toSuccessGetRequestResponseDto (RequestsRecord requestsRecord) {
        return new GetRequestResponseDto()
                .success(true)
                .requestId(requestsRecord.getRequestId())
                .clientNum(requestsRecord.getClientNum())
                .payload(requestsRecord.getPayload());
    }

    public GetRequestResponseDto toErrorGetRequestResponseDto (Throwable ex) {
        log.error("Ошибка получения запроса", ex);
        return new GetRequestResponseDto()
                .success(false)
                .error(ex);
    }

    public UpdateRequestResponseDto toSuccessUpdateRequestResponseDto (RequestsRecord requestsRecord) {
        return new UpdateRequestResponseDto()
                .success(true)
                .requestId(requestsRecord.getRequestId())
                .clientNum(requestsRecord.getClientNum());
    }

    public UpdateRequestResponseDto toErrorUpdateRequestResponseDto (Throwable ex) {
        log.error("Ошибка обновления запроса", ex);
        return new UpdateRequestResponseDto()
                .success(false)
                .error(ex);
    }

    public DeleteRequestResponseDto toSuccessDeleteRequestResponseDto (UUID requestId) {
        return new DeleteRequestResponseDto()
                .success(true)
                .requestId(requestId);
    }

    public DeleteRequestResponseDto toErrorDeleteRequestResponseDto (Throwable ex) {
        log.error("Ошибка удаления запроса", ex);
        return new DeleteRequestResponseDto()
                .success(false)
                .error(ex);
    }
}
