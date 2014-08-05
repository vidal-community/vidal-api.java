package com.vidal.rest.sdk;

import com.vidal.rest.sdk.converters.AtomConverter;
import com.vidal.rest.sdk.converters.EntityConverterFactory;
import retrofit.RestAdapter;

public class VidalAPI {

    public static Resources at(String uri) {
        return new ResourceFactory(
                new RestAdapter.Builder()
                        .setEndpoint(uri)
                        .setConverter(converter())
                        .build()
        );
    }

    private static AtomConverter converter() {
        return new AtomConverter(entityConverterFactory());
    }

    private static EntityConverterFactory entityConverterFactory() {
        return new EntityConverterFactory();
    }
}
