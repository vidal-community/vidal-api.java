package com.vidal.rest.sdk;

import com.vidal.rest.sdk.entities.Product;
import org.junit.Test;

import static com.vidal.rest.sdk.entities.Assertions.assertThat;

public class ProductsIT {

    @Test
    public void fetches_product_by_id() {
        Product product = VidalAPI.at("http://dev-software.vidal.net/excalibur-rest-snapshot/rest/api")
                .fetching(Products.class)
                .findOne(4011);


        assertThat(product)
                .hasId("vidal://product/4011")
                .hasName("CLAMOXYL 125 mg/5 ml pdre p susp buv");
    }
}