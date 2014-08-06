package com.vidal.rest.sdk.converters;

import retrofit.mime.TypedInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

class TypedInputReader {

    private final TypedInput typedInput;

    public TypedInputReader(TypedInput typedInput) {
        this.typedInput = typedInput;
    }

    public String readContents() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String line : readAllLines()) {
            builder.append(line);
            builder.append(System.lineSeparator());
        }
        return builder.substring(0, builder.length() - 1);
    }

    private Collection<String> readAllLines() throws IOException {
        try (BufferedReader reader = new LineNumberReader(new InputStreamReader(typedInput.in(), StandardCharsets.UTF_8))) {
            return allLines(reader);
        }
    }

    private Collection<String> allLines(BufferedReader reader) throws IOException {
        Collection<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
