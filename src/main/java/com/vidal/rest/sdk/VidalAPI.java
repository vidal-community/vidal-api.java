package com.vidal.rest.sdk;

import com.vidal.rest.sdk.converters.AtomConverter;
import retrofit.RestAdapter;

public class VidalAPI {

    public static Resources at(String uri) {
        return new ResourceFactory(
                new RestAdapter.Builder()
                        .setEndpoint(uri)
                        .setConverter(new AtomConverter())
                        .build()
        );
    }
}
