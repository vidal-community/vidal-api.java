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
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import org.jaxen.JaxenException;
import org.jaxen.NamespaceContext;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.xom.XOMXPath;
import retrofit.converter.ConversionException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ProductDeserializer extends StandardAtomDeserializer<Product> {

    @Override
    public Product deserializeOne(String contents) throws ConversionException {
        try {
            Document document = document(contents);
            return new Product(
                parseInt(execute(document, "//atom:entry/vidal:id"), 10),
                execute(document, "//atom:entry/atom:title")
            );

        } catch (ParsingException| JaxenException | IOException e) {
            throw new ConversionException(e.getMessage(), e);
        }
    }

    @Override
    public Collection<Product> deserializeAll(String contents) throws ConversionException {
        throw new UnsupportedOperationException();
    }

    private String execute(Document document, String xpath) throws JaxenException {
        XOMXPath xpathEngine = new XOMXPath(xpath);
        xpathEngine.setNamespaceContext(namespace());
        return xpathEngine.stringValueOf(xpathEngine.selectSingleNode(document));
    }

    private Document document(String contents) throws ParsingException, IOException {
        return new Builder().build(new StringReader(contents));
    }

    private NamespaceContext namespace() {
        Map<String, String> map = new HashMap<>();
        map.put("vidal", "http://api.vidal.net/-/spec/vidal-api/1.0/");
        map.put("atom", "http://www.w3.org/2005/Atom");
        return new SimpleNamespaceContext(map);
    }
}
