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