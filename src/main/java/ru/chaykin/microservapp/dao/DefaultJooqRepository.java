package ru.chaykin.microservapp.dao;

import org.apache.commons.lang3.BooleanUtils;
import org.jooq.*;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public abstract class DefaultJooqRepository<C, R extends UpdatableRecord<R>>
        implements PagingAndSortingRepository<R, UUID>, InitializingBean {

    protected DSLContext dslContext;

    @Override
    public void afterPropertiesSet() {
        if (dslContext == null) throw new BeanInitializationException("DSL context is null");
    }

    public DSLContext getDslContext() {
        return this.dslContext;
    }

    @Autowired
    public void setDslContext(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public R convertPojoToRecord(Object pojo) {
        return this.dslContext.newRecord(getDaoTable(), pojo);
    }

    @Override
    public Iterable<R> findAll() {
        return dslContext.selectFrom(getDaoTable()).fetch();
    }

    @Override
    public Optional<R> findById(UUID id) {
        Iterator<R> iterator = findAllById(Collections.singletonList(id)).iterator();
        if (iterator.hasNext()) return Optional.of(iterator.next());
        return Optional.empty();
    }

    @Override
    public <S extends R> S save(S record) {
        if (record == null) throw new IllegalArgumentException("Record is null");
        UUID id = record.get(getIdField(), UUID.class);
        if (id == null) {
            record.set(getIdField(), UUID.randomUUID());
            dslContext.insertInto(getDaoTable()).set(record).execute();
        } else {
            dslContext.update(getDaoTable()).set(record).where(getIdField().eq(record.get(getIdField()))).execute();
        }
        return record;
    }

    @Override
    public void delete(R record) {
        dslContext.executeDelete(record);
    }

    @Override
    public <S extends R> Iterable<S> saveAll(Iterable<S> records) {
        return StreamSupport.stream(records.spliterator(), false).map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(UUID uuid) {
        return dslContext.fetchExists(getDaoTable(), getIdField().eq(uuid));
    }

    @Override
    public Iterable<R> findAllById(Iterable<UUID> ids) {
        if (ids == null) throw new IllegalArgumentException("Ids must be set");
        return dslContext.selectFrom(getDaoTable())
                .where(getIdField().in(StreamSupport.stream(ids.spliterator(), false).collect(Collectors.toList())))
                .fetch();
    }

    @Override
    public long count() {
        return dslContext.selectCount()
                .from(getDaoTable())
                .fetchOne(0, Long.class);
    }

    @Override
    public void deleteById(UUID uuid) {
        dslContext.delete(getDaoTable()).where(getIdField().eq(uuid)).execute();
    }

    public void deleteAll(Iterable<? extends R> records) {
        if (BooleanUtils.isNotFalse(dslContext.configuration().settings().isReturnRecordToPojo())
                && BooleanUtils.isTrue(dslContext.configuration().settings().isReturnAllOnUpdatableRecord())) {
            for (R record : records) delete(record);
        }
        else {
            dslContext.batchDelete(StreamSupport.stream(records.spliterator(), false).collect(Collectors.toList()))
                    .execute();
        }
    }

    @Override
    public void deleteAll() {
        dslContext.truncate(getDaoTable()).execute();
    }

    @Override
    public Iterable<R> findAll(Sort sort) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Page<R> findAll(Pageable pageable) {
        throw new RuntimeException("Not implemented yet");
    }

    public void deleteAllById(Iterable<? extends UUID> uuids) {
        for (UUID uuid : uuids) deleteById(uuid);
    }

    public List<C> findByIdsIntoPojos(List<UUID> ids) {
        if (ids == null) throw new IllegalArgumentException();
        return dslContext.selectFrom(getDaoTable())
                .where(getIdField().in(ids))
                .fetch().into(getPojoClass());
    }

    public C findByIdIntoPojos(UUID id) {
        if (id == null) throw new IllegalArgumentException();
        return dslContext.selectFrom(getDaoTable())
                .where(getIdField().eq(id))
                .fetchOneInto(getPojoClass());
    }

    public List<C> findByFieldIdsIntoPojos(List<UUID> ids, TableField<R, ?> tableField) {
        if (ids == null) throw new IllegalArgumentException();
        return dslContext.selectFrom(getDaoTable())
                .where(tableField.in(ids))
                .fetch().into(getPojoClass());
    }

    public List<C> findByFieldIdsIntoPojosSort(List<UUID> ids, TableField<R, ?> tableField, SortField<?>... sortFields) {
        if (ids == null) throw new IllegalArgumentException();
        return dslContext.selectFrom(getDaoTable())
                .where(tableField.in(ids)).orderBy(sortFields)
                .fetch().into(getPojoClass());
    }

    public <S extends R> S saveWithId(S record, UUID id) {
        if (record == null) throw new IllegalArgumentException();
        if (record.get(getIdField()) == null) {
            record.set(getIdField(), id);
            dslContext.insertInto(getDaoTable()).set(record).execute();
        } else {
            dslContext.update(getDaoTable()).set(record).where(getIdField().eq(record.get(getIdField()))).execute();
        }
        return record;
    }

    public <S extends R> S merge(S record) {
        dslContext.insertInto(getDaoTable())
                .set(record)
                .onDuplicateKeyUpdate()
                .set(record).execute();
        return record;
    }

    protected abstract Table<R> getDaoTable();

    protected abstract Field<UUID> getIdField();

    protected abstract Class<C> getPojoClass();
}
