package ru.chaykin.microservapp.dao;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.stereotype.Repository;
import ru.chaykin.microservapp.jooq.tables.pojos.Requests;
import ru.chaykin.microservapp.jooq.tables.records.RequestsRecord;

import java.util.List;
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

    public RequestsRecord getRequestById(UUID requestId) {
        return dslContext.selectFrom(REQUESTS)
                .where(REQUESTS.REQUEST_ID.eq(requestId))
                .fetchOne();
    }

    public RequestsRecord getRequestByIdAndClientNum(UUID id, Long clientNum) {
        List<Condition> conditions = List.of(
            REQUESTS.ID.eq(id),
            REQUESTS.CLIENT_NUM.eq(clientNum)
        );
        return dslContext.selectFrom(REQUESTS)
                .where(conditions.stream().reduce(conditions.get(0), Condition::and))
                .fetchOne();
    }

    public RequestsRecord updateRequest(RequestsRecord newRecord) {
        dslContext.update(REQUESTS)
                .set(newRecord)
                .where(REQUESTS.REQUEST_ID.eq(newRecord.getRequestId()))
                .execute();
        return dslContext.selectFrom(REQUESTS)
                .where(REQUESTS.REQUEST_ID.eq(newRecord.getRequestId()))
                .fetchOne();
    }

    public void deleteRequestById(UUID requestId) {
        dslContext.delete(REQUESTS)
                .where(REQUESTS.REQUEST_ID.eq(requestId))
                .execute();
    }
}
