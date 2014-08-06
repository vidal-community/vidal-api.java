package com.vidal.rest.sdk.converters;

import com.vidal.rest.sdk.entities.Product;

import static java.lang.String.format;

public class EntityConverterFactory {

    public <T> EntityConverter<T> converter(Class<T> type) {
        if (Product.class.isAssignableFrom(type)) {
            return (EntityConverter<T>) new ProductConverter();
        }
        throw new IllegalArgumentException(format("%s not supported", type.getName()));
    }
}
