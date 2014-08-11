package com.vidal.rest.sdk.converters;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.io.IOException;
import java.lang.reflect.Type;

public class AtomConverter implements Converter {

    private final AtomDeserializerFactory deserializers;

    public AtomConverter(AtomDeserializerFactory deserializers) {
        this.deserializers = deserializers;
    }

    @Override
    public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
        try {
            String contents = new TypedInputReader(typedInput).readContents();
            return deserializers.find((Class<?>) type).deserializeOne(contents);
        } catch (IOException e) {
            throw new ConversionException(e.getMessage(), e);
        }
    }

    @Override
    public TypedOutput toBody(Object o) {
        throw new UnsupportedOperationException();
    }


}
