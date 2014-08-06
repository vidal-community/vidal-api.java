package com.vidal.rest.sdk.converters;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.io.IOException;
import java.lang.reflect.Type;

public class AtomConverter implements Converter {

    private final EntityConverterFactory factory;

    public AtomConverter(EntityConverterFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
        try {
            String contents = new TypedInputReader(typedInput).readContents();
            return factory.converter((Class<?>)type).convertOne(contents);
        } catch (IOException e) {
            throw new ConversionException(e.getMessage(), e);
        }
    }

    @Override
    public TypedOutput toBody(Object o) {
        return null;
    }


}
