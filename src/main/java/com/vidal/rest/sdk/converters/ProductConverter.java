package com.vidal.rest.sdk.converters;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.vidal.rest.sdk.entities.Product;
import retrofit.converter.ConversionException;

import java.io.StringReader;
import java.util.Collection;

public class ProductConverter implements EntityConverter<Product> {

    @Override
    public Product convertOne(String contents) throws ConversionException {
        try {
            SyndFeed feed = new SyndFeedInput().build(new StringReader(contents));
            SyndEntry entry = (SyndEntry) feed.getEntries().iterator().next();
            return new Product(entry.getUri(), entry.getTitle());
        } catch (FeedException e) {
            throw new ConversionException(e.getMessage(), e);
        }
    }

    @Override
    public Collection<Product> convertAll(String contents) throws ConversionException {
        throw new UnsupportedOperationException();
    }
}
