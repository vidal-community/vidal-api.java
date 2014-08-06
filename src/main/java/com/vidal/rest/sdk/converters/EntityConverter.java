package com.vidal.rest.sdk.converters;

import retrofit.converter.ConversionException;

import java.util.Collection;

public interface EntityConverter<T> {

    T convertOne(String contents) throws ConversionException;
    Collection<T> convertAll(String contents) throws ConversionException;
}
