/*
 * This file is generated by jOOQ.
 */
package ru.chaykin.microservapp.jooq.tables.records;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import ru.chaykin.microservapp.jooq.tables.Requests;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RequestsRecord extends UpdatableRecordImpl<RequestsRecord> implements Record2<UUID, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>requestservice.requests.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>requestservice.requests.id</code>.
     */
    @NotNull
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>requestservice.requests.payload</code>.
     */
    public void setPayload(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>requestservice.requests.payload</code>.
     */
    public String getPayload() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<UUID, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Requests.REQUESTS.ID;
    }

    @Override
    public Field<String> field2() {
        return Requests.REQUESTS.PAYLOAD;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getPayload();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getPayload();
    }

    @Override
    public RequestsRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public RequestsRecord value2(String value) {
        setPayload(value);
        return this;
    }

    @Override
    public RequestsRecord values(UUID value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RequestsRecord
     */
    public RequestsRecord() {
        super(Requests.REQUESTS);
    }

    /**
     * Create a detached, initialised RequestsRecord
     */
    public RequestsRecord(UUID id, String payload) {
        super(Requests.REQUESTS);

        setId(id);
        setPayload(payload);
    }

    /**
     * Create a detached, initialised RequestsRecord
     */
    public RequestsRecord(ru.chaykin.microservapp.jooq.tables.pojos.Requests value) {
        super(Requests.REQUESTS);

        if (value != null) {
            setId(value.getId());
            setPayload(value.getPayload());
        }
    }
}