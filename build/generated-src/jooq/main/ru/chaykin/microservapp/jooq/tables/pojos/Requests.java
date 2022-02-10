/*
 * This file is generated by jOOQ.
 */
package ru.chaykin.microservapp.jooq.tables.pojos;


import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Requests implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID   id;
    private String payload;

    public Requests() {}

    public Requests(Requests value) {
        this.id = value.id;
        this.payload = value.payload;
    }

    public Requests(
        UUID   id,
        String payload
    ) {
        this.id = id;
        this.payload = payload;
    }

    /**
     * Getter for <code>requestservice.requests.id</code>.
     */
    @NotNull
    public UUID getId() {
        return this.id;
    }

    /**
     * Setter for <code>requestservice.requests.id</code>.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Getter for <code>requestservice.requests.payload</code>.
     */
    public String getPayload() {
        return this.payload;
    }

    /**
     * Setter for <code>requestservice.requests.payload</code>.
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Requests (");

        sb.append(id);
        sb.append(", ").append(payload);

        sb.append(")");
        return sb.toString();
    }
}