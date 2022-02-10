package ru.chaykin.microservapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chaykin.microservapp.dao.RequestsDao;
import ru.chaykin.microservapp.jooq.tables.pojos.Requests;
import ru.chaykin.microservapp.jooq.tables.records.RequestsRecord;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestsRepositoryService {
    private final RequestsDao requestsDao;

    @Transactional
    public UUID createRequest(String payload) {
        Requests request = new Requests();
        request.setPayload(payload);
        RequestsRecord requestsRecord = requestsDao.save(requestsDao.convertPojoToRecord(request));
        return requestsRecord.getId();
    }
}
