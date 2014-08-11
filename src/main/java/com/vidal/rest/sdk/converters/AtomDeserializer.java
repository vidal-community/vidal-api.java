package com.vidal.rest.sdk.converters;

import retrofit.converter.ConversionException;

import java.util.Collection;

public interface AtomDeserializer<T> {

    T deserializeOne(String contents) throws ConversionException;
    Collection<T> deserializeAll(String contents) throws ConversionException;
}
