/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 VIDAL France
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
