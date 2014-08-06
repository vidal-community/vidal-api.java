package com.vidal.rest.sdk;

public interface Resources {

    // encapsulates restAdapter.create(MyService.class)
    <T> T fetching(Class<T> entity);
}
