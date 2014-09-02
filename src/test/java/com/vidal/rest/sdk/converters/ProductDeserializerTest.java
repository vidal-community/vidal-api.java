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
package com.vidal.rest.sdk.converters;

import com.vidal.rest.sdk.entities.Product;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit.converter.ConversionException;

import static com.vidal.rest.sdk.entities.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.isA;

@RunWith(MockitoJUnitRunner.class)
public class ProductDeserializerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    ProductDeserializer deserializer;

    @Test
    public void deserializes_product_from_feed() throws ConversionException {
        String feed = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:vidal=\"http://api.vidal.net/-/spec/vidal-api/1.0/\">" +
                "   <entry>" +
                "       <vidal:id>42</vidal:id>" +
                "       <title>Batman</title>" +
                "   </entry>" +
                "</feed>";

        Product product = deserializer.deserializeOne(feed);

        assertThat(product)
                .hasId("42")
                .hasName("Batman");
    }

    @Test
    public void feed_errors_are_wrapped_as_conversion_exceptions() throws ConversionException {
        exception.expect(ConversionException.class);
        exception.expectCause(isA(Exception.class));

        deserializer.deserializeOne("");
    }

    @Test
    public void does_not_deserialize_collection_of_products_yet() throws ConversionException {
        exception.expect(UnsupportedOperationException.class);

        deserializer.deserializeAll("");
    }

}