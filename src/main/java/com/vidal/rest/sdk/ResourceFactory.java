package com.vidal.rest.sdk;

import retrofit.RestAdapter;

public class ResourceFactory implements Resources {

    private final RestAdapter restAdapter;

    public ResourceFactory(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    @Override
    public <T> T fetching(Class<T> entity) {
        return restAdapter.create(entity);
    }
}
