package com.vidal.rest.sdk.converters;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.vidal.rest.sdk.entities.Product;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;

import static java.lang.Integer.parseInt;

public class AtomConverter implements Converter {

    @Override
    public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
        try {
            String contents = new TypedInputReader(typedInput).readContents();
            SyndFeed feed = new SyndFeedInput().build(new StringReader(contents));
            SyndEntry entry = (SyndEntry) feed.getEntries().iterator().next();
            return new Product(entry.getUri(), entry.getTitle());
        } catch (IOException | FeedException e) {
            throw new ConversionException(e.getMessage(), e);
        }
    }

    @Override
    public TypedOutput toBody(Object o) {
        return null;
    }


}
