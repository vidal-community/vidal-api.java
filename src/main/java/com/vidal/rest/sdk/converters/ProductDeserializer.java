package com.vidal.rest.sdk.converters;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.vidal.rest.sdk.entities.Product;
import retrofit.converter.ConversionException;

import java.util.Collection;

public class ProductDeserializer implements AtomDeserializer<Product> {

    private final AtomFeedReader reader;

    public ProductDeserializer(AtomFeedReader reader) {
        this.reader = reader;
    }

    @Override
    public Product deserializeOne(String contents) throws ConversionException {
        try {
            SyndFeed feed = reader.buildFeed(contents);
            SyndEntry entry = (SyndEntry) feed.getEntries().iterator().next();
            return new Product(entry.getUri(), entry.getTitle());
        } catch (FeedException e) {
            throw new ConversionException(e.getMessage(), e);
        }
    }

    @Override
    public Collection<Product> deserializeAll(String contents) throws ConversionException {
        throw new UnsupportedOperationException();
    }
}
