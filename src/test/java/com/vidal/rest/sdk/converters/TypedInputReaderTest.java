package com.vidal.rest.sdk.converters;

import org.junit.Test;
import retrofit.mime.TypedInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TypedInputReaderTest {

    private static final String NEWLINE = System.lineSeparator();

    @Test
    public void reads_contents() throws IOException {
        TypedInputReader typedInputReader = new TypedInputReader(
            typedInput("Je m'appelle Jococo" + NEWLINE + "Et Je suis un Ouf")
        );

        String contents = typedInputReader.readContents();

        assertThat(contents).isEqualTo("Je m'appelle Jococo" + NEWLINE + "Et Je suis un Ouf");
    }

    private TypedInput typedInput(String text) throws IOException {
        TypedInput typedInput = mock(TypedInput.class);
        when(typedInput.in()).thenReturn(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
        return typedInput;
    }

}