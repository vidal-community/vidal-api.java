package com.vidal.rest.sdk;

import java.net.URI;

public interface VidalAPI {
    // encapsulates RestAdapter creation
    // thin wrapper around setEndpoint call
    // and XML deserialization configuration
    Resources at(URI uri);
    Resources at(String uri);
}
