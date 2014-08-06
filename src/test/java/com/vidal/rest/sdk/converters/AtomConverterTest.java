package com.vidal.rest.sdk.converters;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit.converter.ConversionException;
import retrofit.mime.TypedInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtomConverterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    AtomDeserializerFactory deserializers;

    @Mock
    AtomDeserializer deserializer;

    @InjectMocks
    AtomConverter converter;

    @Test
    public void delegates_to_Atom_deserializer() throws IOException, ConversionException {
        when(deserializer.deserializeOne("Lococo")).thenReturn("LC Waikiki");
        when(deserializers.find(String.class)).thenReturn(deserializer);

        Object result = converter.fromBody(typedInput("Lococo"), String.class);
        Assertions.assertThat(result).isEqualTo("LC Waikiki");
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