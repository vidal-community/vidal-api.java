package com.vidal.rest.sdk.converters;

import com.vidal.rest.sdk.entities.Product;

import javax.inject.Inject;

import static java.lang.String.format;

public class AtomDeserializerFactory {

    private final AtomFeedReader reader;

    @Inject
    public AtomDeserializerFactory(AtomFeedReader reader) {
        this.reader = reader;
    }

    public <T> AtomDeserializer<T> find(Class<T> type) {
        if (Product.class.isAssignableFrom(type)) {
            return (AtomDeserializer<T>) new ProductDeserializer(reader);
        }
        throw new IllegalArgumentException(format("%s not supported", type.getName()));
    }
}
