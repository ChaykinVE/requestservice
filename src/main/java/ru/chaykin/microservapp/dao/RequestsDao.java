package ru.chaykin.microservapp.dao;

import lombok.RequiredArgsConstructor;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;
import ru.chaykin.microservapp.jooq.tables.pojos.Requests;
import ru.chaykin.microservapp.jooq.tables.records.RequestsRecord;

import java.util.UUID;

import static ru.chaykin.microservapp.jooq.tables.Requests.REQUESTS;


@Repository
@RequiredArgsConstructor
public class RequestsDao extends DefaultJooqRepository<Requests, RequestsRecord> {

    @Override
    protected Table<RequestsRecord> getDaoTable() {
        return REQUESTS;
    }

    @Override
    protected Field<UUID> getIdField() {
        return REQUESTS.ID;
    }

    @Override
    protected Class<Requests> getPojoClass() {
        return Requests.class;
    }

    public RequestsRecord getRequestById(UUID id) {
        return dslContext.selectFrom(REQUESTS)
                .where(REQUESTS.ID.eq(id))
                .fetchOne();
    }
}
