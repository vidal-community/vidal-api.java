package com.vidal.rest.sdk;

import com.vidal.rest.sdk.entities.Product;
import retrofit.http.GET;
import retrofit.http.Path;

public interface Products {

    @GET("/product/{id}")
    Product findOne(@Path("id") int id);
}
