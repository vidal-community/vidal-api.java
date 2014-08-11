package com.vidal.rest.sdk;

import com.vidal.rest.sdk.converters.AtomConverter;
import com.vidal.rest.sdk.converters.AtomDeserializerFactory;
import com.vidal.rest.sdk.converters.AtomFeedReader;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.Converter;

import javax.inject.Singleton;

@Module(injects = ResourceFactory.class)
public class VidalApi {

    private final String uri;

    private VidalApi(String uri) {
        this.uri = uri;
    }

    public static Resources at(String uri) {
        ObjectGraph graph = ObjectGraph.create(new VidalApi(uri));
        return graph.get(ResourceFactory.class);
    }

    @Singleton
    @Provides
    ResourceFactory resourceFactory(RestAdapter restAdapter) {
        return new ResourceFactory(restAdapter);
    }

    @Singleton
    @Provides
    RestAdapter restAdapter(Converter converter) {
        return new RestAdapter.Builder()
                .setConverter(converter)
                .setEndpoint(uri)
                .build();
    }

    @Provides
    Converter converter(AtomDeserializerFactory deserializers) {
        return new AtomConverter(deserializers);
    }

    @Provides
    AtomFeedReader feedReader() {
        return new AtomFeedReader();
    }

}
