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

import retrofit.converter.ConversionException;
import retrofit.mime.TypedInput;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AtomConverterTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock
	AtomDeserializerFactory deserializers;

	@Mock
	TargetTypeExtractor extractor;

	@Mock
	StandardAtomDeserializer deserializer;

	@InjectMocks
	AtomConverter converter;

	@Test
	public void delegates_to_Atom_deserializer() throws IOException, ConversionException {
		when(extractor.extractTargetEntity(any(Type.class))).thenReturn((Class) String.class);
		when(deserializer.deserialize(any(Type.class), eq("Lococo"))).thenReturn("LC Waikiki");
		when(deserializers.find(String.class)).thenReturn(deserializer);

		Object result = converter.fromBody(typedInput("Lococo"), String.class);
		assertThat(result).isEqualTo("LC Waikiki");
	}

	@Test
	public void encapsulates_reader_errors() throws IOException, ConversionException {
		exception.expect(ConversionException.class);
		exception.expectCause(isA(IOException.class));

		TypedInput typedInput = typedInput("");
		when(typedInput.in()).thenThrow(IOException.class);

		converter.fromBody(typedInput, Object.class);
	}

	@Test
	public void does_not_serialize_yet() {
		exception.expect(UnsupportedOperationException.class);

		converter.toBody(null);
	}

	private TypedInput typedInput(String text) throws IOException {
		TypedInput typedInput = mock(TypedInput.class);
		when(typedInput.in()).thenReturn(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
		return typedInput;
	}
}