package ru.chaykin.microservapp.services;

import dto.requestservice.CreateRequestDto;
import dto.requestservice.DeleteRequestDto;
import dto.requestservice.GetRequestDto;
import dto.requestservice.UpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chaykin.microservapp.dao.RequestsDao;
import ru.chaykin.microservapp.jooq.tables.records.RequestsRecord;
import ru.chaykin.microservapp.mappers.RequestRepositoryMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestsRepositoryService {

    private final RequestsDao requestsDao;
    private final RequestRepositoryMapper requestRepositoryMapper;

    @Transactional
    public RequestsRecord createRequest(CreateRequestDto createRequestDto) {
        return requestsDao.save(
                requestsDao.convertPojoToRecord(
                        requestRepositoryMapper.toRequests(createRequestDto)
                )
        );
    }

    @Transactional
    public RequestsRecord getRequest(GetRequestDto getRequestDto) {
        return requestsDao.getRequestById(getRequestDto.requestId());
    }

    @Transactional
    public RequestsRecord updateRequest(UpdateRequestDto updateRequestDto) {
        return requestsDao.updateRequest(
                requestsDao.convertPojoToRecord(
                        requestRepositoryMapper.toRequests(updateRequestDto)
                )
        );
    }

    @Transactional
    public void deleteRequest(DeleteRequestDto deleteRequestDto) {
        requestsDao.deleteRequestById(deleteRequestDto.requestId());
    }
}
