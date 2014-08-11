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

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.vidal.rest.sdk.entities.Product;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.vidal.rest.sdk.entities.ProductAssert.assertThat;
import static java.lang.String.format;

public class VidalApiTest {

    private final int port = freePort();

    @Rule
    public WireMockRule server = new WireMockRule(port);

    @Test
    public void returns_deserialized_http_response() {
        stubFor(
                get(urlMatching("/product/42"))
                    .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withBody("" +
                                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                        "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                                        "  <title>Product 4011</title>\n" +
                                        "  <link rel=\"self\" type=\"application/atom+xml\" href=\"/excalibur-rest-snapshot/rest/api/product/4011\" />\n" +
                                        "  <id>vidal://product/4011</id>\n" +
                                        "  <entry>\n" +
                                        "    <title>CLAMOXYL 125 mg/5 ml pdre p susp buv</title>\n" +
                                        "    <category term=\"PRODUCT\" />\n" +
                                        "    <author>\n" +
                                        "      <name>VIDAL</name>\n" +
                                        "    </author>\n" +
                                        "    <id>vidal://product/4011</id>\n" +
                                        "    <updated>2012-01-18T23:00:00Z</updated>\n" +
                                        "    <summary type=\"text\">CLAMOXYL 125 mg/5 ml pdre p susp buv</summary>\n" +
                                        "  </entry>\n" +
                                        "</feed>")
                )
        );

        Product product = VidalApi.at(format("http://localhost:%d/", port))
                .fetching(Products.class)
                .findOne(42);

        assertThat(product)
                .hasId("vidal://product/4011")
                .hasName("CLAMOXYL 125 mg/5 ml pdre p susp buv");
    }

    private int freePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}