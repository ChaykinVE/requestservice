/*
 * This file is generated by jOOQ.
 */
package ru.chaykin.microservapp.jooq.tables;


import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.chaykin.microservapp.jooq.Keys;
import ru.chaykin.microservapp.jooq.Requestservice;
import ru.chaykin.microservapp.jooq.tables.records.RequestsRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Requests extends TableImpl<RequestsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>requestservice.requests</code>
     */
    public static final Requests REQUESTS = new Requests();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RequestsRecord> getRecordType() {
        return RequestsRecord.class;
    }

    /**
     * The column <code>requestservice.requests.id</code>.
     */
    public final TableField<RequestsRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>requestservice.requests.payload</code>.
     */
    public final TableField<RequestsRecord, String> PAYLOAD = createField(DSL.name("payload"), SQLDataType.VARCHAR, this, "");

    private Requests(Name alias, Table<RequestsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Requests(Name alias, Table<RequestsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>requestservice.requests</code> table reference
     */
    public Requests(String alias) {
        this(DSL.name(alias), REQUESTS);
    }

    /**
     * Create an aliased <code>requestservice.requests</code> table reference
     */
    public Requests(Name alias) {
        this(alias, REQUESTS);
    }

    /**
     * Create a <code>requestservice.requests</code> table reference
     */
    public Requests() {
        this(DSL.name("requests"), null);
    }

    public <O extends Record> Requests(Table<O> child, ForeignKey<O, RequestsRecord> key) {
        super(child, key, REQUESTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Requestservice.REQUESTSERVICE;
    }

    @Override
    public UniqueKey<RequestsRecord> getPrimaryKey() {
        return Keys.REQUESTS_PKEY;
    }

    @Override
    public Requests as(String alias) {
        return new Requests(DSL.name(alias), this);
    }

    @Override
    public Requests as(Name alias) {
        return new Requests(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Requests rename(String name) {
        return new Requests(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Requests rename(Name name) {
        return new Requests(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
