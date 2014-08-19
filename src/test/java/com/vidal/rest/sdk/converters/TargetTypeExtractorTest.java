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

import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Type;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TargetTypeExtractorTest {

    private TargetTypeExtractor targetTypeExtractor = new TargetTypeExtractor();

    @Test
    public void extracts_single_entity() {
        Class<?> singleEntityClass = targetTypeExtractor.extractTargetEntity(String.class);

        assertThat(singleEntityClass).isEqualTo(String.class);
    }

    @Test
    public void extracts_type_of_collection() {
        ParameterizedTypeImpl type = given_generic_collection_of(String.class);

        Class<?> collectionParameterType = targetTypeExtractor.extractTargetEntity(type);

        assertThat(collectionParameterType).isEqualTo(String.class);
    }

    private ParameterizedTypeImpl given_generic_collection_of(Class<String> parameterClass) {

        ParameterizedTypeImpl type = mock(ParameterizedTypeImpl.class);
        when(type.getRawType()).thenReturn((Class) Collection.class);
        when(type.getActualTypeArguments()).thenReturn(new Type[] {parameterClass});
        return type;
    }

}