/*
 * This file is generated by jOOQ.
 */
package ru.chaykin.microservapp.jooq;


import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import ru.chaykin.microservapp.jooq.tables.Requests;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Requestservice extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>requestservice</code>
     */
    public static final Requestservice REQUESTSERVICE = new Requestservice();

    /**
     * The table <code>requestservice.requests</code>.
     */
    public final Requests REQUESTS = Requests.REQUESTS;

    /**
     * No further instances allowed
     */
    private Requestservice() {
        super("requestservice", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Requests.REQUESTS
        );
    }
}
