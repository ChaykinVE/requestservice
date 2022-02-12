package ru.chaykin.microservapp.mappers;

import dto.requestservice.CreateRequestDto;
import dto.requestservice.UpdateRequestDto;
import org.springframework.stereotype.Component;
import ru.chaykin.microservapp.jooq.tables.pojos.Requests;

@Component
public class RequestRepositoryMapper {

    public Requests toRequests(CreateRequestDto createRequestDto) {
        Requests request = new Requests();
        request.setRequestId(createRequestDto.requestId());
        request.setClientNum(createRequestDto.clientNum());
        request.setPayload(createRequestDto.payload());
        return request;
    }

    public Requests toRequests(UpdateRequestDto updateRequestDto) {
        Requests request = new Requests();
        request.setRequestId(updateRequestDto.requestId());
        request.setClientNum(updateRequestDto.clientNum());
        request.setPayload(updateRequestDto.payload());
        return request;
    }
}
